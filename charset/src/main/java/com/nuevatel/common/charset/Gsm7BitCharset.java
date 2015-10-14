package com.nuevatel.common.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A Charset implementation for Gsm 7-bit default and extended character set
 * See GSM 03.38
 *
 * @author Sverker Abrahamsson, Ariel D. Salazar H.
 *
 * Created by asalazar on 10/11/15.
 */
public class Gsm7BitCharset extends Charset {

    private static Logger logger = Logger.getLogger(Gsm7BitCharset.class.getName());

    private static final Object[][] GSM_CHARACTERS = {
            { "@",      new Byte((byte) 0x00) },
            { "£",      new Byte((byte) 0x01) },
            { "$",      new Byte((byte) 0x02) },
            { "¥",      new Byte((byte) 0x03) },
            { "è",      new Byte((byte) 0x04) },
            { "é",      new Byte((byte) 0x05) },
            { "ù",      new Byte((byte) 0x06) },
            { "ì",      new Byte((byte) 0x07) },
            { "ò",      new Byte((byte) 0x08) },
            { "Ç",      new Byte((byte) 0x09) },
            { "\n",     new Byte((byte) 0x0a) },
            { "Ø",      new Byte((byte) 0x0b) },
            { "ø",      new Byte((byte) 0x0c) },
            { "\r",     new Byte((byte) 0x0d) },
            { "Å",      new Byte((byte) 0x0e) },
            { "å",      new Byte((byte) 0x0f) },
            { "\u0394", new Byte((byte) 0x10) },
            { "_",      new Byte((byte) 0x11) },
            { "\u03A6", new Byte((byte) 0x12) },
            { "\u0393", new Byte((byte) 0x13) },
            { "\u039B", new Byte((byte) 0x14) },
            { "\u03A9", new Byte((byte) 0x15) },
            { "\u03A0", new Byte((byte) 0x16) },
            { "\u03A8", new Byte((byte) 0x17) },
            { "\u03A3", new Byte((byte) 0x18) },
            { "\u0398", new Byte((byte) 0x19) },
            { "\u039E", new Byte((byte) 0x1a) },
            { "\u001B", new Byte((byte) 0x1b) }, // 27 is Escape character
            { "Æ",      new Byte((byte) 0x1c) },
            { "æ",      new Byte((byte) 0x1d) },
            { "ß",      new Byte((byte) 0x1e) },
            { "É",      new Byte((byte) 0x1f) },
            { "\u0020", new Byte((byte) 0x20) },
            { "!",      new Byte((byte) 0x21) },
            { "\"",     new Byte((byte) 0x22) },
            { "#",      new Byte((byte) 0x23) },
            { "¤",      new Byte((byte) 0x24) },
            { "%",      new Byte((byte) 0x25) },
            { "&",      new Byte((byte) 0x26) },
            { "'",      new Byte((byte) 0x27) },
            { "(",      new Byte((byte) 0x28) },
            { ")",      new Byte((byte) 0x29) },
            { "*",      new Byte((byte) 0x2a) },
            { "+",      new Byte((byte) 0x2b) },
            { ",",      new Byte((byte) 0x2c) },
            { "-",      new Byte((byte) 0x2d) },
            { ".",      new Byte((byte) 0x2e) },
            { "/",      new Byte((byte) 0x2f) },
            { "0",      new Byte((byte) 0x30) },
            { "1",      new Byte((byte) 0x31) },
            { "2",      new Byte((byte) 0x32) },
            { "3",      new Byte((byte) 0x33) },
            { "4",      new Byte((byte) 0x34) },
            { "5",      new Byte((byte) 0x35) },
            { "6",      new Byte((byte) 0x36) },
            { "7",      new Byte((byte) 0x37) },
            { "8",      new Byte((byte) 0x38) },
            { "9",      new Byte((byte) 0x39) },
            { ":",      new Byte((byte) 0x3a) },
            { ";",      new Byte((byte) 0x3b) },
            { "<",      new Byte((byte) 0x3c) },
            { "=",      new Byte((byte) 0x3d) },
            { ">",      new Byte((byte) 0x3e) },
            { "?",      new Byte((byte) 0x3f) },
            { "¡",      new Byte((byte) 0x40) },
            { "A",      new Byte((byte) 0x41) },
            { "B",      new Byte((byte) 0x42) },
            { "C",      new Byte((byte) 0x43) },
            { "D",      new Byte((byte) 0x44) },
            { "E",      new Byte((byte) 0x45) },
            { "F",      new Byte((byte) 0x46) },
            { "G",      new Byte((byte) 0x47) },
            { "H",      new Byte((byte) 0x48) },
            { "I",      new Byte((byte) 0x49) },
            { "J",      new Byte((byte) 0x4a) },
            { "K",      new Byte((byte) 0x4b) },
            { "L",      new Byte((byte) 0x4c) },
            { "M",      new Byte((byte) 0x4d) },
            { "N",      new Byte((byte) 0x4e) },
            { "O",      new Byte((byte) 0x4f) },
            { "P",      new Byte((byte) 0x50) },
            { "Q",      new Byte((byte) 0x51) },
            { "R",      new Byte((byte) 0x52) },
            { "S",      new Byte((byte) 0x53) },
            { "T",      new Byte((byte) 0x54) },
            { "U",      new Byte((byte) 0x55) },
            { "V",      new Byte((byte) 0x56) },
            { "W",      new Byte((byte) 0x57) },
            { "X",      new Byte((byte) 0x58) },
            { "Y",      new Byte((byte) 0x59) },
            { "Z",      new Byte((byte) 0x5a) },
            { "Ä",      new Byte((byte) 0x5b) },
            { "Ö",      new Byte((byte) 0x5c) },
            { "Ñ",      new Byte((byte) 0x5d) },
            { "Ü",      new Byte((byte) 0x5e) },
            { "§",      new Byte((byte) 0x5f) },
            { "¿",      new Byte((byte) 0x60) },
            { "a",      new Byte((byte) 0x61) },
            { "b",      new Byte((byte) 0x62) },
            { "c",      new Byte((byte) 0x63) },
            { "d",      new Byte((byte) 0x64) },
            { "e",      new Byte((byte) 0x65) },
            { "f",      new Byte((byte) 0x66) },
            { "g",      new Byte((byte) 0x67) },
            { "h",      new Byte((byte) 0x68) },
            { "i",      new Byte((byte) 0x69) },
            { "j",      new Byte((byte) 0x6a) },
            { "k",      new Byte((byte) 0x6b) },
            { "l",      new Byte((byte) 0x6c) },
            { "m",      new Byte((byte) 0x6d) },
            { "n",      new Byte((byte) 0x6e) },
            { "o",      new Byte((byte) 0x6f) },
            { "p",      new Byte((byte) 0x70) },
            { "q",      new Byte((byte) 0x71) },
            { "r",      new Byte((byte) 0x72) },
            { "s",      new Byte((byte) 0x73) },
            { "t",      new Byte((byte) 0x74) },
            { "u",      new Byte((byte) 0x75) },
            { "v",      new Byte((byte) 0x76) },
            { "w",      new Byte((byte) 0x77) },
            { "x",      new Byte((byte) 0x78) },
            { "y",      new Byte((byte) 0x79) },
            { "z",      new Byte((byte) 0x7a) },
            { "ä",      new Byte((byte) 0x7b) },
            { "ö",      new Byte((byte) 0x7c) },
            { "ñ",      new Byte((byte) 0x7d) },
            { "ü",      new Byte((byte) 0x7e) },
            { "à",      new Byte((byte) 0x7f) }
    };

    private static final Object[][] GSM_EXTENSION_CHARACTERS = {
            { "\n", new Byte((byte) 0x0a) },
            { "^",  new Byte((byte) 0x14) },
            { " ",  new Byte((byte) 0x1b) }, // reserved for future extensions
            { "{",  new Byte((byte) 0x28) },
            { "}",  new Byte((byte) 0x29) },
            { "\\", new Byte((byte) 0x2f) },
            { "[",  new Byte((byte) 0x3c) },
            { "~",  new Byte((byte) 0x3d) },
            { "]",  new Byte((byte) 0x3e) },
            { "|",  new Byte((byte) 0x40) },
            { "",  new Byte((byte) 0x65) }
    };

    // HashMap's used for encoding and decoding
    private static Map<String, Byte>defaultEncodeMap = new HashMap<>();
    private static Map<Byte, String>defaultDecodeMap = new HashMap<>();

    private static Map<String, Byte>extEncodeMap = new HashMap<>();
    private static Map<Byte, String>extDecodeMap = new HashMap<>();

    static {
        // default alphabet
        for (Object[] map : GSM_CHARACTERS) {
            // Set encoder map
            defaultEncodeMap.put((String) map[0], (Byte) map[1]);
            // Set decoder map
            defaultDecodeMap.put((Byte) map[1], (String) map[0]);
        }

        // extended alphabet
        for (Object[] map : GSM_EXTENSION_CHARACTERS) {
            // set encoder
            extEncodeMap.put((String) map[0], (Byte) map[1]);
            // set decoder
            extDecodeMap.put((Byte) map[1], (String) map[0]);
        }
    }

    /**
     * Constructor for the Gsm7Bit charset.  Call the superclass
     * constructor to pass along the name(s) we'll be known by.
     * Then save a reference to the delegate Charset.
     *
     * Initializes a new charset with the given canonical name and alias
     * set.
     *
     * @param canonicalName The canonical name of this charset
     * @param aliases       An array of this charset's aliases, or null if it has no aliases
     * @throws java.nio.charset.IllegalCharsetNameException If the canonical name or any of the aliases are illegal
     */
    protected Gsm7BitCharset(String canonicalName, String[] aliases) {
        super(canonicalName, aliases);
    }

    /**
     * This method must be implemented by concrete Charsets. Always return false.
     *
     * @param cs
     *
     * @return The Charset is not contained.
     */
    @Override
    public boolean contains(Charset cs) {
        // Always false.
        return false;
    }

    @Override
    public CharsetDecoder newDecoder() {
        return new Gsm7BitDecoder(this);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return new Gsm7BitEncoder(this);
    }

    /**
     * The encoder implementation for the Gsm7Bit Charset.
     * This class, and the matching decoder class below, should also
     * override the "impl" methods, such as implOnMalformedInput() and
     * make passthrough calls to the baseEncoder object.  That is left
     * as an exercise for the hacker.
     */
    private static final class Gsm7BitEncoder extends CharsetEncoder {

        /**
         * Constructor, call the superclass constructor with the
         * Charset object and the encodings sizes from the
         * delegate encoder.
         */
        protected Gsm7BitEncoder(Charset cs) {
            super(cs, 1, 2);
        }

        /**
         * Implements encoding loop.
         */
        @Override
        protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
            while (in.hasRemaining() && out.hasRemaining()) {
                // Char in string
                String ch = String.valueOf(in.get());

                // first check the default alphabet
                Byte b = defaultEncodeMap.get(ch);
                if (b != null) {
                    out.put(b.byteValue());
                    continue;
                }

                // check extended alphabet, if the ch was not found in default alphabet.
                b = extEncodeMap.get(ch);
                if (b == null) {
                    // no match found, send a ?
                    b = new Byte((byte) 0x3F);
                    out.put(b.byteValue());
                } else {
                    // since the extended character set takes two bytes
                    // we have to check that there is enough space left
                    if (out.remaining() < 2) {
                        // go back one step
                        in.position(in.position() - 1);
                        // break;
                        return CoderResult.OVERFLOW;
                    }
                    // all ok, add it to the buffer
                    // two bytes to represent an extended char.
                    out.put((byte) 0x1b);
                    out.put(b.byteValue());
                }
            }

            return CoderResult.UNDERFLOW;
        }
    }

    /**
     * The decoder implementation for the Gsm 7Bit Charset.
     */
    private static final class Gsm7BitDecoder extends CharsetDecoder {

        /**
         * Constructor, call the superclass constructor with the
         * Charset object and pass alon the chars/byte values
         * from the delegate decoder.
         *
         * Initializes a new decoder.  The new decoder will have the given
         * chars-per-byte values and its replacement will be the
         * string <tt>"&#92;uFFFD"</tt>.
         *
         * @param cs                  The charset that created this decoder
         * @throws IllegalArgumentException If the preconditions on the parameters do not hold
         */
        protected Gsm7BitDecoder(Charset cs) {
            super(cs, 1, 1);
        }

        /**
         * Implementation of the decoding loop.
         */
        @Override
        protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
            while (in.hasRemaining() && out.hasRemaining()) {
                Byte b = in.get();
                // first check the default alphabet
                String decodedStr = defaultDecodeMap.get(b);
                if (decodedStr != null) {
                    char ch = decodedStr.charAt(0);
                    if (ch == '\u001B') {
                        // check the extended alphabet
                        if (in.hasRemaining()) {
                            b = in.get();
                            decodedStr = extDecodeMap.get(b);
                            if (decodedStr != null) {
                                ch = decodedStr.charAt(0);
                                out.put(ch);
                            } else {
                                // Unknown
                                out.put('?');
                            }
                        }
                    } else {
                        // Set decoded char if the char is not extended
                        out.put(ch);
                    }

                    continue;
                }
                // Unknown
                out.put('?');
            }

            return CoderResult.UNDERFLOW;
        }
    }
}
