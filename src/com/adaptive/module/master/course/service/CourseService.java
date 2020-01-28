package com.adaptive.module.master.course.service;

import java.util.List;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.master.course.vo.CourseVO;

public interface CourseService extends RetrieverDataPage<CourseVO>{
	public void save(CourseVO courseVO, String user);
	public void update(CourseVO courseVO, String user);
	public void delete(Long id);
	public CourseVO findCourseById(Long id);
	public List<CourseVO> selectCourseList();
	public Integer countCourseByCourseCode(String courseCode);
}