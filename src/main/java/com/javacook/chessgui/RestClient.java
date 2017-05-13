package com.javacook.chessgui;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


/**
 * Created by vollmer on 07.05.17.
 */
public class RestClient {

//    public final static String DEFAULT_SERVER_URL = "http://localhost:8080/dddchess/api";
    public final static String DEFAULT_SERVER_URL = "http://localhost:8080/dddschach/api";
    public final static int TIMEOUT = 2000;

    public static String SERVER_URL = DEFAULT_SERVER_URL;

    public final static Client CLIENT = ClientBuilder
            .newClient()
            .register(JacksonJsonProvider.class)
            .property(ClientProperties.CONNECT_TIMEOUT, TIMEOUT)
            .property(ClientProperties.READ_TIMEOUT,    TIMEOUT);

}

