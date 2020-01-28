package com.adaptive.common.model;

import java.io.Serializable;

public class Parameter extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 7450705114398618539L;
	
	private String paramCode;
	private String paramName;
	private String paramNo;
	
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamNo() {
		return paramNo;
	}
	public void setParamNo(String paramNo) {
		this.paramNo = paramNo;
	}
	
	
}
