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
package com.example.geektrust.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.geektrust.model.dto.DataModel;
import com.example.geektrust.model.ref.FundType;

/**
 * @author : Prakash Karki
 * @Purpose : CommonConfig | Configuration class
 * @Created : 3 Oct 2023 7:39:16 pm
 */
@Configuration
public class CommonConfig {

	@Bean
	public DataModel dataModel() {
		
		DataModel dm = new DataModel();
		dm.getFundOrder().add(FundType.EQUITY);
		dm.getFundOrder().add(FundType.DEBT);
		dm.getFundOrder().add(FundType.GOLD);
		return dm;
		
	}
}
