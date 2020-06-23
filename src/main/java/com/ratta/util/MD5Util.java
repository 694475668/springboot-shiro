package com.ratta.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author 刘明
 */
public class MD5Util {

    /**
     * @param source         明文
     * @param salt           盐
     * @param hashIterations 散列次数
     * @return
     */
    public static String md5(String source, String salt, Integer hashIterations) {
        return new Md5Hash(source, salt, hashIterations).toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("admin", "ratta", 2));
    }
}
