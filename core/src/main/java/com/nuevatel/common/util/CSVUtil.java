/**
 * 
 */
package com.nuevatel.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dayler
 *
 */
public final class CSVUtil {

    public static final String SEPARATOR = ",";

    private CSVUtil() {
        // No op
    }

    public static List<String[]> read(String path, int ignoreLines) throws IOException {
        Parameters.checkBlankString(path, "path");

        InputStream in = null;

        try {
            in = new FileInputStream(path);
            return read(in, ignoreLines);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Read CSV input.
     *
     * @param in
     * @param ignoreLines
     * @param ignoreIfStartWith If line is <code>ignoreIfStartWith<code/> ignore line.
     */
    public static List<String[]>read(InputStream in, int ignoreLines, String ignoreIfStartWith) throws IOException {
        Parameters.checkNull(in, "in");

        Reader reader = null;

        try {
            reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String line = StringUtils.EMPTY;
            int count = 0;
            List<String[]> data = new ArrayList<String[]>();

            while ((line = br.readLine()) != null) {
                count++;
                if (ignoreIfStartWith != null &&
                    !ignoreIfStartWith.isEmpty() &&
                    line.startsWith(ignoreIfStartWith)) {
                    // Indicates the line is ignored. Comment line.
                    continue;
                }

                if (count > ignoreLines) {
                    String[] metadata = line.split(SEPARATOR);
                    data.add(metadata);
                }
            }

            return data;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Read CSV input.
     * 
     * @param in
     * @param ignoreLines
     * @return
     * @throws IOException
     */
    public static List<String[]>read(InputStream in, int ignoreLines) throws IOException {
        return read(in, ignoreLines, null);
    }
}
