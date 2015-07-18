/*
 * Authenticator.java
 */

package com.nuevatel.common.wsi;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;

/**
 * <p>The Authenticator class.</p>
 * <p>Nuevatel PCS de Bolivia S.A. (c) 2013</p>
 *
 * @author Eduardo Marin
 * @version 1.0
 * @since 1.6
 */
public abstract class Authenticator extends BasicAuthenticator {

    /* private variables */
    private final String endpointName;
    private String address;

    /**
     * Creates a new instance of Authenticator.
     * @param endpointName String
     * @param realm String
     */
    public Authenticator(String endpointName, String realm) {
        super(realm);
        this.endpointName = endpointName;
    }

    @Override public Authenticator.Result authenticate(HttpExchange he) {
        address = he.getRemoteAddress().getAddress().getHostAddress();
        return super.authenticate(he);
    }

    @Override public boolean checkCredentials(String username, String password) {
        return auth(username, password, endpointName, address);
    }

    /**
     * Overwrite it to implement custom authentication.
     *
     * @param username Name of the user
     * @param password auth string for the user.
     * @param endpointName String
     * @param address String
     * @return boolean
     */
    protected abstract boolean auth(String username, String password, String endpointName, String address);
}
