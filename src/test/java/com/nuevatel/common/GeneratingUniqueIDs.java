package com.nuevatel.common;

import com.nuevatel.common.util.UniqueID;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by dayler on 6/23/15.
 */
public class GeneratingUniqueIDs {

    public static void main (String... arguments) throws NoSuchAlgorithmException {
        UniqueID uniqueID = new UniqueID();
        for (int i = 0; i < 100; i++) {
            System.out.println("## " + UniqueID.hexEncode(uniqueID.next(16)));
        }
    }
//    public static void main (String... arguments) {
//        try {
//            //Initialize SecureRandom
//            //This is a lengthy operation, to be done only upon
//            //initialization of the application
//            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
//            byte[] bytes = new byte[8];
//            prng.nextBytes(bytes);
//
//            //generate a random number
//            String randomNum = new Integer(prng.nextInt()).toString();
//
//            //get its digest
//            MessageDigest sha = MessageDigest.getInstance("SHA-1");
//            byte[] result =  sha.digest(randomNum.getBytes());
////            byte[] result =  sha.digest(bytes);
//
////            System.out.println("Random number: " + randomNum);
//            System.out.println("##### " + toString(bytes));
//            System.out.println("Bytes: " + hexEncode(bytes));
//            System.out.println("Message digest: " + hexEncode(result));
//        }
//        catch (NoSuchAlgorithmException ex) {
//            System.err.println(ex);
//        }
//    }

    private static String toString(byte[] in) {
        StringBuilder sb = new StringBuilder();
        for (byte b : in) {
            sb.append(b);
        }
        return sb.toString();
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
    static private String hexEncode(byte[] aInput){
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
