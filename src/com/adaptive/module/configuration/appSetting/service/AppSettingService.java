package com.adaptive.module.configuration.appSetting.service;

import java.util.List;

import com.adaptive.common.paging.RetrieverDataPage;
import com.adaptive.module.configuration.appSetting.model.AppSetting;
import com.adaptive.module.configuration.appSetting.vo.AppSettingVO;


@SuppressWarnings({ "unused", "unused" })
public interface AppSettingService extends RetrieverDataPage<AppSettingVO>{
	
	public 	AppSetting findSettingByCode(String settingCode);
	public List<AppSettingVO> getAppSettingListByCode(String typeCode);
}