package com.adaptive.module.master.semester.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.master.semester.vo.SemesterVO;

public class SemesterVOLazyDataModel extends LazyDataModel<SemesterVO>{

	private static final long serialVersionUID = -8346607968512620389L;
	
	private RetrieverDataPage<SemesterVO> retrieverData;
	private List <? extends SearchObject> searchCriteria;
	
	static Logger logger = Logger.getLogger(SemesterVOLazyDataModel.class);
	
	public SemesterVOLazyDataModel(RetrieverDataPage<SemesterVO> retrieverData, int pageSize){
		this.retrieverData = retrieverData;
		setPageSize(pageSize);
		updateRowCount();
	}
	
	public void setSearchCriteria(List<? extends SearchObject> searchCriteria){
		this.searchCriteria = searchCriteria;
		updateRowCount();
	}
	
	public void updateRowCount(){
		try{
			Long totalRowCount = retrieverData.searchCountData(searchCriteria);
			
			setRowCount(totalRowCount.intValue());
		} catch (Exception ex) {
			logger.debug("Exception while searching row count, use 0 as result", ex);
			setRowCount(0);
		}
	}
	
	@Override
	public Object getRowKey(SemesterVO semesterVO){
		return semesterVO != null ? semesterVO.getSemesterId() : null;
	}
	
	@Override
	public SemesterVO getRowData(String rowKey){
		List<SemesterVO> semesterList = (List<SemesterVO>) getWrappedData();
		for(SemesterVO semesterVO : semesterList){
			if(semesterVO.getSemesterId().toString().endsWith(rowKey)){
				return semesterVO;
			}
		}
		return null;
	}
	
	@Override
	public List<SemesterVO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> fiters){
		try{
			List<SemesterVO> results = retrieverData.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
			
			setWrappedData(results);
			updateRowCount();
			return results;
		} catch (Exception e){
			logger.debug("Exception while trying search param detail, returning empty list", e);
			return new ArrayList<SemesterVO>();
		}
	}
}