package com.cxw.app;

import org.junit.Test;

public class AzureRestClientTest {
    

    @Test
    public void testHttpGethostedservices() {
        System.out.println("RestClientTest.testHttpGet()");
        AzureRestClient client = new AzureRestClient();
        client.httpGet("services/hostedservices/cxwtc1-testa");
    }
    
    @Test
    public void testHttpGetcloudserviceavailable() {
        System.out.println("RestClientTest.testHttpGet()");
        AzureRestClient client = new AzureRestClient();
        client.httpGet("services/hostedservices/operations/isavailable/cxwtc1-testa");
    }

}
