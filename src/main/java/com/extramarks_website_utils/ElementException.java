package com.extramarks_website_utils;

import org.openqa.selenium.WebDriverException;

public class ElementException extends WebDriverException {
	
	 public ElementException(String message) {
	        super(message);
	    }

	 public ElementException elementNotFoundException(ElementException e) {
	        return new ElementException("Element  in method "+e.getStackTrace()[0].getMethodName()+" is not found"+e.getStackTrace()[0].getLineNumber());
	    }
	 
}
