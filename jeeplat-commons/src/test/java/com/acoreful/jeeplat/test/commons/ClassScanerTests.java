package com.acoreful.jeeplat.test.commons;

import org.junit.Test;

import com.acoreful.jeeplat.commons.spring.ClassScaner;

public class ClassScanerTests {

	@Test
	public void testScan() throws Exception {
		 ClassScaner.scan("com.acoreful")
         .forEach(clazz -> System.out.println(clazz));
		 ClassScaner.scan("com.acoreful1")
		 .forEach(clazz -> System.out.println(clazz));
	}
}
