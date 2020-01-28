package com.adaptive.module.transaction.teacherClass.service;

import java.util.List;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

public interface TeacherClassService extends RetrieverDataPage<TeacherClassVO>{
	
	public List<TeacherClass> findTcrClassByTeacherId(Long teacherId);
	public List<TeacherClass> findTcrClassByCourseId(Long internalId);
	public List<TeacherClassVO> getTeacherClassVOs();
	public void delete (Long id);
}