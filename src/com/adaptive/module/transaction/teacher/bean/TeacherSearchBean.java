package com.adaptive.module.transaction.teacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.adaptive.common.bean.CommonBean;
import com.adaptive.common.constant.CommonConstants;
import com.adaptive.common.paging.PagingTableModel;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.common.vo.SearchValueObject;
import com.adaptive.module.transaction.teacher.model.TeacherPagingTableModel;
import com.adaptive.module.transaction.teacher.service.TeacherService;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;
import com.adaptive.module.transaction.teacherClass.service.TeacherClassService;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

@ManagedBean(name = "teacherSearch")
@ViewScoped
public class TeacherSearchBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 2165839158137352537L;

	static Logger logger = Logger.getLogger(TeacherSearchBean.class);
	
	@ManagedProperty(value = "#{teacherService}")
	private TeacherService teacherService;
	
	@ManagedProperty(value = "#{teacherClassService}")
	private TeacherClassService teacherClassService;
	
	private TeacherPagingTableModel teacherListLazyTable;
	private PagingTableModel<TeacherClassVO> tcrClassListLazyTable;
	
	private List<SearchObject> searchCriteria;
	
	private TeacherClassVO teacherClassVO;
	private TeacherVO teacherVO;
	private TeacherClass teacherClass;
	private String headerDialog;
	private String noSearch;
	private String nameSearch;
	private String genderSearch;
	private String addressSearch;
	private String searchAll;
	private Integer checkSearch;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		searchAll = "";
		teacherListLazyTable = new TeacherPagingTableModel(teacherService, paging);
	}
	
	public void clear(){
		searchAll = "";
		searchCriteria = null;
		teacherListLazyTable.setSearchCriteria(searchCriteria);
	}
	
	public void clearSearchDialog(){
		noSearch = "";
		nameSearch = "";
		genderSearch = "";
		addressSearch = "";
		
		searchCriteria = null;
		teacherListLazyTable.setSearchCriteria(searchCriteria);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void search(){
		searchCriteria = null;
		if(searchAll != null && StringUtils.isNotBlank(searchAll)){
			searchCriteria = new ArrayList<SearchObject>();
			searchCriteria.add(new SearchValueObject("searchAll", searchAll));
		}
		
		teacherListLazyTable.setSearchCriteria(searchCriteria);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void teacherSearchDialog(){
		searchCriteria = null;
		searchCriteria = new ArrayList<SearchObject>();
		
		if(noSearch != null && StringUtils.isNotBlank(noSearch)){
			searchCriteria.add(new SearchValueObject("teacherNumber", noSearch));
		}
		
		if(nameSearch != null && StringUtils.isNotBlank(nameSearch)){
			searchCriteria.add(new SearchValueObject("teacherName", nameSearch));
		}
		
		if(genderSearch != null && StringUtils.isNotBlank(genderSearch)){
			searchCriteria.add(new SearchValueObject("genderCode", genderSearch));
		}
		
		if(addressSearch != null && StringUtils.isNotBlank(addressSearch)){
			searchCriteria.add(new SearchValueObject("teacherAddress", addressSearch));
		}
		System.out.println(searchCriteria);
		
		teacherListLazyTable.setSearchCriteria(searchCriteria);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void detailTeacher(TeacherVO teacherVO){
		List<SearchObject> searchCriteriaClass = new ArrayList<SearchObject>();
		tcrClassListLazyTable = new PagingTableModel <TeacherClassVO>(teacherClassService, paging);
		
		if(teacherVO != null){
			searchCriteriaClass.add(new SearchValueObject("teacherId", teacherVO.getTeacherId()));
			tcrClassListLazyTable.setSearchCriteria(searchCriteriaClass);
			headerDialog = teacherVO.getTeacherName();
		}
	}
	
	public void delete(TeacherVO teacherVO){
		try{
			teacherService.delete(teacherVO.getTeacherId());
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
					facesUtils.retrieveMessage("common_msg_deleted"), null);
		}catch (Exception e){
			e.printStackTrace();
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses",
							facesUtils.retrieveMessage("formTeacherTitle")), null);
		}
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public TeacherClassService getTeacherClassService() {
		return teacherClassService;
	}

	public void setTeacherClassService(TeacherClassService teacherClassService) {
		this.teacherClassService = teacherClassService;
	}

	public TeacherPagingTableModel getTeacherListLazyTable() {
		return teacherListLazyTable;
	}

	public void setTeacherListLazyTable(TeacherPagingTableModel teacherListLazyTable) {
		this.teacherListLazyTable = teacherListLazyTable;
	}

	public PagingTableModel<TeacherClassVO> getTcrClassListLazyTable() {
		return tcrClassListLazyTable;
	}

	public void setTcrClassListLazyTable(PagingTableModel<TeacherClassVO> tcrClassListLazyTable) {
		this.tcrClassListLazyTable = tcrClassListLazyTable;
	}

	public List<SearchObject> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(List<SearchObject> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public TeacherClassVO getTeacherClassVO() {
		return teacherClassVO;
	}

	public void setTeacherClassVO(TeacherClassVO teacherClassVO) {
		this.teacherClassVO = teacherClassVO;
	}

	public TeacherVO getTeacherVO() {
		return teacherVO;
	}

	public void setTeacherVO(TeacherVO teacherVO) {
		this.teacherVO = teacherVO;
	}

	public TeacherClass getTeacherClass() {
		return teacherClass;
	}

	public void setTeacherClass(TeacherClass teacherClass) {
		this.teacherClass = teacherClass;
	}

	public String getHeaderDialog() {
		return headerDialog;
	}

	public void setHeaderDialog(String headerDialog) {
		this.headerDialog = headerDialog;
	}

	public String getNoSearch() {
		return noSearch;
	}

	public void setNoSearch(String noSearch) {
		this.noSearch = noSearch;
	}

	public String getNameSearch() {
		return nameSearch;
	}

	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	public String getGenderSearch() {
		return genderSearch;
	}

	public void setGenderSearch(String genderSearch) {
		this.genderSearch = genderSearch;
	}

	public String getAddressSearch() {
		return addressSearch;
	}

	public void setAddressSearch(String addressSearch) {
		this.addressSearch = addressSearch;
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