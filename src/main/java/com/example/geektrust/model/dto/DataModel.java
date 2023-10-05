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
package com.example.geektrust.model.dto;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.geektrust.model.ref.FundType;

/**
 * @author : Prakash Karki
 * @Purpose : DataModel | 
 * @Created : 3 Oct 2023 4:12:29 pm
 */

public class DataModel {
	
	private TreeMap<Month, Portfolio> monthlyPortfolio = new TreeMap<>();
	
	private List<Fund> desiredAllocation = new ArrayList<>();
	
	private Portfolio initialAllocation;
	
	private Portfolio sip;
	
	private Set<FundType> fundOrder = new LinkedHashSet<>();

	/**
	 * @return the monthlyPortfolio
	 */
	public TreeMap<Month, Portfolio> getMonthlyPortfolio() {
		return monthlyPortfolio;
	}

	/**
	 * @param monthlyPortfolio the monthlyPortfolio to set
	 */
	public void setMonthlyPortfolio(TreeMap<Month, Portfolio> monthlyPortfolio) {
		this.monthlyPortfolio = monthlyPortfolio;
	}

	/**
	 * @return the desiredAllocation
	 */
	public List<Fund> getDesiredAllocation() {
		return desiredAllocation;
	}

	/**
	 * @param desiredAllocation the desiredAllocation to set
	 */
	public void setDesiredAllocation(List<Fund> desiredAllocation) {
		this.desiredAllocation = desiredAllocation;
	}

	/**
	 * @return the initialAllocation
	 */
	public Portfolio getInitialAllocation() {
		return initialAllocation;
	}

	/**
	 * @param initialAllocation the initialAllocation to set
	 */
	public void setInitialAllocation(Portfolio initialAllocation) {
		this.initialAllocation = initialAllocation;
	}

	/**
	 * @return the sip
	 */
	public Portfolio getSip() {
		return sip;
	}

	/**
	 * @param sip the sip to set
	 */
	public void setSip(Portfolio sip) {
		this.sip = sip;
	}

	/**
	 * @return the fundOrder
	 */
	public Set<FundType> getFundOrder() {
		return fundOrder;
	}

	/**
	 * @param fundOrder the fundOrder to set
	 */
	public void setFundOrder(Set<FundType> fundOrder) {
		this.fundOrder = fundOrder;
	}
}
