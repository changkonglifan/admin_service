package com.xuyang.admin.utils;

import com.xuyang.admin.controller.Admin.AdminAccountController;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XuYang
 * @Date: 2020/6/23 14:54
 * @Description:
 */

public class RSAEncrypt {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWbozYSQ6u7f50JDhcVlTEf7ct9dW3kqrIINgfbPV2iKaa9SFv2gYArOhAgajXqgpX4+6CIdqFCtZPL8Znkv7s6cUtMktqBfCeVgv0Q8ehH/CCQeB480MEN17NG6tenraxRCgAEmwZUpg0apW9amDUDLIt8is/cIpANWbiyQL8cQIDAQAB";// 公钥
    private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJZujNhJDq7t/nQkOFxWVMR/ty311beSqsgg2B9s9XaIppr1IW/aBgCs6ECBqNeqClfj7oIh2oUK1k8vxmeS/uzpxS0yS2oF8J5WC/RDx6Ef8IJB4HjzQwQ3Xs0bq16etrFEKAASbBlSmDRqlb1qYNQMsi3yKz9wikA1ZuLJAvxxAgMBAAECgYAsRVaP5Fg85O+IkIxBqGqD9a4cCYge4Tv/b6MLBI7slmSpn9B6UWiI1Fn5ee8NoQ4wNXUguQPNOZY+canrG1iIPtkYmACJ4Sf7P8pr2KMPZx5i3uB/qAlwfSqAXbJinDmE0vP+23cShlbC6BAgrxT82A5lVRz9l7sNS036fkaabQJBAM0trX1rKmWktQjmshAp1z+y+jdEykhpgL/02dOd1hF/stc9yjkwJ6yqczQXtHbmmlvHO8kkipMhClP3B6eSdnMCQQC7sWb6L9KoRosoW0xvOj03s0BgQU+0MVXTQE4lpu11pzfP/QVeZ63iENUf/mqm+aK8CZoSlxid2bNOuYnDOaSLAkBuo88F7p/yJ5snrZ8fcDCOPOkInKOn4Jx45oP/xQEGdqHtx3gg1oLObSuqT44U/80E6K6ojHfdVtfG51LNXgFZAkA80L+QIzy7n5fpbKO4ioNZb/BU//SGa1Hm0DhCBPN+Ir932gEKVMfHrzKPWk1OayaqjOXqTiEAzJBH2uBjbGL/AkAOtEcjCebyaiaGlv15XS0hxpw25Tk44MvoMbLMmS3BAMTOwGLN4QPdLwMp6O1F+YzJI9RLvLMN9JZHfOZ2F+wy";//私钥

    public static void main(String args[]) throws Exception {
        String s="caicai";
        Map<Integer, String> map = RSAEncrypt.genKeyPair();
        System.out.println("公钥："+ map.get(0));
        System.out.println("私钥："+ map.get(1));
    }
    /**
     * 随机生成密钥对
     */
    public static Map<Integer, String> genKeyPair() {

        Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024,new SecureRandom());

            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

            // 得到公钥字符串
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));

            // 将公钥和私钥保存到Map
            keyMap.put(0,publicKeyString);  //0表示公钥
            keyMap.put(1,privateKeyString);  //1表示私钥
        } catch (Exception e) {
            logger.info("生成公钥私钥异常："+e.getMessage());
            return null;
        }

        return keyMap;
    }
    /**
     * RSA公钥加密
     * @param str  需要加密的字符串
     * @param publicKey  公钥
     * @return 公钥加密后的内容
     */
    public static String encrypt( String str, String publicKey ){
        String outStr=null;
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.info("使用公钥加密【"+str+"】异常："+e.getMessage());
        }
        return outStr;
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
    /**
     * RSA私钥解密
     * @param str  加密字符串
     * @param privateKey  私钥
     * @return 私钥解密后的内容
     */
    public static String decrypt(String str, String privateKey){
        String outStr=null;
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            logger.info("使用私钥解密异常："+e.getMessage());
        }
        return outStr;
    }

}