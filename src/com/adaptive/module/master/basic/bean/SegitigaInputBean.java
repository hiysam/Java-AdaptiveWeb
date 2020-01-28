package com.adaptive.module.master.basic.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.util.FacesUtils;

@ManagedBean(name = "segitigaInputBean")
@ViewScoped
public class SegitigaInputBean extends FacesUtils implements Serializable{
	private static final long serialVersionUID = 301157183437596760L;
	
	private String strAlas;
	private String strTinggi;
	private Long alas;
	private Long tinggi;
	private Long hasil;
	
	
	public String getStrAlas() {
		return strAlas;
	}
	public void setStrAlas(String strAlas) {
		this.strAlas = strAlas;
	}
	public String getStrTinggi() {
		return strTinggi;
	}
	public void setStrTinggi(String strTinggi) {
		this.strTinggi = strTinggi;
	}
	public Long getAlas() {
		return alas;
	}
	public void setAlas(Long alas) {
		this.alas = alas;
	}
	public Long getTinggi() {
		return tinggi;
	}
	public void setTinggi(Long tinggi) {
		this.tinggi = tinggi;
	}
	public Long getHasil() {
		return hasil;
	}
	public void setHasil(Long hasil) {
		this.hasil = hasil;
	}
	
	public boolean isValidateBilangan(){
		boolean valid = true;
		
		if(strAlas.isEmpty()){
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formSegitiga:alas",
					retrieveMessage("msgAlas") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		if(strTinggi.isEmpty()){
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formSegitiga:tinggi",
					retrieveMessage("msgTinggi") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if(!strAlas.isEmpty() && !strTinggi.isEmpty()){
			try{
				alas = Long.parseLong(strAlas);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formSegitiga:alas",
						retrieveMessage("msgAlas") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
			
			try{
				tinggi = Long.parseLong(strTinggi);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formSegitiga:tinggi",
						retrieveMessage("msgTinggi") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
		}
		
		return valid;
	}
	
	public void calculate(){
		if(isValidateBilangan()){
			hasil = (alas * tinggi)/2;
		} else {
			hasil = null;
		}
	}
}