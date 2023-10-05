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
package com.example.geektrust;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.geektrust.exception.GeektrustException;
import com.example.geektrust.service.GeekTrustMyMoneyService;

/**
 * @author : Prakash Karki
 * @Purpose : GeekTrustMyMoneyCommandLineRunner | Main class for running command line
 * @Created : 3 Oct 2023 11:24:37 pm
 */
@Component
public class GeekTrustMyMoneyCommandLineRunner implements CommandLineRunner{

	private static Logger logger = LogManager.getLogger(GeekTrustMyMoneyCommandLineRunner.class);

	@Autowired
	private GeekTrustMyMoneyService service;

	@Override
	public void run(String... args) throws Exception {

		try {
			service.processCommandsFromFile(args);
		} catch (GeektrustException e) {
			logger.log(Level.ERROR, e.getMessage());
			System.out.println(e.getMessage());
		}catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage());
			System.out.println("Failed to process file!");
		}
		System.exit(0);
	}
}
