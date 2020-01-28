package com.adaptive.module.master.course.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.primefaces.model.SortOrder;

import com.adaptive.common.dao.GenericDAOHibernate;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.master.course.model.Course;
import com.adaptive.module.master.course.vo.CourseVO;

@ManagedBean(name ="courseDAO")
@ViewScoped
public class CourseDAOImpl extends GenericDAOHibernate<Course, Long> implements CourseDAO, Serializable{

	private static final long serialVersionUID = -262720894269444816L;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<CourseVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT CO.COURSE_ID, CO.COURSE_CODE, CO.COURSE_NAME, CO.COURSE_DESC ");
		sb.append(" FROM tbl_course CO ");
		sb.append(" WHERE CO.ACTIVE_FLAG = 'Y' ");
		
		sb = decorateCriteria(sb, searchCriteria);
		
		sb.append(" ORDER BY COURSE_ID asc ");
		
		SQLQuery result = getSession().createSQLQuery(sb.toString());
		
		result.setResultTransformer(new ResultTransformer() {
			
			@Override
			public Object transformTuple(Object[] result, String[] aliases) {
				// TODO Auto-generated method stub
				CourseVO courseVO = new CourseVO();
				courseVO.setCourseId(result[0] != null ? Long.parseLong(result[0] + "") : null);
				courseVO.setCourseCode(result[1] != null ? (String) result[1] : "");
				courseVO.setCourseName(result[2] != null ? (String) result[2] : "");
				courseVO.setCourseDesc(result[3] != null ? (String) result[3] : "");
				return courseVO;
			}
			
			@Override
			public List transformList(List list) {
				// TODO Auto-generated method stub
				return list;
			}
		});
		
		result.setFirstResult(first);
		result.setMaxResults(pageSize);
		
		return result.list();
	}

	private StringBuilder decorateCriteria(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		// TODO Auto-generated method stub
		if(searchCriteria != null){
			for(SearchObject searchVal : searchCriteria){
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValue() != null ? searchVal.getSearchValueAsString() : "";
				
				if(!StringUtils.isBlank(val)){
					if(StringUtils.equals("searchAll", col)){
						sb.append(" and co.course_code like '%"+ val + "%' ");
						sb.append(" or co.course_name like '%"+ val + "%' ");
						sb.append(" or co.course_desc like '%"+ val + "%' ");
					}
				}
			}
		}
		return sb;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT COUNT(*) ");
		sb.append(" FROM tbl_course co ");
		sb.append(" WHERE co.active_flag = 'Y' ");
		
		sb = decorateCriteria(sb, searchCriteria);
		
		SQLQuery result = getSession().createSQLQuery(sb.toString());
		
		Number results = (Number) result.uniqueResult();
		if(results == null){
			results = 0;
		}
		
		return results.longValue();
	}

	@Override
	public List<CourseVO> selectCourseList() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Course.class);
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		
		List<CourseVO> courseVOs = new ArrayList<CourseVO>();
		
		for(int i = 0; i < criteria.list().size(); i++){
			Course course = (Course) criteria.list().get(i);
			CourseVO courseVO = new CourseVO();
			
			courseVO.setCourseId(course.getCourseId());
			courseVO.setCourseCode(course.getCourseCode());
			courseVO.setCourseName(course.getCourseName());
			courseVO.setCourseDesc(course.getCourseDesc());
			courseVO.setActiveFlag(course.getActiveFlag());
			courseVOs.add(courseVO);
		}
		return courseVOs;
	}
	
}