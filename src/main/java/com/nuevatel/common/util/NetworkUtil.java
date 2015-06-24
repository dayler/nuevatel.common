/**
 * 
 */
package com.nuevatel.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author dayler
 *
 */
public final class NetworkUtil {

    private NetworkUtil() {
        // No op
    }

    public static String getHardwareAddress() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        byte[] mac = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }

        return sb.toString();
    }

    public static String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
}
