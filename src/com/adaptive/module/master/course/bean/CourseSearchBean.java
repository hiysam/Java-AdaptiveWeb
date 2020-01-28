package com.adaptive.module.master.course.bean;

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
import com.adaptive.module.master.course.model.CourseVOLazyDataModel;
import com.adaptive.module.master.course.service.CourseService;
import com.adaptive.module.master.course.vo.CourseVO;
import common.Logger;

@ManagedBean(name = "courseSearch")
@ViewScoped
public class CourseSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -4306180130150661106L;
	static Logger logger = Logger.getLogger(CourseSearchBean.class);
	
	@ManagedProperty(value = "#{courseService}")
    CourseService cService;
    
    private CourseVOLazyDataModel courseVOListLazyTable;
    private List<SearchObject> searchCriteria;
    private List<CourseVO> selectedCourses;
    private String searchAll;
    private Integer checkSearch;
    
    @PostConstruct
    public void postConstruct() {
    	super.init();
            
        selectedCourses = new ArrayList<CourseVO>();
        courseVOListLazyTable = new CourseVOLazyDataModel(cService, paging);
    }
    
    public void clear() {
    	searchAll = "";
        searchCriteria = null;
        courseVOListLazyTable.setSearchCriteria(searchCriteria);
    }
    
    public void onRowSelect(SelectEvent event){
        if (event != null && event.getObject() != null && event.getObject() instanceof CourseVO) {
        	if (selectedCourses == null){
        		selectedCourses = new ArrayList<CourseVO>();
            }
            if (!selectedCourses.contains((CourseVO) event.getObject())) {
            	selectedCourses.add((CourseVO) event.getObject());
            }
        }
    }
    
    public void onRowUnselect(UnselectEvent event)
	{
		if(event != null && event.getObject() != null && event.getObject() instanceof CourseVO
			&& selectedCourses != null && selectedCourses.contains((CourseVO) event.getObject()))
		{
			selectedCourses.remove((CourseVO) event.getObject());		
		}
		
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void search() {
    	searchCriteria = null;
        if (searchAll != null && StringUtils.isNotBlank(searchAll)) {
        	searchCriteria = new ArrayList<SearchObject>();
            searchCriteria.add(new SearchValueObject("searchAll", searchAll));
        }
            courseVOListLazyTable.setSearchCriteria(searchCriteria);
    }
    
    public void delete(CourseVO courseVO) {
    	try {
    		cService.delete(courseVO.getCourseId());
                    
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
    		List<CourseVO> courseVODeleteList = new ArrayList<CourseVO>();
            if (selectedCourses.size() > 0) {
            	for (CourseVO courseVO : selectedCourses) {
            		cService.delete(courseVO.getCourseId());
                    courseVODeleteList.add(courseVO);
                }
                    courseVODeleteList.removeAll(selectedCourses);
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

    public CourseService getcService() {
            return cService;
    }

    public void setcService(CourseService cService) {
            this.cService = cService;
    }

    public CourseVOLazyDataModel getCourseVOListLazyTable() {
            return courseVOListLazyTable;
    }

    public void setCourseVOListLazyTable(CourseVOLazyDataModel courseVOListLazyTable) {
            this.courseVOListLazyTable = courseVOListLazyTable;
    }

    public List<SearchObject> getSearchCriteria() {
            return searchCriteria;
    }

    public void setSearchCriteria(List<SearchObject> searchCriteria) {
            this.searchCriteria = searchCriteria;
    }

    public List<CourseVO> getSelectedCourses() {
            return selectedCourses;
    }

    public void setSelectedCourses(List<CourseVO> selectedCourses) {
            this.selectedCourses = selectedCourses;
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
}