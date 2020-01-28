package com.adaptive.module.transaction.teacher.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import com.adaptive.common.dao.GenericDAOHibernate;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.transaction.teacher.model.Teacher;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

@ManagedBean (name = "teacherDAO")
@ViewScoped
public class TeacherDAOImpl extends GenericDAOHibernate<Teacher, Serializable> implements TeacherDAO, Serializable {

	private static final long serialVersionUID = -7688665359168204956L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<TeacherVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Teacher.class);
		
		criteria = decorateSearch(criteria, searchCriteria);
		
		if(sortField != null && sortOrder != null){
			
			if(sortField.equals("data[1]")){
				criteria.addOrder(sortOrder.name().equalsIgnoreCase("ASCENDING")?
				Order.asc("teacherNo"): Order.desc("teacherNo"));
				
			}
			if(sortField.equals("data[2]")){
				criteria.addOrder(sortOrder.name().equalsIgnoreCase("ASCENDING")?
				Order.asc("teacherName"): Order.desc("teacherName"));
				
			}
			if(sortField.equals("data[3]")){
				criteria.addOrder(sortOrder.name().equalsIgnoreCase("ASCENDING")?
				Order.asc("gender.settingCode"): Order.desc("gender.settingCode"));
				
			}
			if(sortField.equals("data[4]")){
				criteria.addOrder(sortOrder.name().equalsIgnoreCase("ASCENDING")?
				Order.asc("teacherAddress"): Order.desc("teacherAddress"));
				
			}
		}else{
			criteria.addOrder(Order.asc("id"));
		}
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<TeacherVO> teacherListResult = new ArrayList<TeacherVO>();
		
		for (int i = 0; i < criteria.list().size(); i++){
			
			Teacher teacher = (Teacher) criteria.list().get(i);
			TeacherVO teacherVO = new TeacherVO();
			
			teacherVO.setTeacherId(teacher.getTeacherId());
			teacherVO.setTeacherNumber(teacher.getTeacherNumber());
			teacherVO.setTeacherName(teacher.getTeacherName());
			teacherVO.setTeacherGender(teacher.getGender().getSettingCode());
			teacherVO.setGenderName(teacher.getGender().getSettingName());
			teacherVO.setTeacherBirthPlace(teacher.getTeacherBirthPlace());
			teacherVO.setTeacherBirthDate(teacher.getTeacherBirthDate());
			teacherVO.setTeacherAddress(teacher.getTeacherAddress());
			
			List<TeacherClassVO> tcrClassVOs = new ArrayList<TeacherClassVO>();
			List<TeacherClass> tcrClass = teacher.getTeacherClass();
			
			if(tcrClass.size() > 0){
				
				for(TeacherClass teacherClass : tcrClass){
					
					TeacherClassVO tcrClassVO = new TeacherClassVO();
					
					tcrClassVO.setId(teacherClass.getId());
					tcrClassVO.setTeacherId(teacherClass.getTcr().getTeacherId());
					tcrClassVO.setTeacherNo(teacherClass.getTcr().getTeacherNumber());
					tcrClassVO.setTeacherName(teacherClass.getTcr().getTeacherName());
					
					tcrClassVO.setCourseId(teacherClass.getCrs().getCourseId());
					tcrClassVO.setCourseCode(teacherClass.getCrs().getCourseCode());
					tcrClassVO.setCourseName(teacherClass.getCrs().getCourseName());
					
					tcrClassVO.setDayOfWeek(teacherClass.getDayOfWeek());
					tcrClassVO.setTimeStart(teacherClass.getTimeStart());
					tcrClassVO.setTimeEnd(teacherClass.getTimeEnd());
					
					tcrClassVOs.add(tcrClassVO);
				}
			}
			teacherVO.setTcrClassVOList(tcrClassVOs);
			teacherListResult.add(teacherVO);
		}
		
		return teacherListResult;
	}
	
	private Criteria decorateSearch(Criteria criteria, List<? extends SearchObject> searchCriteria) {
		// TODO Auto-generated method stub
		if(searchCriteria != null){
			
			for(SearchObject searchVal : searchCriteria){
				if(searchVal.getSearchColumn().compareTo("searchAll")== 0){
					String strSplit = searchVal.getSearchValueAsString().trim();
					criteria.add(Restrictions.or(
							Restrictions.ilike("teacherNumber", strSplit, MatchMode.ANYWHERE),
							Restrictions.ilike("teacherName", strSplit, MatchMode.ANYWHERE)));
				}
				if(searchVal.getSearchColumn().equals("teacherNo")){
					String strSplit = searchVal.getSearchValueAsString().trim();
					criteria.add(Restrictions.ilike("teacherNo", strSplit, MatchMode.ANYWHERE));
				}
				if(searchVal.getSearchColumn().equals("teacherName")){
					String strSplit = searchVal.getSearchValueAsString().trim();
					criteria.add(Restrictions.ilike("teacherName", strSplit, MatchMode.ANYWHERE));
				}
				if(searchVal.getSearchColumn().equals("genderCode")){
					String strSplit = searchVal.getSearchValueAsString().trim();
					criteria.add(Restrictions.ilike("gender.settingCode", strSplit, MatchMode.ANYWHERE));
				}
				if(searchVal.getSearchColumn().equals("address")){
					String strSplit = searchVal.getSearchValueAsString().trim();
					criteria.add(Restrictions.ilike("address", strSplit, MatchMode.ANYWHERE));
				}
			}
		}
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		return criteria;
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Teacher.class);
		criteria = decorateSearch(criteria, searchCriteria);
		Integer result = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		if(result == null){
			result = 0;
			
		}
		return result.longValue();
	}

	@Override
	public List<TeacherVO> getTeacherVOs() {
		// TODO Auto-generated method stub
		try{
			Criteria criteria = getSession().createCriteria(Teacher.class);
			criteria.addOrder(Order.asc("teacherId"));
			
			List<TeacherVO> teacherListResult = new ArrayList<TeacherVO>();
			
			for (int i = 0; i < criteria.list().size(); i++){
				Teacher teacher = (Teacher) criteria.list().get(i);
				TeacherVO teacherVO = new TeacherVO();
				
				teacherVO.setTeacherId(teacher.getTeacherId());
				teacherVO.setTeacherNumber(teacher.getTeacherNumber());
				teacherVO.setTeacherName(teacher.getTeacherName());
				teacherVO.setTeacherGender(teacher.getGender().getSettingCode());
				teacherVO.setGenderName(teacher.getGender().getSettingName());
				teacherVO.setTeacherBirthPlace(teacher.getTeacherBirthPlace());
				teacherVO.setTeacherBirthDate(teacher.getTeacherBirthDate());
				teacherVO.setTeacherAddress(teacher.getTeacherAddress());
				
				List<TeacherClassVO> tcrClassVOs = new ArrayList<TeacherClassVO>();
				List<TeacherClass> tcrClass = teacher.getTeacherClass();
				if(tcrClass.size() > 0){
					for(TeacherClass teacherClass : tcrClass){
						
						TeacherClassVO tcrClassVO = new TeacherClassVO();
						
						tcrClassVO.setId(teacherClass.getId());
						tcrClassVO.setTeacherId(teacherClass.getTcr().getTeacherId());
						tcrClassVO.setTeacherNo(teacherClass.getTcr().getTeacherNumber());
						tcrClassVO.setTeacherName(teacherClass.getTcr().getTeacherName());
						
						tcrClassVO.setCourseId(teacherClass.getCrs().getCourseId());
						tcrClassVO.setCourseCode(teacherClass.getCrs().getCourseCode());
						tcrClassVO.setCourseName(teacherClass.getCrs().getCourseName());
						
						tcrClassVO.setDayOfWeek(teacherClass.getDayOfWeek());
						tcrClassVO.setTimeStart(teacherClass.getTimeStart());
						tcrClassVO.setTimeEnd(teacherClass.getTimeEnd());
						
						tcrClassVOs.add(tcrClassVO);
					}

				}
				teacherVO.setTcrClassVOList(tcrClassVOs);
				teacherListResult.add(teacherVO);
			}
			return teacherListResult;
		} catch (HibernateException e) {
			e.printStackTrace();
			
			return new ArrayList<TeacherVO>();
		}
	}
}