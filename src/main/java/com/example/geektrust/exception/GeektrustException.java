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
package com.example.geektrust.exception;

import com.example.geektrust.common.CommonConstants;

/**
 * @author : Prakash Karki
 * @Purpose : GeektrustException | 
 * @Created : 2 Oct 2023 10:42:18 pm
 */
public class GeektrustException extends Exception{

	/**
	 *Serial version UID 
	 */
	private static final long serialVersionUID = -5659407780391088312L;
	private final String errorCode;

	public GeektrustException(){
		super();
		this.errorCode = CommonConstants.DEFAULT_ERROR_CODE;
	}

	public GeektrustException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
}
