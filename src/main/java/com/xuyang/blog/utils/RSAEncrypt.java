package com.xuyang.blog.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
/**
 * @Author: XuYang
 * @Date: 2020/6/23 14:54
 * @Description:
 */

public class RSAEncrypt {
    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCG4LwP7AcMdRscjf4jL8JdtN/3q/pXsZEEfk7tCHdRifzKJ/J+Ydp9zBpXcnY/PtwdA0d7qjzDLZTGtMbWshpKA/T7/BcWWab5DbDtbEpOyHD7hO9WEPMb/0aXn+hNWSLFr0wJthl1SNg+XGI/kCSgaS3POcnm6Ct8CpPhlwZZiwIDAQAB";// 公钥
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIbgvA/sBwx1GxyN/iMvwl203/er+lexkQR+Tu0Id1GJ/Mon8n5h2n3MGldydj8+3B0DR3uqPMMtlMa0xtayGkoD9Pv8FxZZpvkNsO1sSk7IcPuE71YQ8xv/Rpef6E1ZIsWvTAm2GXVI2D5cYj+QJKBpLc85yeboK3wKk+GXBlmLAgMBAAECgYA4JLQjrIwCk3yFllWDMA4oE3JnFh9PAYrr4+fWov+H4Xhobdhy4yif1KXiYLL4AKJ/MS5AO8yvMfoL/JQIKnrmVjYroHNI9uQhmRMIVzWkoJ/CF0DY2BtFU93nMRXC30QpuR5rWfedKg4evBU7YGT2jgCpOatuwaJVim5FR1ukgQJBAOWDpDJ7JEhaaD/my1QJT9ft2XEQ8ZJIyfREv4wNTZz66+sWFPhBXYJEjIER8e+7BJl2ZtFpIeYeyawAxtySbM0CQQCWcVNfA+ipuaR/0e1ygsyU4bM0H2e2yBLW4EHwv+940u72bvhEMpB6WME53mwNt1HC4uHt9vy9bB9n/4NT5N+3AkEAhVOms+iTGDDktJJm9Yd/SWmTUjpMivUb1HmHaF1mA9ZD7Enknp6iKoWVtXjuOZxDvL5qViYjmiGyd+fvnby11QJAU4s3Syp2h9pJ2ZmQjOjKB1uXErjl9YEBxUXgGHvd1nD8tulFAUo7JVYoZ6R2yN8mjl1ELcvc6qwifLROD8an3QJAEPgvqwbTmGvpAqEk5GjN41YPigOZNpQEbe52I3/LYZc4LyQysEpomWtSrzGOQct4ub0yFYZqSMkclBXQbncIzQ==";//私钥

    public static void main(String args[]) throws Exception {
        String password = "123456";
        System.out.println(encrypt(password));
        try{
            String n = "f2D0itW5q8XndGMmUJCXfNJD+QB2ZCxGQb3BE/Fd+BuS6tiJEsxWgEMuduubh9F5ZUCiDETDQiDeAiOarvoUajimDfRaaFJe3LfmxHYdJJAOJeL01V+Wem/44cWVQXF/ZBDtFSSB5Wfm+QWLDqNrk05AXPuLoCiTsba2YtMtBmE=";
            System.out.println(decrypt(n));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}