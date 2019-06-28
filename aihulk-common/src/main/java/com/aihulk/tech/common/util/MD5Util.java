package com.aihulk.tech.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: zhangyibo
 * @Date: 2018/9/26 11:33
 * @Description:
 */
public class MD5Util {

    private static final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String DEFAULT_ENCODING = "utf-8";

    private MD5Util() {
    }

    public static String encrypt(String source) {
        StringBuilder builder = new StringBuilder();
        byte[] bytes;
        MessageDigest messageDigest;
        try {
            bytes = source.getBytes(DEFAULT_ENCODING);
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        byte[] targ = messageDigest.digest(bytes);
        for (byte b : targ) {
            builder.append(chars[(b >> 4) & 0x0F]);
            builder.append(chars[b & 0x0F]);
        }
        return builder.toString();


    }

}
