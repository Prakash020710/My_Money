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

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.geektrust.common.CommonConstants;
import com.example.geektrust.config.ErrorConfig;
import com.example.geektrust.exception.GeektrustException;
import com.example.geektrust.model.dto.DataModel;
import com.example.geektrust.model.dto.Fund;
import com.example.geektrust.model.dto.Portfolio;
import com.example.geektrust.model.ref.FundType;

/**
 * @author : Prakash Karki
 * @Purpose : GeekTrustMyMoneyCommandProcessor | Individual command processor.
 * @Created : 3 Oct 2023 8:24:14 pm
 */
@Component
public class GeekTrustMyMoneyCommandProcessor {

	@Autowired
	private DataModel dataModel;

	private int size = 0;

	private List<FundType> fundtypes = null;

	/**
	 * Allocate command
	 * @param commandAndInputs
	 */
	public void allocate(String[] commandAndInputs) {

		if(size == 0) {
			size = dataModel.getFundOrder().size();
		}
		if(null == fundtypes)
			fundtypes = dataModel.getFundOrder().stream().collect(Collectors.toList());

		List<Fund> weightageList = new ArrayList<>();
		List<Double> amounts = getAmountsFromInput(commandAndInputs);

		Portfolio pf = getPortFolioFromAmounts(amounts);
		dataModel.setInitialAllocation(pf);
		dataModel.getMonthlyPortfolio().put(Month.JANUARY, pf);

		for(FundType ft : fundtypes) {
			Double weightage = dataModel.getInitialAllocation().getWeightage(ft);
			Fund f = new Fund(ft, weightage);
			weightageList.add(f);
		}

		dataModel.setDesiredAllocation(weightageList);
	}

	/**
	 * SIP command
	 * @param commandAndInputs
	 */
	public void sip(String[] commandAndInputs) {

		List<Double> amounts = getAmountsFromInput(commandAndInputs);
		Portfolio pf = getPortFolioFromAmounts(amounts);
		dataModel.setSip(pf);

	}

	/**
	 * Balance command
	 * @param commandAndInputs
	 * @throws GeektrustException
	 */
	public void balance(String[] commandAndInputs) throws GeektrustException {

		Month month = Month.valueOf(commandAndInputs[1]);
		if(null != dataModel.getMonthlyPortfolio().get(month))
			System.out.println(dataModel.getMonthlyPortfolio().get(month));
		else
			throw new GeektrustException(CommonConstants.GTMM_004, ErrorConfig.string(CommonConstants.GTMM_004));

	}

	/**
	 * Change command
	 * @param commandAndInputs
	 */
	public void change(String[] commandAndInputs) {

		List<Double> rates = Arrays.stream(commandAndInputs).skip(1).limit(size).
				map(str -> Double.parseDouble(str.replace("%", ""))).collect(Collectors.toList());
		Month currentMonth = Month.valueOf(commandAndInputs[size + 1]);
		Month lastMonth = dataModel.getMonthlyPortfolio().lastKey();

		if(Month.JANUARY.equals(currentMonth)) {
			Portfolio lastPortfolio = dataModel.getMonthlyPortfolio().lastEntry().getValue();
			applyRates(lastPortfolio, rates);
		}

		for(int i = lastMonth.getValue() ; i < currentMonth.getValue(); i++) {
			Month m = Month.of(i + 1);
			Portfolio lastPortfolio = dataModel.getMonthlyPortfolio().lastEntry().getValue();
			Portfolio currentPortFolio = applySIP(lastPortfolio);
			applyRates(currentPortFolio, rates);
			if(Month.JUNE.equals(m) || Month.DECEMBER.equals(m))
				applyReBalance(currentPortFolio);
			dataModel.getMonthlyPortfolio().put(m, currentPortFolio);
		}
	}

	/**
	 * Rebalance command
	 */
	public void reBalance() {

		Month lastUpdatedMonth = dataModel.getMonthlyPortfolio().lastKey();
		if(Month.DECEMBER.equals(lastUpdatedMonth)) {
			System.out.println(dataModel.getMonthlyPortfolio().lastEntry());
		}else if(null != dataModel.getMonthlyPortfolio().get(Month.JUNE)){
			Portfolio lastRebalancedPortfolio = dataModel.getMonthlyPortfolio().get(Month.JUNE);
			if(null != lastRebalancedPortfolio)
				System.out.println(lastRebalancedPortfolio);
		}else
			System.out.println("CANNOT_REBALANCE");

	}

	/**
	 * Converts amount from a single line command to a list of amounts.
	 * @param commandAndInputs
	 * @return List of amount
	 */
	private List<Double> getAmountsFromInput(String[] commandAndInputs) {

		return Arrays.stream(commandAndInputs).skip(1).limit(size).
				map(Double::parseDouble).collect(Collectors.toList());
	}

	/**
	 * Creates a portfolio from a list of amounts.
	 * @param amounts
	 * @return Portfolio
	 */
	private Portfolio getPortFolioFromAmounts(List<Double> amounts) {

		List<Fund> funds = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			Fund f = new Fund(fundtypes.get(i), amounts.get(i));
			funds.add(f);
		}
		Portfolio pf = new Portfolio();
		pf.setFunds(funds);
		return pf;
	}

	/**
	 * Applies rebalance to portfolio
	 * @param currentPortFolio
	 */
	private void applyReBalance(Portfolio currentPortFolio) {

		List<Fund> desiredAllocation = dataModel.getDesiredAllocation();
		Double totalAmount = currentPortFolio.total();
		List<Fund> newFundList = new ArrayList<>();
		for(Fund daf : desiredAllocation) {
			Fund f = new Fund(daf.getType(), (daf.getAmount() * totalAmount) / 100);
			newFundList.add(f);
		}
		currentPortFolio.setFunds(newFundList);
	}

	/**
	 * Apply SIP to last portfolio
	 * @param lastPortfolio
	 * @return Portfolio
	 */
	private Portfolio applySIP(Portfolio lastPortfolio) {

		Portfolio currentPortfolio = new Portfolio();
		List<Fund> fundList = new ArrayList<>();
		Portfolio sip = dataModel.getSip();
		for(Fund sf : sip.getFunds()) {
			for (Fund lf : lastPortfolio.getFunds()) {
				if(lf.getType().equals(sf.getType())) {
					Fund f = new Fund(lf.getType(), lf.getAmount() + sf.getAmount());
					fundList.add(f);
				}
			}
		}
		currentPortfolio.setFunds(fundList);
		return currentPortfolio;
	}

	/**
	 * Apply rates to portfolio.
	 * @param lastPortfolio
	 * @param rates
	 */
	private void applyRates(Portfolio lastPortfolio, List<Double> rates) {

		for(int i = 0; i < lastPortfolio.getFunds().size(); i++) {
			Double amount = (lastPortfolio.getFunds().get(i).getAmount() * rates.get(i)) / 100;
			lastPortfolio.getFunds().get(i).setAmount(lastPortfolio.getFunds().get(i).getAmount() + amount);
		}
	}
}
