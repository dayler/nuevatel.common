package com.nuevatel.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Generates unique ID.
 *
 * @author Ariel Salazar
 */
public class UniqueID {

    private final static String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

    private final static String DIGEST_ALGORITHM = "SHA-1";

    private SecureRandom prng;

    private MessageDigest sha;

    public UniqueID() throws NoSuchAlgorithmException {
        prng = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
        sha = MessageDigest.getInstance(DIGEST_ALGORITHM);
    }

    /**
     *
     * @param length Length for the bit array.
     * @return Next random value.
     */
    public byte[] next(int length) {
        byte[] randomBytes = new byte[length];
        prng.setSeed(System.currentTimeMillis());
        prng.nextBytes(randomBytes);
        return randomBytes;
    }

    /**
     *
     * @return Next integer random value.
     */
    public Integer nextInt() {
        prng.setSeed(System.currentTimeMillis());
        return new Integer(prng.nextInt());
    }
    
    /**
     *
     * @param bound Max value for integer.
     * @return Next integer random value.
     */
    public Integer nextInt(int bound) {
        prng.setSeed(System.currentTimeMillis());
        return new Integer(prng.nextInt(bound));
    }

    /**
     * 
     * @return Next long random value.
     */
    public Long nextLong() {
        prng.setSeed(System.currentTimeMillis());
        return new Long(prng.nextLong());
    }
    
    /**
     *
     * @param input Input to hash
     * @return Hashed input.
     */
    public byte[] digest(byte[] input) {
        return sha.digest(input);
    }

    /**
     * The byte[] returned by MessageDigest does not have a nice
     * textual representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book
     * "Java In A Nutshell", and converts a byte array into a String
     * of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     */
    public static String hexEncode(byte[] aInput){
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b&0xf0) >> 4 ]);
            result.append(digits[ b&0x0f]);
        }
        return result.toString();
    }
}
