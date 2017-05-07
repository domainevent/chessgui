package com.javacook.chessgui;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


/**
 * Created by vollmer on 07.05.17.
 */
public class RestClient {

    public final static String SERVER_URL = "http://localhost:8080/dddchess";

    public final static Client CLIENT = ClientBuilder.newClient().register(JacksonJsonProvider.class);

}

