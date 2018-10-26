package com.buyfull.openapiv1.implement;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import com.buyfull.openapiv1.BFException;
import com.buyfull.openapiv1.BFObjBaseV1;
import com.buyfull.openapiv1.BFOpenAPI;
class BFObjBaseV1_Implement implements BFObjBaseV1 {
	String uuid;
	WeakReference<BFOpenAPI_Implement> context;
	long lastUpdateTimeStamp;
	boolean isReadOnly;
	String createTime ;



	public BFObjBaseV1_Implement() throws BFException{
		throw new BFException(BFException.ERRORS.UNKNOWN, "Not allowed");
	}
	
	public BFObjBaseV1_Implement(BFOpenAPI_Implement context, String uuid) throws BFException{
		if (context == null){
			throw new BFException(BFException.ERRORS.INVALID_CONTEXT, "Invalid object creation");			
		}
		if (uuid == null || uuid == ""){
			throw new BFException(BFException.ERRORS.INVALID_UUID, "Invalid object creation");
		}
		this.context = new WeakReference<BFOpenAPI_Implement>(context);
		this.uuid = uuid;

	}

	public BFOpenAPI getContext() {
		return context.get();
	}

	public boolean fetch() throws BFException, ParseException {

		throw new BFException(BFException.ERRORS.UNKNOWN, "Not implemented");
	}

	public boolean update() throws BFException, ParseException {
		throw new BFException(BFException.ERRORS.UNKNOWN, "Not implemented");
	}

	public String uuid() {
		return uuid;
	}

	public String createTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public long lastUpdateTime() {
		return lastUpdateTimeStamp;
	}
	
	public void setLastUpdateTimeStamp(long lastUpdateTimeStamp) {
		this.lastUpdateTimeStamp = lastUpdateTimeStamp;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}
	
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	public boolean isValid() {
		if (context == null || context.get() == null ||
				uuid == null || uuid == "" ||
				lastUpdateTimeStamp == 0
				){
			return false;
		}
		return true;
	}

}
