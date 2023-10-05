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

import java.util.ResourceBundle;

/**
 * @author : Prakash Karki
 * @Purpose : ErrorConfig | 
 * @Created : 2 Oct 2023 9:32:39 pm
 */
public class ErrorConfig {

	private static final String BUNDLE_NAME = "my-money-error-config";
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	public static String string(final String property) {
		
		return RESOURCE_BUNDLE.getString(property);
	}
}
