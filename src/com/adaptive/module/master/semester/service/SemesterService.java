package com.adaptive.module.master.semester.service;

import java.util.List;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.master.semester.vo.SemesterVO;

public interface SemesterService extends RetrieverDataPage<SemesterVO>{
	public void save(SemesterVO semesterVO, String user);
	public void update(SemesterVO semesterVO, String user);
	public void delete(Long id);
	public SemesterVO findSemesterById(Long id);
	public List<SemesterVO> selectSemesterList();
	public Integer countSemesterBySemesterType(String semesterType);
}