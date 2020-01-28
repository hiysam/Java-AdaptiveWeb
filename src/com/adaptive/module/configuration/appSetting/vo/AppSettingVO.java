package com.adaptive.module.configuration.appSetting.vo;

import java.io.Serializable;

import com.adaptive.common.model.BaseEntity;



public class AppSettingVO extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3527807499947561432L;
	
	
	private String settingCode;
	private String settingName;
	private String gender;
	private String typeCode;
	private String TypeName;
	public String getSettingCode() {
		return settingCode;
	}
	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}
	public String getSettingName() {
		return settingName;
	}
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}	
}