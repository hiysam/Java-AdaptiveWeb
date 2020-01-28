package com.adaptive.module.configuration.appSetting.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.SortOrder;

import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.configuration.appSetting.dao.AppSettingDAO;
import com.adaptive.module.configuration.appSetting.model.AppSetting;
import com.adaptive.module.configuration.appSetting.vo.AppSettingVO;

@ManagedBean(name = "appSettingService")
@ViewScoped

public  class AppSettingServiceImpl implements AppSettingService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7185255314649541531L;
	
	
	@ManagedProperty(value = "#{appSettingDAO}")
	private AppSettingDAO appSettingDAO;


	@Override
	public List<AppSettingVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return appSettingDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}


	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return appSettingDAO.searchCountData(searchCriteria);
	}


	@Override
	public AppSetting findSettingByCode(String settingCode) {
		// TODO Auto-generated method stub
		return appSettingDAO.findSettingByCode(settingCode);
	}


	@Override
	public List<AppSettingVO> getAppSettingListByCode(String typeCode) {
		// TODO Auto-generated method stub
		return appSettingDAO.getAppSettingListByCode(typeCode);
	}


	public AppSettingDAO getAppSettingDAO() {
		return appSettingDAO;
	}


	public void setAppSettingDAO(AppSettingDAO appSettingDAO) {
		this.appSettingDAO = appSettingDAO;
	}
	
	
}