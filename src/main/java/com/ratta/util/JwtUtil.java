package com.ratta.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ratta.constants.Constant;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘明
 */
public class JwtUtil {
    /**
     * 生成Token
     *
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    public static String createToken(String userId, String userName) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, Constant.JWT_TTL);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withSubject("令牌")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(Constant.SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Constant.SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     *
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }

    public static void main(String[] args) throws InterruptedException {
        String token = JwtUtil.createToken("12345", "wangbo");
        System.out.println("token=" + token);
        Thread.sleep(5000);
        Map<String, Claim> map = JwtUtil.verifyToken(token);
        //Map<String, Claim> map = JwtUtil.parseToken(token);
        //遍历
        for (Map.Entry<String, Claim> entry : map.entrySet()) {
            if ("userId".equals(entry.getKey()) || "userName".equals(entry.getKey())) {
                System.out.println(entry.getKey() + "============" + entry.getValue().asString());
            }
        }
    }
}