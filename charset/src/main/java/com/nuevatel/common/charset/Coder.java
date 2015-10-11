package com.nuevatel.common.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Convert input data from specific codification to other specific codification.
 * <br/><br/>
 * Coder c = new Coder();<br/>
 * // Transform data(UTF-8 codification) to out-data(UTF-16)<br/>
 * byte[] outdata = c.from(indata, "UTF-8").to("UTF-16")<br/>
 * <br/>
 *
 * @see <a href="Supported Encodings">https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html</a>
 *
 * @author Ariel Salazar
 */
public class Coder {

    public Coder.Transformer from(byte[] in, String charsetName) throws CharacterCodingException {
        if (in == null || charsetName == null) {
            throw new IllegalArgumentException("in and charsetName != null");
        }

        Coder.Transformer tr = new Transformer();
        ByteBuffer buffer = ByteBuffer.wrap(in);
        return tr.toUnicode(buffer, charsetName);
    }

    /**
     * Used has intermediate step to convert a condign type to other coding type.
     */
    public final static class Transformer {

        private CharBuffer unicode = null;

        Transformer toUnicode(ByteBuffer buffer, String charsetName) throws CharacterCodingException {
            Charset charset = Charset.forName(charsetName);
            CharsetDecoder decoder = charset.newDecoder();
            unicode = decoder.decode(buffer);
            return this;
        }

        public byte[] to(String charsetName) throws CharacterCodingException {
            if (charsetName == null) {
                throw  new IllegalArgumentException("charsetName != null");
            }

            //ByteBuffer
            if (unicode == null) {
                return null;
            }

            Charset charset = Charset.forName(charsetName);
            CharsetEncoder encoder = charset.newEncoder();
            ByteBuffer buffer = encoder.encode(unicode);
            return buffer.array();
        }
    }
}
