package com.chad.azuretest.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.chad.azuretest.ConfigurationUtil;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.management.compute.ComputeManagementClient;
import com.microsoft.windowsazure.management.compute.ComputeManagementService;
import com.microsoft.windowsazure.management.compute.DeploymentOperations;
import com.microsoft.windowsazure.management.compute.VirtualMachineOperations;
import com.microsoft.windowsazure.management.compute.models.DeploymentGetResponse;
import com.microsoft.windowsazure.management.compute.models.HostedServiceGetDetailedResponse;
import com.microsoft.windowsazure.management.compute.models.HostedServiceGetDetailedResponse.Deployment;
import com.microsoft.windowsazure.management.compute.models.HostedServiceGetResponse;
import com.microsoft.windowsazure.management.compute.models.HostedServiceListResponse;
import com.microsoft.windowsazure.management.compute.models.HostedServiceListResponse.HostedService;
import com.microsoft.windowsazure.management.compute.models.RoleInstance;
import com.microsoft.windowsazure.management.compute.models.VirtualIPAddress;
import com.microsoft.windowsazure.management.compute.models.VirtualMachineGetResponse;

public class TestManageComputer extends ConfigurationUtil {

	private ComputeManagementClient client;

	public TestManageComputer() throws IOException, URISyntaxException {
		super.createConfigurationByFile();
		client = ComputeManagementService.create(super.configuration);
	}

	@Test
	public void testHostList() throws IOException,
			ParserConfigurationException, SAXException, URISyntaxException {

		HostedServiceListResponse hostedServiceGetResponse;
		try {
			hostedServiceGetResponse = client.getHostedServicesOperations()
					.list();
			for (HostedService hostedService : hostedServiceGetResponse) {
				System.out.println(hostedService.getServiceName() + "\n"
						+ hostedService.getUri() + "\n");
			}

			System.out.println(hostedServiceGetResponse.toString());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testHostGet() throws IOException, ParserConfigurationException,
			SAXException, URISyntaxException {

		HostedServiceGetResponse hostedServiceGetResponse;
		try {
			hostedServiceGetResponse = client.getHostedServicesOperations()
					.get("chadservice1");

			System.out.println("get service [chadservice1] success.");
			System.out.println(hostedServiceGetResponse.getServiceName() + "\n"
					+ hostedServiceGetResponse.getUri() + "\n");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testHostGetDetail() throws IOException,
			ParserConfigurationException, SAXException, URISyntaxException {

		try {
			HostedServiceGetDetailedResponse detailed = client
					.getHostedServicesOperations().getDetailed("chadservice1");

			System.out.println("get service [chadservice1] success.");

			System.out.println(detailed.getServiceName() + "\n"
					+ detailed.getUri() + "\n");

			ArrayList<Deployment> deployments = detailed.getDeployments();
			for (Deployment deployment : deployments) {
				System.out.println(deployment.getLabel() + "\n"
						+ deployment.getStatus() + "\n"
						+ deployment.getUri() + "\n");

				ArrayList<VirtualIPAddress> virtualIPAddresses = deployment
						.getVirtualIPAddresses();
				System.out.println("[virtualIPAddresses]:");
				for (VirtualIPAddress virtualIPAddress : virtualIPAddresses) {
					System.out.println(virtualIPAddress.getAddress().getHostAddress());
				}
				System.out.println();

				ArrayList<RoleInstance> roleInstances = deployment
						.getRoleInstances();
				System.out.println("[roleInstances]:");
				for (RoleInstance roleInstance : roleInstances) {
					System.out.println(roleInstance.getInstanceName() + "\n"
							+ roleInstance.getInstanceStatus());
					if (roleInstance.getInstanceStatus().equals("ReadyRole")) {
						System.out.println( roleInstance.getIPAddress().getHostAddress());
					}
					System.out.println();
				}
			}

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testDeployList() throws IOException,
			ParserConfigurationException, SAXException, URISyntaxException {

		DeploymentOperations operations = client.getDeploymentsOperations();

		try {
			DeploymentGetResponse byName = operations.getByName("chadservice1",
					"");

			System.out.println("get virtualMachines success.");

			ArrayList<VirtualIPAddress> virtualIPAddresses = byName
					.getVirtualIPAddresses();

			for (VirtualIPAddress virtualIPAddress : virtualIPAddresses) {
				System.out.println(virtualIPAddress.getName() + "\n"
						+ virtualIPAddress.getAddress().getHostAddress());

			}

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testVM() throws IOException, ParserConfigurationException,
			SAXException, URISyntaxException {
		VirtualMachineOperations operations = client
				.getVirtualMachinesOperations();

		try {
			VirtualMachineGetResponse virtualMachineGetResponse = operations
					.get("chadservice1", "chad-machine-01", "chad-machine-01");

			System.out.println("get virtualMachines success.");
			System.out
					.println(virtualMachineGetResponse.getRoleName()
							+ "\n"
							+ virtualMachineGetResponse
									.getAvailabilitySetName() + "\n");

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}

	}
}
