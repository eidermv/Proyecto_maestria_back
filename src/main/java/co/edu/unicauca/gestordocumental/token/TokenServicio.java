package co.edu.unicauca.gestordocumental.token;

import co.edu.unicauca.gestordocumental.config.ConstantesSeguridad;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

import static io.jsonwebtoken.Jwts.ENC.A256CBC_HS512;

public class TokenServicio
{
    public static String crearToken(String username, String authorities) {
        String token = Jwts.builder()
                .subject(username)
                .claim(ConstantesSeguridad.AUTHORIY_KEY, authorities)
                .expiration(new Date(System.currentTimeMillis() + ConstantesSeguridad.EXPIRATION_TIME))
                //.signWith(convertStringToSecretKeyto(ConstantesSeguridad.SECRET), SignatureAlgorithm.HS512)
                .encryptWith(convertStringToSecretKeyto(ConstantesSeguridad.SECRET), A256CBC_HS512)
                .compact();

        return ConstantesSeguridad.TOKEN_PREFIX + token;
    }

    public static SecretKey convertStringToSecretKeyto(String encodedKey) {
        byte[] decodedKey = Base64.getEncoder().encode(encodedKey.getBytes());//Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }
}
