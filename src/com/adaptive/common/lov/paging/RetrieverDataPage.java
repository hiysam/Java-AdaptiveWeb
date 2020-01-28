package com.adaptive.common.lov.paging;

import java.util.List;

import org.primefaces.model.SortOrder;

import com.adaptive.common.vo.SearchObject;

public interface RetrieverDataPage <E>{

    List<E> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
                    String sortField, SortOrder sortOrder) throws Exception ;

    Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception;
 
}
