package com.adaptive.module.master.course.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.master.course.vo.CourseVO;

public class CourseVOLazyDataModel extends LazyDataModel<CourseVO>{

	private static final long serialVersionUID = -8346607968512620389L;
	
	private RetrieverDataPage<CourseVO> retrieverData;
	private List <? extends SearchObject> searchCriteria;
	
	static Logger logger = Logger.getLogger(CourseVOLazyDataModel.class);
	
	public CourseVOLazyDataModel(RetrieverDataPage<CourseVO> retrieverData, int pageSize){
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
	public Object getRowKey(CourseVO courseVO){
		return courseVO != null ? courseVO.getCourseId() : null;
	}
	
	@Override
	public CourseVO getRowData(String rowKey){
		List<CourseVO> courseList = (List<CourseVO>) getWrappedData();
		for(CourseVO courseVO : courseList){
			if(courseVO.getCourseId().toString().endsWith(rowKey)){
				return courseVO;
			}
		}
		return null;
	}
	
	@Override
	public List<CourseVO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> fiters){
		try{
			List<CourseVO> results = retrieverData.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
			
			setWrappedData(results);
			updateRowCount();
			return results;
		} catch (Exception e){
			logger.debug("Exception while trying search param detail, returning empty list", e);
			return new ArrayList<CourseVO>();
		}
	}
}