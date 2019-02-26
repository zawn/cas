package org.apereo.cas.adaptors.jdbc;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * File Name    : EncryptClass
 * Description  :
 * Author       : Ralap
 * Create Date  : 2017/3/19
 * Version      : v1
 */
public class EncryptClass {

    /**
     * 加密
     *
     * @param data      要加密的数据
     * @param key       密钥
     */
    public static String encrypt(String data, String key) {
        try {
            // 获取key的MD5值
            byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(key.getBytes());

            // md5值转换成十六进制（大写）
            String md5 = bytes2Hex(md5Bytes).toUpperCase();
            println("keyMD5=%s", md5);

            // 并获取前8位作为真实的key
            String pwd = md5.substring(0, 8);
            println("pwd=%s", pwd);

            // 使用DES 加密，key和iv都使用pwd
            // 根据pwd，生成DES加密后的密钥，SecretKeySpec对象
            SecretKeySpec secretKey = new SecretKeySpec(pwd.getBytes(), "DES");

            // 根据pwd，创建一个初始化向量IvParameterSpec对象
            IvParameterSpec iv = new IvParameterSpec(pwd.getBytes());

            // 创建密码器，参数：算法/模式/填充
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用key和iv初始化密码器，参1：opmode，操作模式-加密、解密等。
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            // 执行（加密）并返回结果（字节数组）
            byte[] resultBytes = cipher.doFinal(data.getBytes("UTF-8"));

            // 转换成十六进制（大写）
            return bytes2Hex(resultBytes).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param data      要解密的数据
     * @param key       密钥
     */
    private static String decrypt(String data, String key) {
        try {
            // 把加密的十六进制字符串数据转换成字节数组
            int len = data.length() >> 1;
            byte[] dataBytes = new byte[len];
            for (int i=0; i<len; i++) {
                int index = i << 1;
                dataBytes[i] = (byte)Integer.parseInt(data.substring(index, index + 2), 16);
            }

            // 获取key的MD5值
            byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(key.getBytes());
            String pwd = bytes2Hex(md5Bytes).toUpperCase().substring(0, 8);

            // 创建key和iv
            SecretKeySpec secretKey = new SecretKeySpec(pwd.getBytes(), "DES");
            IvParameterSpec iv = new IvParameterSpec(pwd.getBytes());

            // DES 解密
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] resultBytes = cipher.doFinal(dataBytes);

            return new String(resultBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转换成十六进制字符串
     */
    private static String bytes2Hex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder resultSB = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() < 2) {
                resultSB.append("0");
            }
            resultSB.append(hex);
        }
        return resultSB.toString();
    }

    private static void println(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
}
