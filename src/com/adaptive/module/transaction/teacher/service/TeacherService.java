package com.adaptive.module.transaction.teacher.service;

import java.util.List;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;

public interface TeacherService extends RetrieverDataPage<TeacherVO>{
	public void save(TeacherVO teacherVO, String user);
	public void update(TeacherVO teacherVO, String user);
	public void delete(Long id);
	public TeacherVO findSemesterById(Long id);
	public List<TeacherVO> selectTeacherList();
	public Integer countByTeacherNumber(String teacherNumber);
}