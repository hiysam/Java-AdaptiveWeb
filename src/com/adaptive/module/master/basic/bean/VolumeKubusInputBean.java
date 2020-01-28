package com.adaptive.module.master.basic.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.util.FacesUtils;

@ManagedBean(name = "volumeKubusInputBean")
@ViewScoped
public class VolumeKubusInputBean extends FacesUtils implements Serializable{
	private static final long serialVersionUID = 301157183437596760L;
	
	private String strSisi;
	private Long sisi;
	private Long hasil;
	
	
	public String getStrSisi() {
		return strSisi;
	}
	public void setStrSisi(String strSisi) {
		this.strSisi = strSisi;
	}
	public Long getSisi() {
		return sisi;
	}
	public void setSisi(Long sisi) {
		this.sisi = sisi;
	}
	public Long getHasil() {
		return hasil;
	}
	public void setHasil(Long hasil) {
		this.hasil = hasil;
	}
	
	public boolean isValidateBilangan(){
		boolean valid = true;
		
		if(strSisi.isEmpty()){
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formKubus:sisi",
					retrieveMessage("msgSisi") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if(!strSisi.isEmpty()){
			try{
				sisi = Long.parseLong(strSisi);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formKubus:sisi",
						retrieveMessage("msgSisi") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
		}
		
		return valid;
	}
	
	public void calculate(){
		if(isValidateBilangan()){
			hasil = sisi * sisi * sisi;
		} else {
			hasil = null;
		}
	}
}