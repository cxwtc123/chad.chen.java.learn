package com.chad.azuretest.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.chad.azuretest.ConfigurationUtil;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.management.models.AffinityGroupListResponse.AffinityGroup;

public class TestAffinityGroup extends ConfigurationUtil {

	public TestAffinityGroup() throws IOException, URISyntaxException {
		super.createConfigurationByPublishFile();
	}

	@Test
	public void test() throws IOException, ServiceException,
			ParserConfigurationException, SAXException {

		ArrayList<AffinityGroup> affinityGroups = client
				.getAffinityGroupsOperations().list().getAffinityGroups();

		System.out.println("get affinityGroups success, count = "
				+ ( affinityGroups == null ? 0 : affinityGroups.size()));

		for (AffinityGroup affinityGroup : affinityGroups) {
			System.out.println(affinityGroup.getName()
					+ affinityGroup.getDescription()
					+ affinityGroup.getLocation()
					+ affinityGroup.getCapabilities().size());
		}

	}

}
