package com.nuevatel.common.wsi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.ws.Endpoint;

/**
 * <p>The EndpointSet class.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public class EndpointSet {

    /**
     * The entryMap.
     */
    private final Map<String, Entry> entryMap;

    /**
     * Creates a new instance of EndpointSet.
     */
    public EndpointSet() {
        entryMap = new HashMap<String, Entry>();
    }

    /**
     * Adds an endpoint.
     *
     * @param name          String
     * @param endpoint      Endpoint
     * @param authenticator Authenticator
     */
    public final void add(String name, Endpoint endpoint, Authenticator authenticator) {
        entryMap.put(name, new Entry(endpoint, authenticator));
    }

    /**
     * Returns the entrySet.
     *
     * @return Set<Map.Entry<String, Entry>>
     */
    Set<Map.Entry<String, Entry>> getEntrySet() {
        return entryMap.entrySet();
    }

    /**
     * The Entry class.
     */
    class Entry {

        /* private variables */
        private final Endpoint endpoint;
        private final Authenticator authenticator;

        /**
         * Creates a new instance of Entry.
         *
         * @param endpoint      Endpoint
         * @param authenticator Authenticator
         */
        private Entry(Endpoint endpoint, Authenticator authenticator) {
            this.endpoint = endpoint;
            this.authenticator = authenticator;
        }

        /**
         * Returns the endpoint.
         *
         * @return Endpoint
         */
        Endpoint getEndpoint() {
            return endpoint;
        }

        /**
         * Returns the authenticator.
         *
         * @return Authenticator
         */
        Authenticator getAuthenticator() {
            return authenticator;
        }
    }
}
