package co.edu.unicauca.gestordocumental.token;

import co.edu.unicauca.gestordocumental.config.ConstantesSeguridad;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenServicio
{
    public static String crearToken(String username, String authorities) {
        String token = Jwts.builder()
                .setSubject(username)
                .claim(ConstantesSeguridad.AUTHORIY_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + ConstantesSeguridad.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.SECRET)
                .compact();

        return ConstantesSeguridad.TOKEN_PREFIX + token;
    }
}
