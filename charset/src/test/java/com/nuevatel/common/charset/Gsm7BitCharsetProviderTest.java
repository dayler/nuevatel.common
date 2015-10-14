package com.nuevatel.common.charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.*;

/**
 * Created by asalazar on 10/14/15.
 */
public class Gsm7BitCharsetProviderTest {
    @Before
    public void setUp() throws Exception {
        // No op
    }

    @After
    public void tearDown() throws Exception {
        // No op
    }

    @SuppressWarnings("InjectedReferences")
    @Test
    public void fromName() {
        Charset charset = Charset.forName("X-Gsm7Bit");
        assertNotNull("Gsm7bitCharset is null", charset);
        assertTrue("it is not an instance of Gsm7BitCharset", charset instanceof Gsm7BitCharset);
    }
}