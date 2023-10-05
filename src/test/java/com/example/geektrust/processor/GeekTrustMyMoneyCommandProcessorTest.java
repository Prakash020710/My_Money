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
package com.example.geektrust.processor;

import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.geektrust.exception.GeektrustException;
import com.example.geektrust.model.dto.DataModel;

/**
 * @author : Prakash Karki
 * @Purpose : GeekTrustMyMoneyCommandProcessor | 
 * @Created : 4 Oct 2023 3:50:37 pm
 */
@SpringJUnitConfig
public class GeekTrustMyMoneyCommandProcessorTest {


	@Spy 
	private DataModel dataModel;


	@InjectMocks
	private GeekTrustMyMoneyCommandProcessor geekTrustMyMoneyCommandProcessor;


	@Test
	public void test_Allocate_With_Valid_Data() {

		String[] args = new String[] {"ALLOCATE", "6000", "3000", "1000"};
		geekTrustMyMoneyCommandProcessor.allocate(args);
		assertTrue(null != dataModel.getMonthlyPortfolio().get(Month.JANUARY));
	}

	@Test
	public void test_SIP_With_Valid_Data() {

		String[] args = new String[] {"SIP", "2000", "1000", "500"};
		geekTrustMyMoneyCommandProcessor.sip(args);
		assertTrue(null != dataModel.getSip());
	}

	@Test
	public void test_Balance_With_Valid_Data_Failed() {

		String[] args = new String[] {"BALANCE", "MARCH"};
		try {
			geekTrustMyMoneyCommandProcessor.balance(args);
		} catch (GeektrustException e) {
			assertTrue(e.getMessage().equals("Can not print balance for the month provided in input!"));
		}
	}

	@Test
	public void test_Change_With_Valid_Data() {

		String[] args = new String[] {"ALLOCATE", "6000", "3000", "1000"};
		geekTrustMyMoneyCommandProcessor.allocate(args);

		String[] args1 = new String[] {"CHANGE", "4.00%", "4.00%", "4.00%", "JANUARY"};

		geekTrustMyMoneyCommandProcessor.change(args1);	

		assertTrue(dataModel.getMonthlyPortfolio().get(Month.JANUARY) != null);

	}

	@Test
	public void test_Full_Operation_Success_With_Rebalance() {

		String[] args1 = new String[] {"ALLOCATE", "6000", "3000", "1000"};
		geekTrustMyMoneyCommandProcessor.allocate(args1);

		String[] args2 = new String[] {"SIP", "2000", "1000", "500"};
		geekTrustMyMoneyCommandProcessor.sip(args2);

		String[] args3 = new String[] {"CHANGE", "4.00%", "10.00%", "2.00%", "JANUARY"};
		geekTrustMyMoneyCommandProcessor.change(args3);

		String[] args4 = new String[] {"CHANGE", "3.00%", "8.00%", "5.00%", "JUNE"};
		geekTrustMyMoneyCommandProcessor.change(args4);

		geekTrustMyMoneyCommandProcessor.reBalance();

		assertTrue(dataModel.getMonthlyPortfolio().get(Month.JANUARY) != null);
	}

	@Test
	public void test_Full_Operation_Success_Can_Not_Rebalance() {

		String[] args1 = new String[] {"ALLOCATE", "6000", "3000", "1000"};
		geekTrustMyMoneyCommandProcessor.allocate(args1);

		String[] args2 = new String[] {"SIP", "2000", "1000", "500"};
		geekTrustMyMoneyCommandProcessor.sip(args2);

		String[] args3 = new String[] {"CHANGE", "4.00%", "10.00%", "2.00%", "JANUARY"};
		geekTrustMyMoneyCommandProcessor.change(args3);

		String[] args4 = new String[] {"CHANGE", "3.00%", "8.00%", "5.00%", "FEBRUARY"};
		geekTrustMyMoneyCommandProcessor.change(args4);

		geekTrustMyMoneyCommandProcessor.reBalance();

		assertTrue(dataModel.getMonthlyPortfolio().get(Month.JANUARY) != null);
	}


}

