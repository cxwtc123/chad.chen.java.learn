package com.chad.azuretest.test;


import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.chad.azuretest.ConfigurationUtil;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.management.SubscriptionOperations;
import com.microsoft.windowsazure.management.models.SubscriptionGetResponse;

public class TestSubscription extends ConfigurationUtil {

	private SubscriptionOperations subscriptionsOperations;

	public TestSubscription() throws IOException, URISyntaxException {
		super.createConfigurationByPublishFile();
	}

	@Test
	public void test() throws IOException, ServiceException, ParserConfigurationException, SAXException, URISyntaxException {

		subscriptionsOperations = client.getSubscriptionsOperations();
		System.out.println("get subscriptions Operations success.");
		
		SubscriptionGetResponse subscriptionGetResponse = subscriptionsOperations.get();
		System.out.println("get subscription response success.");
		System.out.println(subscriptionGetResponse);
		
	}

}
