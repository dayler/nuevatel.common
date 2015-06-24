package com.nuevatel.common.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author asalazar
 */
public class IOUtils {

    public static byte[] inputStreamToByteArray(InputStream is) throws IOException {

        ByteArrayOutputStream os = null;

        try {
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1; ) {
                os.write(buffer, 0, len);
            }

            os.flush();

            return os.toByteArray();
        } catch (IOException ex) {
            return null;
        } finally {
            if (os != null) {
                os.close();
            }

            // Safe close, is cannot be null
            is.close();
        }
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        return new String(inputStreamToByteArray(is));
    }
}
