package com.adaptive.module.configuration.appSetting.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import com.adaptive.common.dao.GenericDAOHibernate;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.configuration.appSetting.model.AppSetting;
import com.adaptive.module.configuration.appSetting.vo.AppSettingVO;

@ManagedBean(name = "appSettingDAO")
@ViewScoped	
public class AppSettingDAOImpl extends GenericDAOHibernate<AppSetting,Long> implements AppSettingDAO, Serializable{

	@Override
	public List<AppSettingVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppSettingVO> getAppSettingListByCode(String typeCode) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AppSetting.class);
		criteria.add(Restrictions.eq("appSettingType.typeCode", typeCode));
		
		List<AppSettingVO> appSettingVOList = new ArrayList<AppSettingVO>();
		
		for(int i = 0; i < criteria.list().size(); i++){
			AppSetting appSetting = (AppSetting) criteria.list().get(i);
			AppSettingVO appSettingVO = new AppSettingVO();
			
			appSettingVO.setSettingCode(appSetting.getSettingCode());
			appSettingVO.setSettingName(appSetting.getSettingName());
			
			appSettingVOList.add(appSettingVO);
		}
		return appSettingVOList;
	}

	@Override
	public AppSetting findSettingByCode(String settingCode) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria (AppSetting.class);
		
		criteria.add(Restrictions.eq("settingCode", settingCode).ignoreCase());
		
		AppSetting appSetting = (AppSetting) criteria.uniqueResult();
		
		return appSetting;
	}

}