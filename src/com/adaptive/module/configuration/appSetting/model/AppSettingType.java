package com.adaptive.module.configuration.appSetting.model;

import java.io.Serializable;

import com.adaptive.common.model.BaseEntity;



public class AppSettingType extends BaseEntity implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 865366879309026118L;
	
	private String typeCode;
	private String typeName;
	
	
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}	
}