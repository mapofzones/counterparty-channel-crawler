package com.mapofzones.counterpartychannelcrawler.common.exceptons;

public class JsonParseException extends BaseException {

	protected JsonParseException(String message) {
		super(message);
	}

	public JsonParseException(String message, Throwable cause) {
		super(message, cause);
    }	
}
