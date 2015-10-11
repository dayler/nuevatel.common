package com.nuevatel.common.charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import static org.junit.Assert.*;

/**
 * Created by asalazar on 10/11/15.
 */
public class Gsm7BitCharsetTest {

    private static final String UTF8_TEXT = "CD0123456789ABCDABCDABCD987654321123x141517";
    private static final byte[] GSM7_TEXT_DATA = {
                                                0x06, 0x05, 0x04, 0x15, 0x79, 0x00, 0x00, 0x52, 0x45, 0x47, 0x2d, 0x52,
                                                0x45, 0x53, 0x50, 0x3f, 0x76, 0x3d, 0x33, 0x3b, 0x72, 0x3d, 0x66, 0x66,
                                                0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x3b, 0x6e, 0x3d, 0x2b, 0x35, 0x39,
                                                0x31, 0x37, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x31, 0x3b, 0x73, 0x3d,
                                                0x4f, 0x4d, 0x54, 0x45, 0x53, 0x54, 0x31, 0x32, 0x33, 0x34, 0x41, 0x42,
                                                0x43, 0x44, 0x41, 0x42, 0x43, 0x44, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35,
                                                0x36, 0x37, 0x38, 0x39, 0x41, 0x42, 0x43, 0x44, 0x41, 0x42, 0x43, 0x44,
                                                0x41, 0x42, 0x43, 0x44, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32,
                                                0x31, 0x31, 0x32, 0x33, 0x78, 0x31, 0x34, 0x31, 0x35, 0x31, 0x37};

    private Gsm7BitCharset charset;

    @Before
    public void setUp() throws Exception {
        charset = new Gsm7BitCharset();
    }

    @After
    public void tearDown() throws Exception {
        charset = null;
    }

    @Test
    public void encode() throws CharacterCodingException {
        Charset utf8Charset = Charset.forName("UTF-8");
        ByteBuffer in = ByteBuffer.wrap(UTF8_TEXT.getBytes());
        CharsetDecoder utf8Decoder = utf8Charset.newDecoder();
        // out in unicode
        CharBuffer out = utf8Decoder.decode(in);
        // Encode to GSM-7
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer gsm7Data = encoder.encode(out);
        byte[]data = gsm7Data.array();
        // Verify encoding
        assertEquals("lengths not matches", GSM7_TEXT_DATA.length, data.length);
        for (int i = 0; i < data.length; i++) {
            byte gsmByte = data[i];
            assertEquals("not match at i=" + i + " gsmbyte=" + (gsmByte & 0xFF), GSM7_TEXT_DATA[i], gsmByte);
        }
    }

    @Test
    public void decode() throws CharacterCodingException {
        CharsetDecoder decoder = charset.newDecoder();
        ByteBuffer in = ByteBuffer.wrap(GSM7_TEXT_DATA);
        // decode out to unicode
        CharBuffer out = decoder.decode(in);
        assertNotNull("unicode out is null", out);
        // Try to encode to UTF-8
        Charset utf8Charset = Charset.forName("UTF-8");
        CharsetEncoder utf8Encoder = utf8Charset.newEncoder();
        ByteBuffer utf8Buffer = utf8Encoder.encode(out);
        String text = new String(utf8Buffer.array());
        assertEquals("UTF8_TEXT not matches", UTF8_TEXT, text);
    }

    @Test
    public void contains() throws Exception {
        fail("No impl.");
    }

    @Test
    public void newDecoder() throws Exception {
        fail("No impl.");
    }

    @Test
    public void newEncoder() throws Exception {
        fail("No impl.");
    }
}