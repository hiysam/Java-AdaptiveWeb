package com.adaptive.module.master.semester.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.bean.CommonBean;
import com.adaptive.common.constant.CommonConstants;
import com.adaptive.module.master.semester.vo.SemesterVO;

@ManagedBean(name = "semesterBean")
@ViewScoped
public class SemesterBean extends CommonBean{

	private static final long serialVersionUID = -1198491757869334246L;

	@ManagedProperty(value = "#{semesterInputBean}")
	private SemesterInputBean semesterInputBean;
	
	private String MODE_TYPE;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		semesterInputBean.modeAdd();
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSave(){
		semesterInputBean.save();
		MODE_TYPE = semesterInputBean.getMODE_TYPE();
	}
	
	public void modeEdit(SemesterVO semesterVO){
		MODE_TYPE = CommonConstants.MODE_TYPE_EDIT;
		semesterInputBean.modeEdit(semesterVO);
	}

	public SemesterInputBean getSemesterInputBean() {
		return semesterInputBean;
	}

	public void setSemesterInputBean(SemesterInputBean semesterInputBean) {
		this.semesterInputBean = semesterInputBean;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}
	
}