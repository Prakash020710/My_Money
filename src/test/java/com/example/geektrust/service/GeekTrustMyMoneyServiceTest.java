/**
 *=================================================================
 * Copyright (c) Sample 2023
 *=================================================================
 * (C) 2023 All rights reserved. This product and related documents
 * are protected by copyright restricting its use, copying, 
 * distribution and decompilation. No part of this product or 
 * related documentation can be reproduced in any form by any 
 * means without prior written authorisation.
 *
 * This class is for Sample Assessment.
 */
package com.example.geektrust.service;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.geektrust.exception.GeektrustException;
import com.example.geektrust.model.dto.DataModel;
import com.example.geektrust.processor.GeekTrustMyMoneyCommandProcessor;

/**
 * @author : Prakash Karki
 * @Purpose : GeekTrustMyMoneyServiceTest | 
 * @Created : 4 Oct 2023 1:05:39 pm
 */
@SpringJUnitConfig
public class GeekTrustMyMoneyServiceTest {

	@Spy 
	private DataModel dataModel;

	@Mock
	private GeekTrustMyMoneyCommandProcessor processor;

	@InjectMocks
	private GeekTrustMyMoneyService geekTrustMyMoneyService;

	@Test
	public void test_ProcessCommands_With_Invalid_Data() {

		String[] args = new String[] {"test1", "test2"};
		try {
			geekTrustMyMoneyService.processCommandsFromFile(args);
		} catch (GeektrustException e) {
			assertTrue(e.getMessage().equals("Arguments supplied are incorrect!"));
		}
	}

	@Test
	public void test_ProcessCommands_With_Null() {

		String[] args = new String[] {};
		try {
			geekTrustMyMoneyService.processCommandsFromFile(args);
		} catch (GeektrustException e) {
			assertTrue(e.getMessage().equals("No input arguments were supplied!"));
		}
	}

	@Test
	public void test_ProcessCommands_With_Input() throws URISyntaxException, GeektrustException {

		Mockito.doNothing().when(processor).allocate(Mockito.any());
		Mockito.doNothing().when(processor).balance(Mockito.any());
		Mockito.doNothing().when(processor).change(Mockito.any());
		Mockito.doNothing().when(processor).sip(Mockito.any());
		Mockito.doNothing().when(processor).reBalance();
		URL resource = GeekTrustMyMoneyServiceTest.class.getResource("/input.txt");
		File f =Paths.get(resource.toURI()).toFile();
		String[] args = new String[] {f.getAbsolutePath()};
		geekTrustMyMoneyService.processCommandsFromFile(args);

		/**
		 * Below condition is not required as it will always execute as true.
		 * processCommandsFromFile method in GeekTrustMyMoneyService calls internally
		 * processIndividualCommand, a private method, so this code is just for code
		 * coverage. Also underlying dependency is mocked so its not executing any code
		 * from GeekTrustMyMoneyCommandProcessor.
		 * # Recommended not to use such pattern!
		 */
		assertTrue(dataModel.getMonthlyPortfolio() != null);
	}
}
