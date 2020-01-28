package com.adaptive.module.master.semester.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.primefaces.model.SortOrder;

import com.adaptive.common.dao.GenericDAOHibernate;
import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.master.semester.model.Semester;
import com.adaptive.module.master.semester.vo.SemesterVO;

@ManagedBean(name ="semesterDAO")
@ViewScoped
public class SemesterDAOImpl extends GenericDAOHibernate<Semester, Long> implements SemesterDAO, Serializable{

	private static final long serialVersionUID = -262720894269444816L;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<SemesterVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub (Tidak menggunakan Criteria)
		/*StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * FROM tr_semester");
		
		sb = decorateCriteria(sb, searchCriteria);
	
		SQLQuery result = getSession().createSQLQuery(sb.toString());
		
		result.setResultTransformer(new ResultTransformer() {
			
			@Override
			public Object transformTuple(Object[] result, String[] aliases) {
				// TODO Auto-generated method stub
				SemesterVO semesterVO = new SemesterVO();
				semesterVO.setSemesterId(result[0] != null ? Long.parseLong(result[0] + "") : null);
				semesterVO.setSemesterType(result[1] != null ? (String) result[1] : "");
				semesterVO.setPeriode(result[2] != null ? (String) result[2] : "");
				semesterVO.setSemesterDesc(result[3] != null ? (String) result[3] : "");
				return semesterVO;
			}
			
			@Override
			public List transformList(List list) {
				// TODO Auto-generated method stub
				return list;
			}
		});
		
		result.setFirstResult(first);
		result.setMaxResults(pageSize);
		
		return result.list();*/
		
		Criteria criteria = getSession().createCriteria(Semester.class);
		criteria = decorateCriteria(criteria, searchCriteria);
		
		if (sortField !=  null && sortOrder != null) 
		{
			if (sortField.equals("data[1]"))
			{
				criteria.addOrder(sortOrder.name().equalsIgnoreCase("ASCENDING")
						? Order.asc("semesterType"): Order.desc("semesterType"));
			}
			if (sortField.equals("data[2]"))
			{
				criteria.addOrder(sortOrder.name().equalsIgnoreCase("ASCENDING")
						? Order.asc("semesterYear"): Order.desc("semesterYear"));
			}
			if (sortField.equals("data[3]"))
			{
				criteria.addOrder(sortOrder.name().equalsIgnoreCase("ASCENDING")
						? Order.asc("semesterDesc"): Order.desc("semesterDesc"));
			}
		}
		else
		{
			criteria.addOrder(Order.asc("semesterId"));
		}
		
		criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        List<SemesterVO> semesterVOs = new ArrayList<SemesterVO>();
        
        for (int i = 0; i < criteria.list().size(); i++) {
                Semester semester =(Semester) criteria.list().get(i);
                SemesterVO semesterVO = new SemesterVO();
                semesterVO.setSemesterId(semester.getSemesterId());
                semesterVO.setSemesterType(semester.getSemesterType());
                semesterVO.setPeriode(semester.getPeriode());
                semesterVO.setSemesterDesc(semester.getSemesterDesc());
                semesterVOs.add(semesterVO);
        }
        return semesterVOs;
	}

	/*private StringBuilder decorateCriteria(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		// TODO Auto-generated method stub (Tidak menggunakan Criteria)
		if(searchCriteria != null){
			for(SearchObject searchVal : searchCriteria){
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValue() != null ? searchVal.getSearchValueAsString() : "";
				
				if(!StringUtils.isBlank(val)){
					if(StringUtils.equals("searchAll", col)){
						sb.append(" where upper(semester_type) like '%"+ val.toUpperCase() + "%' ");
						sb.append(" or upper(periode) like '%"+ val.toUpperCase() + "%' ");
						sb.append(" or upper(semester_desc) like '%"+ val.toUpperCase() + "%' ");
					}
				}
			}
		}
		return sb;
	}*/
	@SuppressWarnings("rawtypes")
    private Criteria decorateCriteria(Criteria criteria, List<? extends SearchObject> searchCriteria) 
		{
                if(searchCriteria != null){
                        for (SearchObject searchVal: searchCriteria) {
                                //searchAll => 1 inputtext = 2++ column pada data table
                                if(searchVal.getSearchColumn().compareTo("searchAll") == 0) {
                                        String strSplit =searchVal.getSearchValueAsString().trim();
                                        criteria.add(Restrictions.or
                                        		(
                                                        Restrictions.ilike("semesterType", strSplit, MatchMode.ANYWHERE),
                                                        Restrictions.ilike("semesterYear", strSplit, MatchMode.ANYWHERE)));
                                }
                                //findCourseCode => 1 inputtext = 1column courseCode pada data Table
                                if(searchVal.getSearchColumn().compareTo("findSemesterType") == 0)
                                {
                                        String strSplit =searchVal.getSearchValueAsString().trim();
                                        criteria.add(Restrictions.ilike("semesterType", strSplit, MatchMode.ANYWHERE));
                                }
                                //findCourseName => 1 inputtext = 1column courseName pada data Table
                                if(searchVal.getSearchColumn().compareTo("findSemesterYear") == 0)
                                {
                                        String strSplit = searchVal.getSearchValueAsString().trim();
                                        criteria.add(Restrictions.ilike("semesterYear", strSplit, MatchMode.ANYWHERE));
                                }
                        }
                }
                
                return criteria;
        }
	

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub (Tidak menggunakan Criteria)
		/*StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT COUNT(semester_id) ");
		sb.append(" FROM tr_semester ");
		
		sb = decorateCriteria(sb, searchCriteria);
		
		SQLQuery result = getSession().createSQLQuery(sb.toString());
		
		Number results = (Number) result.uniqueResult();
		if(results == null){
			results = 0;
		}
		
		return results.longValue();*/
		Criteria criteria = getSession().createCriteria(Semester.class);
        criteria = decorateCriteria(criteria, searchCriteria);
        Integer result = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if(result == null) {
                result = 0;
        }
        return result.longValue();
	}

	@Override
	public List<SemesterVO> selectSemesterList() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Semester.class);
		
		List<SemesterVO> semesterVOs = new ArrayList<SemesterVO>();
		
		for(int i = 0; i < criteria.list().size(); i++){
			Semester semester = (Semester) criteria.list().get(i);
			SemesterVO semesterVO = new SemesterVO();
			
			semesterVO.setSemesterId(semester.getSemesterId());
			semesterVO.setSemesterType(semester.getSemesterType());
			semesterVO.setPeriode(semester.getPeriode());
			semesterVO.setSemesterDesc(semester.getSemesterDesc());
			semesterVOs.add(semesterVO);
		}
		return semesterVOs;
	}
	
}