package com.adaptive.module.master.course.dao;

import java.util.List;

import com.adaptive.common.dao.GenericDAO;
import com.adaptive.common.lov.paging.RetrieverDataPage;
import com.adaptive.module.master.course.model.Course;
import com.adaptive.module.master.course.vo.CourseVO;

public interface CourseDAO extends GenericDAO<Course, Long>, RetrieverDataPage<CourseVO>{
	public List<CourseVO> selectCourseList();
}