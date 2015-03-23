package com.chad.azuretest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.core.utils.KeyStoreType;
import com.microsoft.windowsazure.management.ManagementClient;
import com.microsoft.windowsazure.management.configuration.ManagementConfiguration;
import com.microsoft.windowsazure.management.configuration.PublishSettingsLoader;

public class ConfigurationUtil {
	private String urlString = "https://management.core.chinacloudapi.cn";
	private String publishFileName = "E:\\svn\\Docs\\TestCount\\azure-account\\chad-credentials.publishsettings";
	
//	private String certificateProfileName = "windowsazureremoteaccess";
	private String certificatePath = "cxwtc12015-3-18.pfx";
	
	private String certificatePassword = "123456";
	
	private String subscriptionId = "8e6d097f-85d9-4338-80d3-4150ff9e5e70";
	protected Configuration configuration;
	
	protected ManagementClient client;
	
	public ConfigurationUtil() throws IOException, URISyntaxException {
	    System.setProperty("http.proxyHost", "cnnjproxy02-gfw.tw.trendnet.org");
	    System.setProperty("http.proxyPort", "8080");
	}	
	
	protected void createConfigurationByPublishFile() throws IOException, URISyntaxException {
	
		configuration = PublishSettingsLoader.createManagementConfiguration(publishFileName , subscriptionId);
		System.out.println("create configuration from publish file success. ");
	}
	
	protected void createConfigurationByFile() {
		try {
	        // create new configuration object	
			configuration = ManagementConfiguration.configure(
					null,
					Configuration.load(),
					new URI(urlString),
					subscriptionId,
					certificatePath, 
					certificatePassword,
					KeyStoreType.pkcs12);
			
			System.out.println("create configuration success.");
	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}	
				
	}
	

}
