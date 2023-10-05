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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.geektrust.model.ref.FundType;

/**
 * @author : Prakash Karki
 * @Purpose : Portfolio | 
 * @Created : 3 Oct 2023 7:28:22 pm
 */
public class Portfolio {

	private List<Fund> funds;

	/**
	 * @return the funds
	 */
	public List<Fund> getFunds() {
		return funds;
	}

	/**
	 * @param funds the funds to set
	 */
	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}

	public Double total() {
		Optional<Double> totalOp = this.funds.stream().map(f -> f.getAmount()).reduce(Double :: sum);
		return totalOp.isPresent() ? totalOp.get() : 0d;
	}

	public Double getWeightage(FundType type){

		List<Fund> fundList = this.funds.stream().filter(
				f -> type.equals(f.getType())).collect(Collectors.toList());
		return (null != fundList && !fundList.isEmpty()) ? 
				(fundList.get(0).getAmount() * 100 ) / total() : 0d;
	}

	@Override
	public String toString() {
		return this.funds.stream()
				.map(entity -> Integer.toString((int) Math.floor(entity.getAmount())))
				.collect(Collectors.joining(" "));
	}
}
