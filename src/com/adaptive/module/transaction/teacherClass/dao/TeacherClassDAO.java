package com.adaptive.module.transaction.teacherClass.dao;

import java.io.Serializable;
import java.util.List;

import com.adaptive.common.dao.GenericDAO;
import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

public interface TeacherClassDAO extends GenericDAO<TeacherClass, Serializable>, RetrieverDataPage<TeacherClassVO>{
	
	
	public List<TeacherClass> findTcrClassByTeacherId(Long teacherId);
	public List<TeacherClass> findTcrClassByCourseId(Long courseId);
	public List<TeacherClassVO> getTeacherClassVOs();
}