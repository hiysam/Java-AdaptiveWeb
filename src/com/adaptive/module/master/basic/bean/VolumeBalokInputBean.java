package com.adaptive.module.master.basic.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.constant.CommonConstants;
import com.adaptive.common.util.FacesUtils;

@ManagedBean(name = "volumeBalokInputBean")
@ViewScoped
public class VolumeBalokInputBean extends FacesUtils implements Serializable{
	private static final long serialVersionUID = 301157183437596760L;
	
	private String MODE_TYPE;
	
	private String strBil1;
	private String strBil2;
	private String strBil3;
	private String title;
	private Long bil1;
	private Long bil2;
	private Long bil3;
	private Long hasil;
	
	public String getMODE_TYPE() {
		return MODE_TYPE;
	}
	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}
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
	public String getStrBil3() {
		return strBil3;
	}
	public void setStrBil3(String strBil3) {
		this.strBil3 = strBil3;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Long getBil3() {
		return bil3;
	}
	public void setBil3(Long bil3) {
		this.bil3 = bil3;
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
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formVolumeBalok:panjang",
					retrieveMessage("msgPanjang") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		if(strBil2.isEmpty()){
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formVolumeBalok:lebar",
					retrieveMessage("msgLebar") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		if(strBil3.isEmpty()){
			addFacesMsg(FacesMessage.SEVERITY_WARN, "formVolumeBalok:tinggi",
					retrieveMessage("msgTinggi") + " " + retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		
		if(!strBil1.isEmpty() && !strBil2.isEmpty() && !strBil3.isEmpty()){
			try{
				bil1 = Long.parseLong(strBil1);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formVolumeBalok:panjang",
						retrieveMessage("msgPanjang") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
			
			try{
				bil2 = Long.parseLong(strBil2);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formVolumeBalok:lebar",
						retrieveMessage("msgLebar") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
			
			try{
				bil3 = Long.parseLong(strBil3);
			} catch (NumberFormatException nfe) {
				addFacesMsg(FacesMessage.SEVERITY_WARN, "formVolumeBalok:tinggi",
						retrieveMessage("msgTinggi") + " " + retrieveMessage("errorMustBeNumber"), null);
				valid = false;
			}
		}
		
		return valid;
	}
	
	public void calculate(){
		if(isValidateBilangan()){
			title = "Hasil Volume Balok";
			MODE_TYPE = CommonConstants.MODE_TYPE_VIEW;
			hasil = bil1 * bil2 * bil3;
		} else {
			hasil = null;
		}
	}
	
	public void modelInput(){
		title = "Input Nilai Volume Balok";
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
	}
}