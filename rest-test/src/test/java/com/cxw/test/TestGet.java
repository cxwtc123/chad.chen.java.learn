package com.cxw.test;

import javax.naming.NameNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.jayway.restassured.RestAssured;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

public class TestGet {

	@Before
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost:8080";
		RestAssured.basePath = "/test/";
	}

	@After
	public void tearDown() throws Exception {
		RestAssured.reset();
	}

	@Test
	public void testServerStatus() {
		expect().statusCode(200).get("_status").then().assertThat()
				.body("serviceName", equalTo("service"));
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void example3() throws NameNotFoundException {
		exception.expect(NameNotFoundException.class);
		exception.expectMessage(containsString("exception message"));

		throw new NameNotFoundException();
	}
}
