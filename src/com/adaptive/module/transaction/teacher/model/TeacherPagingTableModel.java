package com.adaptive.module.transaction.teacher.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;

public class TeacherPagingTableModel extends LazyDataModel<TeacherVO>{

	private static final long serialVersionUID = 4299341380265534363L;
	private RetrieverDataPage <TeacherVO> retrieverData;
	private List<? extends SearchObject> searchCriteria;
	static Logger logger = Logger.getLogger(TeacherPagingTableModel.class);
	
	public TeacherPagingTableModel(RetrieverDataPage <TeacherVO> retrieverData, int pageSize){
		this.retrieverData = retrieverData;
		setPageSize(pageSize);
		updateRowCount();
	}
	
	public void setSearchCriteria(List<? extends SearchObject> searchCriteria){
		this.searchCriteria = searchCriteria;
		updateRowCount();
		}
	
	public List<? extends SearchObject> getSearchCriteria(){
		return searchCriteria;
	}
	
	public void updateRowCount(){
		try{
			Long totalRowCount = retrieverData.searchCountData(searchCriteria);
			setRowCount(totalRowCount.intValue());
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.debug("Exception while searching row count, use 0 as result", ex);
			setRowCount(0);
		}
	}
	
	
	public List<TeacherVO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
		try{
			List<TeacherVO> results = retrieverData.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
			setWrappedData(results);
			updateRowCount();
			return results;
		} catch (Exception e){
			logger.debug("Exception while trying search param detail, returning empty list", e);
			return new ArrayList<TeacherVO>();
		}
	}
}