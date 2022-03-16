package uz.pdp.pdp_food_delivery.rest.config.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author johnl
 * @since 2/24/2022
 */

public class JwtUtils {

    public static Integer expiry = 1_800_000;
    public static String secret = "ASDQW#@!$#@%$#DSFSDFRT%$#%34543terg45%^%$";

    public static Date getExpiry() {
        return new Date(System.currentTimeMillis() + expiry);
    }

    public static Date getExpiryForRefreshToken() {
        return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public static JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }
}
