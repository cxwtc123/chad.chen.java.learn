package com.cxw.app;

import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleRestClientTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHttpGet() {
        
        SimpleRestClient client = new SimpleRestClient("http://localhost:9200");
        
        
        Response response = client.httpGet("/_nodes");
 
        System.out.println(response.readEntity(String.class));
        
    }

}
