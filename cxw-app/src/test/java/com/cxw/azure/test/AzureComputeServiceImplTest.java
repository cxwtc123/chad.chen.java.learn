package com.cxw.azure.test;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cxw.azure.client.AzureComputeServiceImpl;
import com.cxw.azure.client.Instance;

public class AzureComputeServiceImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInstances() {
        System.out.println("AzureComputeServiceImplTest.testInstances() start");
        
        AzureComputeServiceImpl serv = new AzureComputeServiceImpl();

        Set<Instance> instances = serv.instances();
        System.out.println("instance count = " + instances.size() + " .");
        
        for (Instance instance : instances) {
            System.out.println(instance.toString());
        }
        
        
        System.out.println("AzureComputeServiceImplTest.testInstances() end");
    }

}
