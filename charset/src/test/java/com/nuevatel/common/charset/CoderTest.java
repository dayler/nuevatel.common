package com.nuevatel.common.charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by asalazar on 10/11/15.
 */
public class CoderTest {

    private static final String FROM_CHARSET_NAME = "UTF-8";
    private static final String TO_CHARSET_NAME = "UTF-16BE";
    private static final String TEXT = "Unicode";
    private static final byte[] UTF8_TEXT = {85, 110, 105, 99, 111, 100, 101};
    private static final byte[] UTF16_TEXT = {0, 85, 0, 110, 0, 105, 0, 99, 0, 111, 0, 100, 0, 101};

    private Coder coder;

    @Before
    public void setUp() throws Exception {
        coder = new Coder();
    }

    @After
    public void teardown() {
        coder = null;
    }

    /**
     * Test entry flow: from code to code
     * @throws Exception
     */
    @Test
    public void fromTo() throws Exception {
        byte[] data = coder.from(TEXT.getBytes(), FROM_CHARSET_NAME).to(TO_CHARSET_NAME);
        assertNotNull("data is null", data);
        assertEquals("length not match", UTF16_TEXT.length, data.length);

        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            assertEquals("char not match " + b + " index " + i, b, UTF16_TEXT[i]);
        }

        byte[] data2  = coder.from(data, TO_CHARSET_NAME).to(FROM_CHARSET_NAME);
        assertNotNull("data2 is null", data2);
        assertEquals("length not match", UTF8_TEXT.length, data2.length);

        for (int i = 0; i < data2.length; i++) {
            byte b = data2[i];
            assertEquals("char not match " + b + " index " + i, b, UTF8_TEXT[i]);
        }
    }
}