package com.adaptive.module.transaction.teacherClass.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import com.adaptive.common.dao.GenericDAOHibernate;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

@ManagedBean(name = "teacherClassDAO")
@ViewScoped

public class TeacherClassDAOImpl extends GenericDAOHibernate<TeacherClass, Serializable> implements TeacherClassDAO, Serializable{

	@Override
	public List<TeacherClassVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(TeacherClass.class);
		criteria = decorateSearch(criteria, searchCriteria);
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<TeacherClassVO> tcrClassVOs = new ArrayList<TeacherClassVO>();
		
		for (int i = 0; i < criteria.list().size(); i++){
			
			TeacherClass teacherClass = (TeacherClass) criteria.list().get(i);
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
		return tcrClassVOs;
	}

	private Criteria decorateSearch(Criteria criteria, List<? extends SearchObject> searchCriteria) {
		// TODO Auto-generated method stub
		if(searchCriteria != null){
			for(SearchObject searchVal  : searchCriteria){
				if(searchVal.getSearchColumn().equals("teacherId")){
					Long teacherId = (Long) searchVal.getSearchValue();
					criteria.createAlias("tcr", "teacher");
					criteria.add(Restrictions.eq("tcr.teacherId", teacherId));
				}
			}
		}
			criteria.add(Restrictions.eq("activeFlag", "Y"));
		return criteria;
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(TeacherClass.class);
		criteria = decorateSearch(criteria, searchCriteria);
		Integer result = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		if(result == null){
			result = 0;
		}
		return result.longValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherClass> findTcrClassByTeacherId(Long teacherId) {
		// TODO Auto-generated method stub
		try{
			Criteria criteria = getSession().createCriteria(TeacherClass.class);
			criteria.createAlias("tcr", "teacher");
			criteria.add(Restrictions.eq("tcr.teacherId", teacherId));
			
			return (List<TeacherClass>) criteria.list();
		}catch(HibernateException he){
			
			he.printStackTrace();
			throw new RuntimeException(he);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherClass> findTcrClassByCourseId(Long courseId) {
		// TODO Auto-generated method stub
		try{
			Criteria criteria = getSession().createCriteria(TeacherClass.class);
			criteria.createAlias("crs", "course");
			criteria.add(Restrictions.eq("crs.courseId", courseId));
			
			return (List<TeacherClass>) criteria.list();
		}catch(HibernateException he){
			
			he.printStackTrace();
			throw new RuntimeException(he);
		}
	}

	@Override
	public List<TeacherClassVO> getTeacherClassVOs() {
		// TODO Auto-generated method stub
		try{
			Criteria criteria = getSession().createCriteria(TeacherClass.class);
			criteria.addOrder(Order.asc("id"));
			
			List<TeacherClassVO> tcrClassVOs = new ArrayList<TeacherClassVO>();
			
			for (int i = 0; i < criteria.list().size(); i++){
				TeacherClass teacherClass = (TeacherClass) criteria.list().get(i);
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
			return tcrClassVOs;
		}catch (HibernateException he) {
			// TODO: handle exception
			he.printStackTrace();
			throw new RuntimeException(he);
		}
	}

}