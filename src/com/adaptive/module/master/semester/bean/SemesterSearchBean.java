package com.adaptive.module.master.semester.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.adaptive.common.bean.CommonBean;

import com.adaptive.common.vo.SearchObject;
import com.adaptive.common.vo.SearchValueObject;
import com.adaptive.module.master.semester.model.SemesterVOLazyDataModel;
import com.adaptive.module.master.semester.service.SemesterService;
import com.adaptive.module.master.semester.vo.SemesterVO;
import common.Logger;

@ManagedBean(name = "semesterSearch")
@ViewScoped
public class SemesterSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -4306180130150661106L;
	static Logger logger = Logger.getLogger(SemesterSearchBean.class);
	
	@ManagedProperty(value = "#{semesterService}")
    SemesterService sService;
    
    private SemesterVOLazyDataModel semesterVOListLazyTable;
    private List<SearchObject> searchCriteria;
    private List<SemesterVO> selectedSemesters;
    private String searchAll;
    private Integer checkSearch;
    
    @PostConstruct
    public void postConstruct() {
    	super.init();
            
        selectedSemesters = new ArrayList<SemesterVO>();
        semesterVOListLazyTable = new SemesterVOLazyDataModel(sService, paging);
    }
    
    public void clear() {
    	searchAll = "";
        searchCriteria = null;
        semesterVOListLazyTable.setSearchCriteria(searchCriteria);
    }
    
    public void onRowSelect(SelectEvent event){
        if (event != null && event.getObject() != null && event.getObject() instanceof SemesterVO) {
        	if (selectedSemesters == null){
        		selectedSemesters = new ArrayList<SemesterVO>();
            }
            if (!selectedSemesters.contains((SemesterVO) event.getObject())) {
            	selectedSemesters.add((SemesterVO) event.getObject());
            }
        }
    }
    
    public void onRowUnselect(UnselectEvent event)
	{
		if(event != null && event.getObject() != null && event.getObject() instanceof SemesterVO
			&& selectedSemesters != null && selectedSemesters.contains((SemesterVO) event.getObject()))
		{
			selectedSemesters.remove((SemesterVO) event.getObject());		
		}
		
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void search() {
    	searchCriteria = null;
        if (searchAll != null && StringUtils.isNotBlank(searchAll)) {
        	searchCriteria = new ArrayList<SearchObject>();
            searchCriteria.add(new SearchValueObject("searchAll", searchAll));
        }
            semesterVOListLazyTable.setSearchCriteria(searchCriteria);
    }
    
    public void delete(SemesterVO semesterVO) {
    	try {
    		sService.delete(semesterVO.getSemesterId());
                    
            facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
            facesUtils.retrieveMessage("common_msg_deleted"), null);
        } catch (Exception e) {
        	e.printStackTrace();
            facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
            facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses",
            facesUtils.retrieveMessage("formCourseTitle")), null);
        }
    }
    
    public void multipleDelete() {
    	try {
    		List<SemesterVO> courseVODeleteList = new ArrayList<SemesterVO>();
            if (selectedSemesters.size() > 0) {
            	for (SemesterVO semesterVO : selectedSemesters) {
            		sService.delete(semesterVO.getSemesterId());
                    courseVODeleteList.add(semesterVO);
                }
                    courseVODeleteList.removeAll(selectedSemesters);
                    facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
                    facesUtils.retrieveMessage("common_msg_deleted"), null);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
            facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses",
            facesUtils.retrieveMessage("formCourseTitle")), null);
        }
    }

    
    public SemesterVOLazyDataModel getSemesterVOListLazyTable() {
		return semesterVOListLazyTable;
	}

	public void setSemesterVOListLazyTable(SemesterVOLazyDataModel semesterVOListLazyTable) {
		this.semesterVOListLazyTable = semesterVOListLazyTable;
	}

	public List<SemesterVO> getSelectedSemesters() {
		return selectedSemesters;
	}

	public void setSelectedSemesters(List<SemesterVO> selectedSemesters) {
		this.selectedSemesters = selectedSemesters;
	}

    public List<SearchObject> getSearchCriteria() {
            return searchCriteria;
    }

    public void setSearchCriteria(List<SearchObject> searchCriteria) {
            this.searchCriteria = searchCriteria;
    }

    public String getSearchAll() {
            return searchAll;
    }

    public void setSearchAll(String searchAll) {
            this.searchAll = searchAll;
    }

    public Integer getCheckSearch() {
            return checkSearch;
    }

    public void setCheckSearch(Integer checkSearch) {
            this.checkSearch = checkSearch;
    }

	public SemesterService getsService() {
		return sService;
	}

	public void setsService(SemesterService sService) {
		this.sService = sService;
	}
    
}