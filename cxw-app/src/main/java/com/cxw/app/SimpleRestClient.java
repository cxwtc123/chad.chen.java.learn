package com.cxw.app;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


public class SimpleRestClient {

    private Client client;
    private WebTarget target;

    public SimpleRestClient(String baseUrl) {
        client = ClientBuilder.newClient();
        target = client.target(baseUrl);
    }

    public Response httpGet(String url) {
        return target.path(url).request().get();
    }

}
