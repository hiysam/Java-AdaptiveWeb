package com.adaptive.module.transaction.teacher.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adaptive.common.model.BaseEntity;
import com.adaptive.module.configuration.appSetting.model.AppSetting;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;

public class Teacher extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -3284769615851970735L;
	
	private Long teacherId;
	private String teacherNumber;
	private String teacherName;
	private AppSetting gender;
	private String teacherBirthPlace;
	private Date teacherBirthDate;
	private String teacherAddress;
	
	private List<TeacherClass> teacherClass = new ArrayList<TeacherClass>();

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherNumber() {
		return teacherNumber;
	}

	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherBirthPlace() {
		return teacherBirthPlace;
	}

	public void setTeacherBirthPlace(String teacherBirthPlace) {
		this.teacherBirthPlace = teacherBirthPlace;
	}

	public Date getTeacherBirthDate() {
		return teacherBirthDate;
	}

	public void setTeacherBirthDate(Date teacherBirthDate) {
		this.teacherBirthDate = teacherBirthDate;
	}

	public String getTeacherAddress() {
		return teacherAddress;
	}

	public void setTeacherAddress(String teacherAddress) {
		this.teacherAddress = teacherAddress;
	}

	public List<TeacherClass> getTeacherClass() {
		return teacherClass;
	}

	public void setTeacherClass(List<TeacherClass> teacherClass) {
		this.teacherClass = teacherClass;
	}

	public AppSetting getGender() {
		return gender;
	}

	public void setGender(AppSetting gender) {
		this.gender = gender;
	}
	
	
	
}