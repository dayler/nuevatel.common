/**
 * 
 */
package com.nuevatel.common.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.nuevatel.common.util.Parameters;

/**
 * @author dayler
 *
 */
public final class FileUtils {

    private FileUtils() {
        // No op. Used to prevent instantiation.
    }

    /**
     *  Rename origin file to toFile.
     * 
     * @param origin File to rename.
     * @param toFile Destination file.
     * @param overwrite <b>true</b> to overwrite destination file.
     * @return <b>true</b> if files was renamed.
     * @throws IOException if origin does not exists (only if overwrite is false) or toFile already exists.
     */
    public static boolean rename(File origin, File toFile, boolean overwrite) throws IOException {
        if (!origin.exists()) {
            throw new IOException(origin + " does not exists.");
        }

        if (toFile.exists() && !overwrite) {
            throw new IOException(toFile + " already exists.");
        }

        return origin.renameTo(toFile);
    }

    /**
     *  Rename origin file to toFile.
     * 
     * @param origin File to rename.
     * @param toFile Destination file.
     * @return <b>true</b> if files was renamed.
     * @throws IOException if origin does not exists or toFile already exists.
     */
    public static boolean rename(File origin, File toFile) throws IOException {
        return rename(origin, toFile, false);
    }

    /**
     * Rename origin file to toFile.
     * 
     * @param origin Relative path of file to rename.
     * @param toFile Destination file.
     * @return <b>true</b> if files was renamed.
     * @throws IOException if origin does not exists or toFile already exists.
     */
    public static boolean rename(String origin, String toFile) throws IOException{
        File ori = new File(origin);
        File dest = new File(toFile);
        return rename(ori, dest, false);
    }

    /**
     * 
     * @return User home path.
     */
    public static String getUserHomePath() {
        return System.getProperty("user.home");
    }

    /**
     * Copy two files in the destination file. Overwrite destination file if it exists. This method
     * is implemented for Java 6, if is using java 7 or latter use <b>File.copy(f1, f2)</b>
     * 
     * @param file File to copy.
     * @param copyTo Destination file.
     * @throws IOException If the Origin file does not exists or the copy operation could not be 
     * performed.
     */
    public static void copyFile(File file, File copyTo) throws IOException {
        Parameters.checkNull(file, "origin");
        Parameters.checkNull(copyTo, "destination");

        FileChannel fileCahnnel = null;
        FileChannel copyToChannel = null;
        FileInputStream inFile = null;
        FileOutputStream osCopyTo = null;

        // Ensure target directory exists.
        File targetDir = copyTo.getParentFile();
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }

        try {
            inFile = new FileInputStream(file);
            fileCahnnel = inFile.getChannel();
            osCopyTo = new FileOutputStream(copyTo, false);
            copyToChannel = osCopyTo.getChannel();
            long bytesTransferred = 0;
            // Defensive loop.
            while (bytesTransferred < fileCahnnel.size()) {
                bytesTransferred += fileCahnnel.transferTo(0, fileCahnnel.size(), copyToChannel);
            }
        } finally {
            if (fileCahnnel != null) {
                fileCahnnel.close();
            }

            if (copyToChannel != null) {
                copyToChannel.close();
            }

            if (inFile != null) {
                inFile.close();
            }

            if (osCopyTo != null) {
                osCopyTo.close();
            }
        }
    }
}
