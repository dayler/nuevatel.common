package com.nuevatel.common.wsi;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>The WSIPublisher class.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public class WSIPublisher {

    /* constants for properties */
    public static final String BIND_ADDRESS = "wsi.publisher.bindAddress";
    public static final String PORT = "wsi.publisher.port";
    public static final String BACKLOG = "wsi.publisher.backlog";

    public static final String ALL = "all";
    public static final int DEFAULT_PORT = 8080;

    /**
     * The taskSet.
     */
    protected final EndpointSet endpointSet;

    /* properties */
    private InetAddress bindAddress;
    private int port;
    private int backlog;

    /**
     * The httpServer.
     */
    private HttpServer httpServer;

    /**
     * Creates a new instance of WSIPublisher.
     *
     * @param endpointSet EndpointSet
     * @param properties  Properties
     */
    public WSIPublisher(EndpointSet endpointSet, Properties properties) {
        if (endpointSet == null) throw new IllegalArgumentException("null endpointSet");
        this.endpointSet = endpointSet;
        setProperties(properties);
    }

    /**
     * Starts this.
     *
     * @throws Exception
     */
    public void start() throws Exception {
        httpServer = HttpServer.create(new InetSocketAddress(bindAddress, port), backlog);
        httpServer.setExecutor(Executors.newFixedThreadPool(backlog));
        for (Map.Entry<String, EndpointSet.Entry> entry : endpointSet.getEntrySet()) {
            HttpContext httpContext = httpServer.createContext("/wsi/" + entry.getKey());
            if (entry.getValue().getAuthenticator() != null)
                httpContext.setAuthenticator(entry.getValue().getAuthenticator());
            entry.getValue().getEndpoint().publish(httpContext);
        }
        httpServer.start();
    }

    /**
     * Interrupts this.
     */
    public void interrupt() {
        if (endpointSet != null) for (Map.Entry<String, EndpointSet.Entry> entry : endpointSet.getEntrySet())
            entry.getValue().getEndpoint().stop();
        if (httpServer != null) {
            httpServer.stop(0);
            ((ExecutorService) httpServer.getExecutor()).shutdown();
        }
    }

    /**
     * Sets the properties.
     *
     * @param properties Properties
     */
    private void setProperties(Properties properties) {
        if (properties == null) throw new IllegalArgumentException("null properties");
        // bindAddress
        try {
            String str = properties.getProperty(BIND_ADDRESS, ALL);
            if (str.equals(ALL)) bindAddress = null;
            else bindAddress = InetAddress.getByName(str);
        } catch (UnknownHostException uhe) {
            throw new RuntimeException("illegal " + BIND_ADDRESS + " " + properties.getProperty(BIND_ADDRESS), uhe);
        }

        // port
        try {
            port = Integer.parseInt(properties.getProperty(PORT, String.valueOf(DEFAULT_PORT)));
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("illegal " + PORT + " " + properties.getProperty(PORT), nfe);
        }

        // backlog
        try {
            backlog = Integer.parseInt(properties.getProperty(BACKLOG, "64"));
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("illegal " + BACKLOG + " " + properties.getProperty(BACKLOG), nfe);
        }
    }
}
