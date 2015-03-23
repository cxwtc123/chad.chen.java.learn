package com.chad.azuretest.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.chad.azuretest.ConfigurationUtil;

public class TestConnect extends ConfigurationUtil {

	public TestConnect() throws IOException, URISyntaxException {
		super.createConfigurationByPublishFile();
	}

	@Test
	public void test() {
		
		System.out.println("TestConnect.test()");
	}

}
