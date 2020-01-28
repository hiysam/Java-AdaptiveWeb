package com.adaptive.module.master.course.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.bean.CommonBean;
import com.adaptive.common.constant.CommonConstants;
import com.adaptive.module.master.course.vo.CourseVO;

@ManagedBean(name = "courseBean")
@ViewScoped
public class CourseBean extends CommonBean{

	private static final long serialVersionUID = -1198491757869334246L;

	@ManagedProperty(value = "#{courseInputBean}")
	private CourseInputBean courseInputBean;
	
	private String MODE_TYPE;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		courseInputBean.modeAdd();
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSave(){
		courseInputBean.save();
		MODE_TYPE = courseInputBean.getMODE_TYPE();
	}
	
	public void modeEdit(CourseVO courseVO){
		MODE_TYPE = CommonConstants.MODE_TYPE_EDIT;
		courseInputBean.modeEdit(courseVO);
	}

	public CourseInputBean getCourseInputBean() {
		return courseInputBean;
	}

	public void setCourseInputBean(CourseInputBean courseInputBean) {
		this.courseInputBean = courseInputBean;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}
	
}