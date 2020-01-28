package com.adaptive.module.master.course.model;

import java.io.Serializable;

import com.adaptive.common.model.BaseEntity;

public class Course extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 301157183437596760L;
	
	private Long courseId;
	private String courseCode;
	private String courseName;
	private String courseDesc;
	
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDesc() {
		return courseDesc;
	}
	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}
}