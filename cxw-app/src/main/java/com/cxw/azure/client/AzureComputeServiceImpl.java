/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.cxw.azure.client;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class AzureComputeServiceImpl {

    static final class Azure {
        private static final String ENDPOINT = "https://management.core.chinacloudapi.cn/";
        private static final String VERSION = "2013-03-01";
    }

    private SSLSocketFactory socketFactory;
    private final String keystore = "AzureCertificateForChad2014-12-26.pfx";
    private final String trustStoreFile = "jssecacerts";
    private final String password = "123456";
    private final String subscription_id = "8e6d097f-85d9-4338-80d3-4150ff9e5e70";
    private final String service_name = "cxwtc1-testa";
    private final String port_name = "cxwtc1-testa";

    public AzureComputeServiceImpl() {
        
        System.setProperty("https.proxyHost", "10.64.1.62");        
        System.setProperty("https.proxyPort", "8080");
        
        try {
            socketFactory = getSocketFactory(keystore, password);
            System.out.println("create socketFactory success.");
        } catch (Exception e) {
            System.err.println("create socketFactory failed.");
            System.out.println(e.getMessage());
            socketFactory = null;
        }
    }

    private InputStream getXML(String api) throws UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, IOException,
            CertificateException, KeyManagementException {
        String https_url = Azure.ENDPOINT + subscription_id + api;

        URL url = new URL(https_url);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setSSLSocketFactory(socketFactory);
        con.setRequestProperty("x-ms-version", Azure.VERSION);

        return con.getInputStream();
    }

    public Set<Instance> instances() {
        if (socketFactory == null) {
            return new HashSet<Instance>();
        } else {
            try {
                InputStream stream = getXML("/services/hostedservices/"
                        + service_name + "?embed-detail=true");
                Set<Instance> instances = buildInstancesFromXml(stream,
                        port_name);
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

    public static Set<Instance> buildInstancesFromXml(InputStream inputStream,
            String port_name) throws ParserConfigurationException, IOException,
            SAXException, XPathExpressionException {
        Set<Instance> instances = new HashSet<Instance>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStream);

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

    private SSLSocketFactory getSocketFactory(String keystore, String password)
            throws NoSuchAlgorithmException, KeyStoreException, IOException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException {
        File pKeyFile = new File(keystore);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance("SunX509");
        
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream keyInput = new FileInputStream(pKeyFile);
        keyStore.load(keyInput, password.toCharArray());
        keyInput.close();
        keyManagerFactory.init(keyStore, password.toCharArray());

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(keyManagerFactory.getKeyManagers(), null,
                new SecureRandom());
        return context.getSocketFactory();
    }

}
