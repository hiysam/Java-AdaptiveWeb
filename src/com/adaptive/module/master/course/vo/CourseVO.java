package com.adaptive.module.master.course.vo;

import java.io.Serializable;

import com.adaptive.common.model.BaseEntity;

public class CourseVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 301157183437596760L;
	
	private Long courseId;
    private String courseCode;
    private String courseName;
    private String courseDesc;
    private boolean cheked;
	
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
	public boolean isCheked() {
		return cheked;
	}
	public void setCheked(boolean cheked) {
		this.cheked = cheked;
	}
}