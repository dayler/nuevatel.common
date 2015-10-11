package com.nuevatel.common.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by asalazar on 10/11/15.
 */
public class Gsm7BitCharset extends Charset {
    /**
     * Initializes a new charset with the given canonical name and alias
     * set.
     *
     * @param canonicalName The canonical name of this charset
     * @param aliases       An array of this charset's aliases, or null if it has no aliases
     * @throws IllegalCharsetNameException If the canonical name or any of the aliases are illegal
     */
    protected Gsm7BitCharset(String canonicalName, String[] aliases) {
        super(canonicalName, aliases);
    }

    @Override
    public boolean contains(Charset cs) {
        return false;
    }

    @Override
    public CharsetDecoder newDecoder() {
        return null;
    }

    @Override
    public CharsetEncoder newEncoder() {
        return null;
    }
}
