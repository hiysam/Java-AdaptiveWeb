package com.adaptive.module.transaction.teacher.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adaptive.common.model.BaseEntity;
import com.adaptive.module.configuration.appSetting.model.AppSetting;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

public class TeacherVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -3284769615851970735L;
	
	private Long teacherId;
	private String teacherNumber;
	private String teacherName;
	private String teacherGender;
	private String teacherBirthPlace;
	private Date teacherBirthDate;
	private String teacherAddress;
	private boolean cheked;
	private String genderName;
	private List<TeacherClassVO> tcrClassVOList = new ArrayList<TeacherClassVO>();
	
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
	public String getTeacherGender() {
		return teacherGender;
	}
	public void setTeacherGender(String teacherGender) {
		this.teacherGender = teacherGender;
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
	public boolean isCheked() {
		return cheked;
	}
	public void setCheked(boolean cheked) {
		this.cheked = cheked;
	}
	public List<TeacherClassVO> getTcrClassVOList() {
		return tcrClassVOList;
	}
	public void setTcrClassVOList(List<TeacherClassVO> tcrClassVOList) {
		this.tcrClassVOList = tcrClassVOList;
	}
	public String getGenderName() {
		return genderName;
	}
	
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}
}