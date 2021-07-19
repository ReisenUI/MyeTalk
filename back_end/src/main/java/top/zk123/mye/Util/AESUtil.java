package top.zk123.mye.Util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES 加密工具
 *
 * @author Ke Zhange
 * @since 1.0
 */
public class AESUtil {
    public static final String key = "6a7cb6c05da3eda52d9397bf6dd6e0bd";

    public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
            return Hex.encodeHexString(byteKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 加密
     *
     * @param thisKey 密钥
     * @param data    plainText
     * @return String
     */
    public static String encode(String thisKey, String data) {
        try {
            // 转换KEY
            Key key = new SecretKeySpec(Hex.decodeHex(thisKey), "AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(data.getBytes());
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param thisKey 密钥
     * @param data    plainText
     * @return String
     */
    public static String decode(String thisKey, String data) {
        try {
            // 转换KEY
            Key key = new SecretKeySpec(Hex.decodeHex(thisKey), "AES");
            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Hex.decodeHex(data));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
