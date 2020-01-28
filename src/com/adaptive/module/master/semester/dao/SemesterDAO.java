package com.adaptive.module.master.semester.dao;

import java.util.List;

import com.adaptive.common.dao.GenericDAO;
import com.adaptive.common.lov.paging.RetrieverDataPage;
import com.adaptive.module.master.semester.model.Semester;
import com.adaptive.module.master.semester.vo.SemesterVO;

public interface SemesterDAO extends GenericDAO<Semester, Long>, RetrieverDataPage<SemesterVO>{
	public List<SemesterVO> selectSemesterList();
}