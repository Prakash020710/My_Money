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

import com.example.geektrust.model.ref.FundType;

/**
 * @author : Prakash Karki
 * @Purpose : Portfolio | 
 * @Created : 3 Oct 2023 4:10:28 pm
 */
public class Fund {
	
	public Fund() {}
	
	public Fund(FundType type, Double amount) {
		this.type = type;
		this.amount = amount;
	}

	private FundType type;
	
	private Double amount;

	/**
	 * @return the type
	 */
	public FundType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(FundType type) {
		this.type = type;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
