package com.cxw.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.message.internal.InputStreamProvider;

public class AzureRestClient {

    private String urlString = "https://management.core.chinacloudapi.cn/8e6d097f-85d9-4338-80d3-4150ff9e5e70/";
    private String certificatePath =  "D:\\cxw\\java_learn\\cxw-app\\cxwtc12015-3-18.pfx";    
    private String certificatePassword = "123456";
    private String trustStoreFile = "D:\\cxw\\java_learn\\cxw-app\\jssecacerts";

    Client client = null;
    WebTarget target = null;

    public AzureRestClient() {
        initKey();
    }

    private void initKey() {
        System.out.println("RestClient.initKey() ...");
        
        SslConfigurator sslConfig = SslConfigurator.newInstance()
                .keyStoreFile(certificatePath)
                .keyStorePassword(certificatePassword).keyStoreType("pkcs12")
                .trustStoreFile(trustStoreFile)
//                .trustStoreFile(certificatePath)
//                .trustStorePassword(certificatePassword)
                ;
        
        SSLContext sslContext = sslConfig.createSSLContext();
                
        client = ClientBuilder.newBuilder().sslContext(sslContext).build();
        target = client.target(urlString);
        System.out.println("RestClient.initKey() success");
    }

    String httpGet(String resourcePath) {
        System.out.println("RestClient.httpGet()");
        String retStr = null;

        try {
            Response response = target.path(resourcePath).request()
                    .header("x-ms-version", "2014-10-01").get();

            System.out.println("Connection is still open.");
            System.out.println("string response: "
                    + response.readEntity(String.class));
            System.out.println("Now the connection is closed.");
            return retStr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retStr;

    }

}
