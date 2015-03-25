package com.cxw.test.policy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

public class TestPolicyGet {

	@Before
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost:8080";
		RestAssured.basePath = "/policy/";
	}

	@After
	public void tearDown() throws Exception {
		RestAssured.reset();
	}

	@Test
	public void testServerStatus() {
		expect().statusCode(200).get("serverStatus")
		.then().assertThat()
		.body("serviceName", equalTo("policy service"));
	}
}
