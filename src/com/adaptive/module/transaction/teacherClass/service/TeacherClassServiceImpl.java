package com.adaptive.module.transaction.teacherClass.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.SortOrder;

import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.transaction.teacherClass.dao.TeacherClassDAO;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

@ManagedBean(name = "teacherClassService")
@ViewScoped
public class TeacherClassServiceImpl implements TeacherClassService, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -163437364117828464L;
	
	@ManagedProperty(value = "#{teacherClassDAO}")
	private TeacherClassDAO teacherClassDAO;

	@Override
	public List<TeacherClassVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return teacherClassDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return teacherClassDAO.searchCountData(searchCriteria);
	}

	@Override
	public List<TeacherClass> findTcrClassByTeacherId(Long teacherId) {
		// TODO Auto-generated method stub
		return teacherClassDAO.findTcrClassByTeacherId(teacherId);
	}

	@Override
	public List<TeacherClass> findTcrClassByCourseId(Long courseId) {
		// TODO Auto-generated method stub
		return teacherClassDAO.findTcrClassByCourseId(courseId);
	}

	@Override
	public List<TeacherClassVO> getTeacherClassVOs() {
		// TODO Auto-generated method stub
		return teacherClassDAO.getTeacherClassVOs();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		TeacherClass teacherClass = teacherClassDAO.findById(id);
		teacherClassDAO.delete(teacherClass);
		teacherClassDAO.flush();
	}

	public TeacherClassDAO getTeacherClassDAO() {
		return teacherClassDAO;
	}

	public void setTeacherClassDAO(TeacherClassDAO teacherClassDAO) {
		this.teacherClassDAO = teacherClassDAO;
	}
	
}
