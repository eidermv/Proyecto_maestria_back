package co.edu.unicauca.gestordocumental.config;

import co.edu.unicauca.gestordocumental.model.Usuario;
import co.edu.unicauca.gestordocumental.token.TokenServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Issue JWTS to users sending credentialss
 * @author danielavelasquezgarzon
 */
public class JWTAutenticacionFiltro extends UsernamePasswordAuthenticationFilter 
{
    
    private AuthenticationManager authenticationManager;

    public JWTAutenticacionFiltro(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException 
    {
        try
        {
            Usuario creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Usuario.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsuario(),
                            creds.getContrasena(),
                            creds.getTiposUsuario())
            );
        } 
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        String username = ((User) auth.getPrincipal()).getUsername();
        String token = TokenServicio.crearToken(username, authorities);
        /*String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .claim(ConstantesSeguridad.AUTHORIY_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + ConstantesSeguridad.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.SECRET)
                .compact();
        //res.addHeader(ConstantesSeguridad.HEADER_STRING, ConstantesSeguridad.TOKEN_PREFIX + token);*/
        res.getWriter().write("{ \"token\": \"" + token + "\", " +
                                "\"roles\":\"" + authorities + "\"}");
    }
}