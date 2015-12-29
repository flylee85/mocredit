package cn.mocredit.gateway.util;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import static java.security.KeyFactory.getInstance;
import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHexString;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String CHARSET_NAME = "UTF-8";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static Logger logger = getLogger(RSACoder.class);

    /**
     * 用私钥解密
     *
     * @param data 16进制字符串的密文
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String key) {
        try {
            // 对密钥解密
            byte[] keyBytes = decodeHex(key.toCharArray());

            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = getInstance(KEY_ALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return new String(cipher.doFinal(decodeHex(data.toCharArray())), CHARSET_NAME);
        } catch (Exception e) {
            String stackTrace = getStackTrace(e);
            logger.error(stackTrace);
            return stackTrace;
        }
    }

    /**
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decodeHex(key.toCharArray());

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return new String(cipher.doFinal(decodeHex(data.toCharArray())), CHARSET_NAME);
    }

    /**
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return 16进制字符串的密文
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String key) {
        try {
            // 对公钥解密
            byte[] keyBytes = decodeHex(key.toCharArray());

            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = getInstance(KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return encodeHexString(cipher.doFinal(data.getBytes(CHARSET_NAME)));
        } catch (Exception e) {
            String stackTrace = getStackTrace(e);
            logger.error(stackTrace);
            return stackTrace;
        }
    }

    /**
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return 16进制字符串的密文
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decodeHex(key.toCharArray());

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return encodeHexString(cipher.doFinal(data.getBytes(CHARSET_NAME)));
    }

    /**
     * 初始化密钥
     *
     * @return 16进制字符串的公钥和私钥
     * @throws Exception
     */
    public static Map<String, String> initKey() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        Map<String, String> ret = new HashMap<>(2);
        ret.put(PUBLIC_KEY, encodeHexString(keyPair.getPublic().getEncoded()));
        ret.put(PRIVATE_KEY, encodeHexString(keyPair.getPrivate().getEncoded()));
        return ret;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> keyMap = initKey();
        String publicKey = keyMap.get(PUBLIC_KEY);
        String privateKey = keyMap.get(PRIVATE_KEY);
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        String data = "39730E9D0F2D4368ACEC8610C3221443";

        System.out.println("公钥加密——私钥解密");
        String encodedData = encryptByPublicKey(data, publicKey);
        String decodedData = decryptByPrivateKey(encodedData, privateKey);
        System.out.println("加密前： " + data);
        System.out.println("加密后： " + encodedData);
        System.out.println("解密后： " + decodedData);

        System.out.println("私钥加密——公钥解密");
        String encryptByPrivateKey = encryptByPrivateKey(data, privateKey);
        String decryptByPublicKey = decryptByPublicKey(encryptByPrivateKey, publicKey);
        System.out.println("加密前： " + data);
        System.out.println("加密后： " + encryptByPrivateKey);
        System.out.println("解密后： " + decryptByPublicKey);
    }
}
