package com.buyfull.openapiv1;

import java.io.IOException;

/**
 * @author yechangqing
 * 百蝠服务器返回的错误信息
 */
public final class BFException extends IOException {
	private static final long serialVersionUID = -4923539943210269302L;

	public enum ERRORS{
		UNKNOWN,
		NETWORK_ERROR,
		HTTP_ERROR,
		INVALID_CONTEXT,
		INVALID_ACCESSKEY,
		INVALID_UUID,
		INVALID_WORK,
		INVALID_JSON,
		APP_CREATE_ERROR,
		GROUP_CREATE_ERROR,
		GROUP_UPDATE_ERROR,
		FETCH_ERROR,
		DELETE_ERROR,
		DEVICE_BOUND_ERROR,
		DATA_FORMAT_ERROR,
		PARAM_NULL_ERROR,
		DYNAMIC_PARAM_ERROR
	}
	
	ERRORS errCode;

	String errMsg;

	public BFException(IOException exception,ERRORS errCode, String errMsg){
		super(exception);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	public BFException(ERRORS errCode, String errMsg){
		super( errCode  + ": " +   errMsg );
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	public BFException(int errCode, String errMsg){
		//TODO: you need translate
		switch(errCode){
		default:
			this.errCode = ERRORS.UNKNOWN;
		}
		this.errMsg = errMsg;
	}
	/**
	 * @return 错误代码
	 */
	public ERRORS getErrCode() {
		return errCode;
	}
	
	/**
	 * @return 错误信息
	 */
	public String getErrMsg() {
		return errMsg;
	}


}
