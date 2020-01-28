package com.adaptive.module.master.semester.model;

import java.io.Serializable;

import com.adaptive.common.model.BaseEntity;

public class Semester extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 301157183437596760L;
	
	private Long semesterId;
	private String semesterType;
	private String periode;
	private String semesterDesc;
	public Long getSemesterId() {
		return semesterId;
	}
	public void setSemesterId(Long semesterId) {
		this.semesterId = semesterId;
	}
	public String getSemesterType() {
		return semesterType;
	}
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public String getSemesterDesc() {
		return semesterDesc;
	}
	public void setSemesterDesc(String semesterDesc) {
		this.semesterDesc = semesterDesc;
	}
}