package com.adaptive.module.transaction.teacherClass.model;

import java.io.Serializable;

import com.adaptive.common.model.BaseEntity;
import com.adaptive.module.master.course.model.Course;
import com.adaptive.module.transaction.teacher.model.Teacher;

public class TeacherClass extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 2497524318832975550L;
	
	private Long id;
	private Course crs;
	private Teacher tcr;
	private Integer dayOfWeek;
	private String timeStart;
	private String timeEnd;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Course getCrs() {
		return crs;
	}
	public void setCrs(Course crs) {
		this.crs = crs;
	}
	public Teacher getTcr() {
		return tcr;
	}
	public void setTcr(Teacher tcr) {
		this.tcr = tcr;
	}
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
}
