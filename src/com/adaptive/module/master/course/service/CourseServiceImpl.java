package com.adaptive.module.master.course.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.SortOrder;

import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.master.course.dao.CourseDAO;
import com.adaptive.module.master.course.model.Course;
import com.adaptive.module.master.course.vo.CourseVO;

@ManagedBean (name ="courseService")
@ViewScoped
public class CourseServiceImpl implements CourseService, Serializable{

	private static final long serialVersionUID = 6725788461638618345L;
	
	@ManagedProperty(value = "#{courseDAO}")
    private CourseDAO courseDAO;
	
	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	@Override
	public void save(CourseVO courseVO, String user){
		//TODO Auto-generated method stub
		Course course = new Course();
		course.setCourseCode(courseVO.getCourseCode());
		System.out.println("get VO : "+ courseVO.getCourseCode());
		course.setCourseName(courseVO.getCourseName());
		System.out.println("get VO : "+ courseVO.getCourseName());
		course.setCourseDesc(courseVO.getCourseDesc());	
		course.setCreateBy(user);
		course.setCreateOn(new Timestamp(System.currentTimeMillis()));
		course.setActiveFlag("Y");
		
		
		courseDAO.save(course);
		courseDAO.flush();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CourseVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return courseDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return courseDAO.searchCountData(searchCriteria);
	}

	@Override
	public void update(CourseVO courseVO, String user) {
		// TODO Auto-generated method stub
		Course course = courseDAO.findById(courseVO.getCourseId());
		course.setCourseCode(courseVO.getCourseCode());
		course.setCourseName(courseVO.getCourseName());
		course.setCourseDesc(courseVO.getCourseDesc());
		course.setUpdateBy(user);
		course.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		courseDAO.update(course);
		courseDAO.flush();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Course course = courseDAO.findById(id);
		courseDAO.delete(course);
		courseDAO.flush();
	}

	@Override
	public CourseVO findCourseById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseVO> selectCourseList() {
		// TODO Auto-generated method stub
		return courseDAO.selectCourseList();
	}

	@Override
	public Integer countCourseByCourseCode(String courseCode) {
		// TODO Auto-generated method stub
		return courseDAO.countUniqueKey(courseCode, "courseCode", new Course());
	}
}