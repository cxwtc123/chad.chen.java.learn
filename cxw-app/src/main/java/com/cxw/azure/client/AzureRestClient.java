package com.cxw.azure.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.glassfish.jersey.SslConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AzureRestClient {

    private String urlString = "https://management.core.chinacloudapi.cn/8e6d097f-85d9-4338-80d3-4150ff9e5e70";
    private String certificatePath = "cxwtc12015-3-18.pfx";
    private String certificatePassword = "123456";
    private String trustStoreFile = "jssecacerts";
    private String serviceName = "cxwtc1-testa";
    private String portType = "";

    Client client = null;
    WebTarget target = null;

    public AzureRestClient() {
        System.out.println("AzureRestClient.AzureRestClient()");

        System.setProperty("https.proxyHost", "10.64.1.62");
        System.setProperty("https.proxyPort", "8080");

        SslConfigurator sslConfig = SslConfigurator.newInstance()
                .keyStoreFile(certificatePath)
                .keyStorePassword(certificatePassword).keyStoreType("pkcs12")
                .trustStoreFile(trustStoreFile);

        SSLContext sslContext = sslConfig.createSSLContext();
        System.out.println("RestClient.initKey success");

        client = ClientBuilder.newBuilder().sslContext(sslContext).build();
        System.out.println("RestClient create client success");

        target = client.target(urlString);
        System.out.println("RestClient create target success");
    }

    public Set<Instance> instances() {
        if (target == null) {
            return new HashSet<Instance>();
        } else {
            try {
                String strString = httpGet("/services/hostedservices/"
                        + serviceName + "?embed-detail=true");
                Set<Instance> instances = buildInstancesFromXml(strString, portType);
                return instances;
            } catch (Exception e) {
                System.err.println("get XML failed.");
                System.out.println(e);
            }
        }
        return new HashSet<Instance>();
    }

    private static String extractValueFromPath(Node node, String path)
            throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node subnode = (Node) xPath.compile(path).evaluate(node,
                XPathConstants.NODE);
        return subnode.getFirstChild().getNodeValue();
    }

    public static Set<Instance> buildInstancesFromXml(String xmlstring,
            String port_name) throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        Set<Instance> instances = new HashSet<Instance>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        InputStream stream = new ByteArrayInputStream(xmlstring.getBytes(StandardCharsets.UTF_8));
        Document doc = dBuilder.parse(stream);

        doc.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();

        // We only fetch Started nodes (TODO: should we start with all nodes
        // whatever the status is?)
        String expression = "/HostedService/Deployments/Deployment/RoleInstanceList/RoleInstance[PowerState='Started']";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc,
                XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Instance instance = new Instance();
            Node node = nodeList.item(i);
            instance.setPrivateIp(extractValueFromPath(node, "IpAddress"));
            instance.setName(extractValueFromPath(node, "InstanceName"));
            instance.setStatus(Instance.Status.STARTED);

            // Let's digg into <InstanceEndpoints>
            expression = "InstanceEndpoints/InstanceEndpoint[Name='"
                    + port_name + "']";
            NodeList endpoints = (NodeList) xPath.compile(expression).evaluate(
                    node, XPathConstants.NODESET);
            for (int j = 0; j < endpoints.getLength(); j++) {
                Node endpoint = endpoints.item(j);
                instance.setPublicIp(extractValueFromPath(endpoint, "Vip"));
                instance.setPublicPort(extractValueFromPath(endpoint,
                        "PublicPort"));
            }

            instances.add(instance);
        }

        return instances;
    }

    public String httpGet(String resourcePath) {
        System.out.println("RestClient.httpGet()");
        String retStr = null;

        try {
            Response response = target.path(resourcePath).request()
                    .header("x-ms-version", "2014-10-01").get();
            retStr = response.readEntity(String.class);
            return retStr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retStr;

    }

}
