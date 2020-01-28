package com.adaptive.module.configuration.appSetting.model;

import java.io.Serializable;

import com.adaptive.common.model.BaseEntity;
import com.adaptive.module.configuration.appSetting.model.AppSettingType;


public class AppSetting extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8602915955974044562L;
	
	private String settingCode;
	private String settingName;
	private AppSettingType appSettingType;
	
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
	public AppSettingType getAppSettingType() {
		return appSettingType;
	}
	public void setAppSettingType(AppSettingType appSettingType) {
		this.appSettingType = appSettingType;
	}
		
	/*public String getGender() {
		return gender;0
	}
	public void setGender(String gender) {
		this.gender = gender;
	}*/
	
	
}