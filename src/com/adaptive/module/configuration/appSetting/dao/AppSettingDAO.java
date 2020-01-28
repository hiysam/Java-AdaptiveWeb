package com.adaptive.module.configuration.appSetting.dao;

import java.util.List;

import com.adaptive.common.dao.GenericDAO;
import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.configuration.appSetting.model.AppSetting;
import com.adaptive.module.configuration.appSetting.vo.AppSettingVO;


public interface AppSettingDAO extends GenericDAO<AppSetting, Long>, RetrieverDataPage<AppSettingVO>{
	
	public List<AppSettingVO> getAppSettingListByCode(String typeCode);

	public 	AppSetting findSettingByCode(String settingCode);
	
	
}