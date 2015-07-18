package com.nuevatel.common.util.encoder;

import com.nuevatel.common.util.Parameters;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author clvelarde
 */
public final class Encoder {

    private static final String SHA_256 = "SHA-256";

    private static final String UTF_8 = "UTF-8";

    private static final String MD5 = "MD5";

    public static byte[] encodeSHA256(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        Parameters.checkBlankString(key, "key");

        MessageDigest messageSHA = MessageDigest.getInstance(SHA_256);
        messageSHA.update(key.getBytes(UTF_8));

        return messageSHA.digest();
    }

    public static String encodeSHA256ToHex(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        return bytesToHex(encodeSHA256(key));
    }

    public static String encodeBase64(byte[] messToEncode) {
        Parameters.checkNull(messToEncode, "messToEncode");

        return new BASE64Encoder().encode(messToEncode);
    }

    public static byte[] decodeBase64(String messToDecode) throws IOException {
        Parameters.checkBlankString(messToDecode, "messToDecode");

        return new BASE64Decoder().decodeBuffer(messToDecode);
    }

    public static byte[] encodeMD5(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(MD5);
        byte[] hash = md.digest(message.getBytes(UTF_8));

        return hash;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public static String encryptMD5(String password) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return generatedPassword;
    }
}
