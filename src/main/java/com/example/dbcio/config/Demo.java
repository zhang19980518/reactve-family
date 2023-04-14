package com.example.dbcio.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author zhang
 * @date 2023/4/14
 * @description
 */
public class Demo {
    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        String encryptedPassword = passwordEncoder.encode("12d611d4af239b15e2f7a439a0172313");
//        System.out.println(encryptedPassword);

        String base64Key = "ly8YTgfvVwMGJFTWpiB1BN/Oi5dxrhnWtXGZecy7iQSqwmPOGstoVfj9s7Txy6QGLVxmpxAjFrVMA/SXqIz5Bw==";
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        SecretKey secKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secKey)
                        .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4MTQ0MzA2NywiZXhwIjoxNjgxNDQzNDI3fQ.XNBWTgMh-CXbygtPiDyj82Nv4vnf_GAN-teyMyFu9gxtOiZZpBo_gFKesSHo8ySH-ibF8h1prxMnje12zFhiSg");
        System.out.println(claimsJws.getBody().getSubject());
    }
}
