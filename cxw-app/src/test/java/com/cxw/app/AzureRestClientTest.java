package com.cxw.app;


import java.util.Set;

import org.junit.Test;

import com.cxw.azure.client.AzureRestClient;
import com.cxw.azure.client.Instance;


public class AzureRestClientTest {
    

    @Test
    public void instanceTest() {
        AzureRestClient client = new AzureRestClient();
        
        Set<Instance> instances = client.instances();
        
        for (Instance instance : instances) {
            System.out.println(instance);
        }
         

    }
    


}
