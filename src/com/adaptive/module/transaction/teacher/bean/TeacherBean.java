package com.adaptive.module.transaction.teacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.adaptive.common.bean.CommonBean;
import com.adaptive.common.constant.CommonConstants;
import com.adaptive.module.configuration.appSetting.service.AppSettingService;
import com.adaptive.module.configuration.appSetting.vo.AppSettingVO;
import com.adaptive.module.master.semester.bean.SemesterInputBean;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;

@ManagedBean(name = "teacherBean")
@ViewScoped
public class TeacherBean extends CommonBean implements Serializable{
	
	
	private static final long serialVersionUID = -5926795656246581469L;

	@ManagedProperty(value = "#{teacherInput}")
	private TeacherInputBean teacherInput;
	
	@ManagedProperty(value = "#{appSettingService}")
	private AppSettingService appSettingService;
	
	private List<SelectItem> genderSelectItem;
	
	private String MODE_TYPE;
	
	private TeacherInputBean teacherInputBean;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		selectList();
	}
	
	private void selectList(){
		genderSelectItem = new ArrayList<SelectItem>();
		List<AppSettingVO> appSettingGenderList = new ArrayList<AppSettingVO>();
		appSettingGenderList = appSettingService.getAppSettingListByCode(CommonConstants.GENDER);
		for(AppSettingVO gender : appSettingGenderList){
			genderSelectItem.add(new SelectItem(gender.getSettingCode(), gender.getSettingName()));
		}
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		teacherInput.modeAdd();
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSave(){
		teacherInput.save();
		MODE_TYPE = teacherInput.getMODE_TYPE();
	}
	
	public void modeEdit(TeacherVO teacherVO){
		MODE_TYPE = CommonConstants.MODE_TYPE_EDIT;
		teacherInput.modeEdit(teacherVO);
	}

	public TeacherInputBean getTeacherInput() {
		return teacherInput;
	}

	public void setTeacherInput(TeacherInputBean teacherInput) {
		this.teacherInput = teacherInput;
	}

	public AppSettingService getAppSettingService() {
		return appSettingService;
	}

	public void setAppSettingService(AppSettingService appSettingService) {
		this.appSettingService = appSettingService;
	}

	public List<SelectItem> getGenderSelectItem() {
		return genderSelectItem;
	}

	public void setGenderSelectItem(List<SelectItem> genderSelectItem) {
		this.genderSelectItem = genderSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public TeacherInputBean getTeacherInputBean() {
		return teacherInputBean;
	}

	public void setTeacherInputBean(TeacherInputBean teacherInputBean) {
		this.teacherInputBean = teacherInputBean;
	}
	
	
}