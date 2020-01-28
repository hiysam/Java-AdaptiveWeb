package com.adaptive.module.master.basic.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.constant.CommonConstants;

@ManagedBean(name = "volumeBalokBean")
@ViewScoped
public class VolumeBalokBean implements Serializable{
	private static final long serialVersionUID = 7472162033614881644L;
	
	@ManagedProperty(value ="#{volumeBalokInputBean}")
	private VolumeBalokInputBean volumeBalokInputBean;
	
	private String MODE_TYPE;

	public VolumeBalokInputBean getVolumeBalokInputBean() {
		return volumeBalokInputBean;
	}

	public void setVolumeBalokInputBean(VolumeBalokInputBean volumeBalokInputBean) {
		this.volumeBalokInputBean = volumeBalokInputBean;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		volumeBalokInputBean.modelInput();
	}
}