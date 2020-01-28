package com.adaptive.module.master.semester.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.SortOrder;

import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.master.semester.dao.SemesterDAO;
import com.adaptive.module.master.semester.model.Semester;
import com.adaptive.module.master.semester.vo.SemesterVO;

@ManagedBean (name ="semesterService")
@ViewScoped
public class SemesterServiceImpl implements SemesterService, Serializable{

	private static final long serialVersionUID = 6725788461638618345L;
	
	@ManagedProperty(value = "#{semesterDAO}")
    private SemesterDAO semesterDAO;
	
	public SemesterDAO getSemesterDAO() {
		return semesterDAO;
	}

	public void setSemesterDAO(SemesterDAO semesterDAO) {
		this.semesterDAO = semesterDAO;
	}

	@Override
	public void save(SemesterVO semesterVO, String user){
		//TODO Auto-generated method stub
		Semester semester = new Semester();
		semester.setSemesterType(semesterVO.getSemesterType());
		semester.setPeriode(semesterVO.getPeriode());
		semester.setSemesterDesc(semesterVO.getSemesterDesc());
		
		
		semesterDAO.save(semester);
		semesterDAO.flush();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SemesterVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return semesterDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return semesterDAO.searchCountData(searchCriteria);
	}

	@Override
	public void update(SemesterVO semesterVO, String user) {
		// TODO Auto-generated method stub
		Semester semester = semesterDAO.findById(semesterVO.getSemesterId());
		semester.setSemesterType(semesterVO.getSemesterType());
		semester.setPeriode(semesterVO.getPeriode());
		semester.setSemesterDesc(semesterVO.getSemesterDesc());
		
		semesterDAO.update(semester);
		semesterDAO.flush();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Semester semester = semesterDAO.findById(id);
		semesterDAO.delete(semester);
		semesterDAO.flush();
	}

	@Override
	public SemesterVO findSemesterById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SemesterVO> selectSemesterList() {
		// TODO Auto-generated method stub
		return semesterDAO.selectSemesterList();
	}

	@Override
	public Integer countSemesterBySemesterType(String semesterType) {
		// TODO Auto-generated method stub
		return semesterDAO.countUniqueKey(semesterType, "semesterType", new Semester());
	}
}