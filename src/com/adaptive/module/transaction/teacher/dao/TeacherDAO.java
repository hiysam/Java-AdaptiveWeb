package com.adaptive.module.transaction.teacher.dao;

import java.io.Serializable;
import java.util.List;

import com.adaptive.common.dao.GenericDAO;
import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.transaction.teacher.model.Teacher;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;



public interface TeacherDAO extends GenericDAO<Teacher, Serializable>, RetrieverDataPage<TeacherVO>{
	
	public List<TeacherVO> getTeacherVOs();
	
	
}