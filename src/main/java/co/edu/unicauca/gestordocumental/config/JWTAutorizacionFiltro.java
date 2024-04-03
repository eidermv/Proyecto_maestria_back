package co.edu.unicauca.gestordocumental.config;

import co.edu.unicauca.gestordocumental.token.TokenServicio;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * validate requests containing JWTs
 * @author danielavelasquezgarzon
 */
public class JWTAutorizacionFiltro extends BasicAuthenticationFilter 
{
    
    public JWTAutorizacionFiltro(AuthenticationManager authManager)
    {
        super(authManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException 
    {
        String header = req.getHeader(ConstantesSeguridad.HEADER_STRING);
        if (header == null || !header.startsWith(ConstantesSeguridad.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) 
    {
        String token = request.getHeader(ConstantesSeguridad.HEADER_STRING);
        if (token != null) 
        {
            
            Claims claims = (Claims) Jwts.parser()
                    .decryptWith(TokenServicio.convertStringToSecretKeyto(ConstantesSeguridad.SECRET))
                    .build()
                    //.setSigningKey()
                    .parseSignedClaims(token.replace(ConstantesSeguridad.TOKEN_PREFIX, ""))
                    .getPayload();
                    /*Jwts.parser()
                    .setSigningKey(ConstantesSeguridad.SECRET)
                    .parseClaimsJws(token.replace(ConstantesSeguridad.TOKEN_PREFIX, ""))
                    .getBody();*/
            
            Collection authorities =
                Arrays.stream(claims.get(ConstantesSeguridad.AUTHORIY_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

            System.out.println(authorities.stream().findFirst().get().toString());
            
            String user = claims.getSubject();
            
            if (user != null) 
            {
                UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(user, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return authentication;
            }
            return null;
        }
        return null;
    }
}
