package com.adaptive.module.master.basic.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.util.FacesUtils;

@ManagedBean(name = "penjumlahanInputBean")
@ViewScoped
public class penjumlahanInputBean extends FacesUtils implements Serializable{
	private static final long serialVersionUID = 301157183437596760L;
	
	private String strBil1;
	private String strBil2;
	private Long bil1;
	private Long bil2;
	private Long hasil;
	
	public String getStrBil1() {
		return strBil1;
	}
	public void setStrBil1(String strBil1) {
		this.strBil1 = strBil1;
	}
	public String getStrBil2() {
		return strBil2;
	}
	public void setStrBil2(String strBil2) {
		this.strBil2 = strBil2;
	}
	public Long getBil1() {
		return bil1;
	}
	public void setBil1(Long bil1) {
		this.bil1 = bil1;
	}
	public Long getBil2() {
		return bil2;
	}
	public void setBil2(Long bil2) {
		this.bil2 = bil2;
	}
	public Long getHasil() {
		return hasil;
	}
	public void setHasil(Long hasil) {
		this.hasil = hasil;
	}
	
	public boolean isValidateBilangan(){
		boolean valid = true;
		
		if(strBil1.isEmpty()){
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formPenjumlahan:bilangan1",
					retrieveMessage("lblBil1") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		if(strBil2.isEmpty()){
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formPenjumlahan:bilangan2",
					retrieveMessage("lblBil2") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if(!strBil1.isEmpty() && !strBil2.isEmpty()){
			try{
				bil1 = Long.parseLong(strBil1);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formPenjumlahan:bilangan1",
						retrieveMessage("lblBil1") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
			
			try{
				bil2 = Long.parseLong(strBil2);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formPenjumlahan:bilangan2",
						retrieveMessage("lblBil2") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
		}
		
		return valid;
	}
	
	public void calculate(){
		if(isValidateBilangan()){
			hasil = bil1 + bil2;
		} else {
			hasil = null;
		}
	}
}