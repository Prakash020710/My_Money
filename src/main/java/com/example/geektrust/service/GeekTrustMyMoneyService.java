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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.geektrust.GeekTrustMyMoneyApplication;
import com.example.geektrust.common.CommonConstants;
import com.example.geektrust.config.ErrorConfig;
import com.example.geektrust.exception.GeektrustException;
import com.example.geektrust.model.dto.DataModel;
import com.example.geektrust.model.ref.SupportedInputCommands;
import com.example.geektrust.processor.GeekTrustMyMoneyCommandProcessor;

/**
 * @author : Prakash Karki
 * @Purpose : GeekTrustMyMoneyService | Main service class to orchestrate all the operations
 * @Created : 2 Oct 2023 10:52:13 pm
 */
@Service
public class GeekTrustMyMoneyService {

	private static Logger logger = LogManager.getLogger(GeekTrustMyMoneyApplication.class);

	@Autowired
	private DataModel dataModel;

	@Autowired
	private GeekTrustMyMoneyCommandProcessor processor;

	/**
	 * Entry point of processing commands from file.
	 * 
	 * @param args
	 * @throws GeektrustException : if arguments are not valid
	 */
	public void processCommandsFromFile(String[] args) throws GeektrustException{
		if (args.length < 1) {
			logger.error("No input arguments were supplied!");
			throw new GeektrustException(CommonConstants.GTMM_001, ErrorConfig.string(CommonConstants.GTMM_001));
		} else if (args.length != 1) {
			logger.error("Arguments supplied are incorrect!");
			throw new GeektrustException(CommonConstants.GTMM_002, ErrorConfig.string(CommonConstants.GTMM_002));
		}

		processFile(args[0]);
	}

	/**
	 * This file starts the processing of file.
	 * @param filename
	 * @throws GeektrustException
	 */
	private void processFile(String filename) throws GeektrustException {

		Stream<String> lines = null;
		List<String> outputs = null;
		try {
			lines = Files.lines(Paths.get(filename));
			for(String line : lines.filter((l -> (null != l && !l.isEmpty()))).toArray(String[]::new)) {
				processIndividualCommand(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new GeektrustException(CommonConstants.GTMM_003, ErrorConfig.string(CommonConstants.GTMM_003));
		}
	}

	/**
	 * This method processes individual line(command) from input file.
	 * @param line
	 * @throws GeektrustException
	 */
	private void processIndividualCommand(String line) throws GeektrustException {

		String[] commandAndInputs = line.split(" ");
		SupportedInputCommands command = null;
		try {
			command = SupportedInputCommands.valueOf(commandAndInputs[0]);
		} catch (Exception e) {
			throw new GeektrustException(CommonConstants.GTMM_005, ErrorConfig.string(CommonConstants.GTMM_005));
		}
		int size = dataModel.getFundOrder().size();
		switch(command) {
		case ALLOCATE:
			validate(commandAndInputs, size);
			processor.allocate(commandAndInputs);
			break;
		case BALANCE:
			validate(commandAndInputs, 1);
			processor.balance(commandAndInputs);
			break;
		case CHANGE:
			validate(commandAndInputs, size + 1);
			processor.change(commandAndInputs);
			break;
		case REBALANCE:
			processor.reBalance();
			break;
		case SIP:
			validate(commandAndInputs, size);
			processor.sip(commandAndInputs);
			break;
		}
	}

	private void validate(String[] commandAndInputs, int size) throws GeektrustException {

		if(commandAndInputs.length != size + 1) {
			throw new GeektrustException(CommonConstants.GTMM_005, ErrorConfig.string(CommonConstants.GTMM_005));
		}
	}
}
