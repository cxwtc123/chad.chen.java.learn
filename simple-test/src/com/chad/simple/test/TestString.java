package com.chad.simple.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestString {

	@Test
	public void test() {
		
	}
	
	
	@Test 
	public void testNullStringAdd() {
		String str1 = "null string add test:";
		String strNull = null;
		System.out.println(str1 + strNull);		
	}
	

}
