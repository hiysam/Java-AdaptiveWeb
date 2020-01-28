package com.adaptive.common.paging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.adaptive.common.vo.SearchObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class PagingTableModel<E> extends LazyDataModel<E> {

	private static final long serialVersionUID = -5872869494149656422L;
	private RetrieverDataPage<E> retrieverData;
	@SuppressWarnings("rawtypes")
	private List<? extends SearchObject> searchCriteria;
	static Logger logger = Logger.getLogger(PagingTableModel.class);

	public PagingTableModel(RetrieverDataPage<E> retrieverData, int pageSize) {
		this.retrieverData = retrieverData;
		setPageSize(pageSize);
		updateRowCount();
	}

	@SuppressWarnings("rawtypes")
	public void setSearchCriteria(List<? extends SearchObject> searchCriteria) {
		this.searchCriteria = searchCriteria;
		updateRowCount();

	}

	public List<? extends SearchObject> getSearchCriteria() {
		return searchCriteria;
	}

	@Override
	public E getRowData(String rowKey) {
		/*
		 * for(E obj : this.retrieverData) { if(obj.getModel().equals(rowKey))
		 * return car; }
		 */
		List<E> entityList = getWrappedData();
		for (E obj: entityList) {
			
		}
		return null;
	}
	
	@Override
	public Object getRowKey(E object) {
		// TODO Auto-generated method stub
		return super.getRowKey(object);
		//return null;
	}

	public void updateRowCount() {
			try {

				Long totalRowCount = retrieverData
						.searchCountData(searchCriteria);

				setRowCount(totalRowCount.intValue());
			} catch (Exception ex) {
				logger.debug(
						"Exception while searching row count, use 0 as result",
						ex);
				setRowCount(0);
			}

	}

	@Override
	public List<E> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		try {
			List<E> results = retrieverData.searchData(
					searchCriteria, first, pageSize, sortField, sortOrder);
			
			setWrappedData(results);
			updateRowCount();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Exception while trying search param detail, returning empty list", e);
			return new ArrayList<E>();
		}
		
	}
}
