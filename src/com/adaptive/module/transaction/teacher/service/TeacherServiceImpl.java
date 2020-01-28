package com.adaptive.module.transaction.teacher.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.SortOrder;

import com.adaptive.common.vo.SearchObject;
import com.adaptive.module.configuration.appSetting.dao.AppSettingDAO;
import com.adaptive.module.configuration.appSetting.model.AppSetting;
import com.adaptive.module.master.course.dao.CourseDAO;
import com.adaptive.module.transaction.teacher.dao.TeacherDAO;
import com.adaptive.module.transaction.teacher.model.Teacher;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;
import com.adaptive.module.transaction.teacherClass.model.TeacherClass;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

@ManagedBean(name = "teacherService")
@ViewScoped
public class TeacherServiceImpl implements TeacherService, Serializable{

	private static final long serialVersionUID = 2416491076719321427L;
	
	@ManagedProperty(value = "#{teacherDAO}")
	private TeacherDAO teacherDAO;
	
	@ManagedProperty(value = "#{courseDAO}")
	private CourseDAO courseDAO;
	
	@ManagedProperty(value = "#{appSettingDAO}")
	private AppSettingDAO appSettingDAO;

	@Override
	public List<TeacherVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, SortOrder sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return teacherDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return teacherDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(TeacherVO teacherVO, String user) {
		// TODO Auto-generated method stub
		Teacher teacher = new Teacher();
		AppSetting gender = new AppSetting();
		teacher.setTeacherNumber(teacherVO.getTeacherNumber());
		teacher.setTeacherName(teacherVO.getTeacherName());
		gender = appSettingDAO.findSettingByCode(teacherVO.getTeacherGender());
		teacher.setGender(gender);
		teacher.setTeacherBirthPlace(teacherVO.getTeacherBirthPlace());
		teacher.setTeacherBirthDate(teacherVO.getTeacherBirthDate());
		teacher.setTeacherAddress(teacherVO.getTeacherAddress());
		
		teacher.setCreateBy(user);
		teacher.setCreateOn(new Timestamp(System.currentTimeMillis()));
		teacher.setActiveFlag(teacherVO.getActiveFlag());
		
		List<TeacherClass> tcrClass = new ArrayList<TeacherClass>();
		for (TeacherClassVO tcrClassVO : teacherVO.getTcrClassVOList()){
			
			TeacherClass teacherClass = new TeacherClass();
			teacherClass.setDayOfWeek(tcrClassVO.getDayOfWeek());
			teacherClass.setTimeStart(tcrClassVO.getTimeStart());
			teacherClass.setTimeEnd(tcrClassVO.getTimeEnd());
			
			teacherClass.setTcr(teacher);
			teacherClass.setCrs(courseDAO.findById(tcrClassVO.getCourseId()));
			
			teacherClass.setCreateBy(user);
			teacherClass.setCreateOn(new Timestamp(System.currentTimeMillis()));
			teacherClass.setActiveFlag(teacherVO.getActiveFlag());
			
			tcrClass.add(teacherClass);
			
		}
		teacher.setTeacherClass(tcrClass);
		teacherDAO.save(teacher);
		teacherDAO.flush();
	}

	@Override
	public void update(TeacherVO teacherVO, String user) {
		// TODO Auto-generated method stub
		Teacher teacher = teacherDAO.findById(teacherVO.getTeacherId());
		if(teacher != null){
			//UPDATE Teacher
			AppSetting gender = new AppSetting();
			teacher.setTeacherName(teacherVO.getTeacherName());
			gender = appSettingDAO.findSettingByCode(teacherVO.getTeacherGender());
			teacher.setGender(gender);
			teacher.setTeacherBirthPlace(teacherVO.getTeacherBirthPlace());
			teacher.setTeacherBirthDate(teacherVO.getTeacherBirthDate());
			teacher.setTeacherAddress(teacherVO.getTeacherAddress());
			
			teacher.setUpdateBy(user);
			teacher.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			teacher.setActiveFlag(teacherVO.getActiveFlag());
			
			//UPDATE Teacher Class
			List<TeacherClass> tcrClassDB = teacher.getTeacherClass();
			List<TeacherClassVO> tcrClassVOs = teacherVO.getTcrClassVOList();
			TeacherClass teacherClassDB = null;
			TeacherClassVO teacherClassVO = null;
			
			if(tcrClassDB.size() > 0){
				//Looking for me TeacherClass
				for(int x = 0; x < tcrClassVOs.size(); x++){
					teacherClassVO = tcrClassVOs.get(x);
					boolean exist = false;
					
					for(int y = 0; y < tcrClassDB.size(); y++){
						teacherClassDB = tcrClassDB.get(y);
						
						if(teacherClassDB.getCrs().getCourseId().equals(teacherClassVO.getCourseId())){
							exist = true;
							break;
						}
					}
					
					//update old data teacher class
					if(exist){
						teacherClassDB.setTcr(teacher);
						teacherClassDB.setDayOfWeek(teacherClassVO.getDayOfWeek());
						teacherClassDB.setTimeStart(teacherClassVO.getTimeStart());
						teacherClassDB.setTimeEnd(teacherClassVO.getTimeEnd());
						
						teacherClassDB.setUpdateBy(user);
						teacherClassDB.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						teacherClassDB.setActiveFlag(teacherVO.getActiveFlag());
					}else{
						//ad new teacher class
						TeacherClass teacherClassNew = new TeacherClass();
						teacherClassNew.setTcr(teacher);
						teacherClassNew.setCrs(courseDAO.findById(teacherClassVO.getCourseId()));
						
						teacherClassNew.setDayOfWeek(teacherClassVO.getDayOfWeek());
						teacherClassNew.setTimeStart(teacherClassVO.getTimeStart());
						teacherClassNew.setTimeEnd(teacherClassVO.getTimeEnd());
						
						teacherClassNew.setCreateBy(user);
						teacherClassNew.setCreateOn(new Timestamp(System.currentTimeMillis()));
						teacherClassNew.setActiveFlag(teacherVO.getActiveFlag());
						
						tcrClassDB.add(teacherClassNew);
					}
				}
				//looking for delete teacher class
				for(int y = 0; y <tcrClassDB.size(); y++){
					teacherClassDB = tcrClassDB.get(y);
					boolean exist = false;
					
					for(int x = 0; x < tcrClassVOs.size(); x++){
						teacherClassVO = tcrClassVOs.get(x);
						
						if(teacherClassDB.getCrs().getCourseId().equals(teacherClassVO.getCourseId())){
							exist = true;
							break;
						}
					}
					
					if(!exist){
						tcrClassDB.remove(teacherClassDB);
						y--;
					}
				}	
				
			}else{
				//add new teacher class
				for(int x = 0; x < tcrClassVOs.size(); x++){
					teacherClassVO = tcrClassVOs.get(x);
					TeacherClass teacherClassNew = new TeacherClass();
					teacherClassNew.setTcr(teacher);
					teacherClassNew.setCrs(courseDAO.findById(teacherClassVO.getCourseId()));
					
					teacherClassNew.setDayOfWeek(teacherClassVO.getDayOfWeek());
					teacherClassNew.setTimeStart(teacherClassVO.getTimeStart());
					teacherClassNew.setTimeEnd(teacherClassVO.getTimeEnd());
					
					teacherClassNew.setCreateBy(user);
					teacherClassNew.setCreateOn(new Timestamp(System.currentTimeMillis()));
					teacherClassNew.setActiveFlag(teacherVO.getActiveFlag());
					
					teacher.getTeacherClass().add(teacherClassNew);
				}
			}
			
			teacherDAO.update(teacher);
			teacherDAO.flush();
		}
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Teacher tacher  = teacherDAO.findById(id);
		teacherDAO.delete(tacher);
		teacherDAO.flush();
	}

	@Override
	public TeacherVO findSemesterById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeacherVO> selectTeacherList() {
		// TODO Auto-generated method stub
		return teacherDAO.getTeacherVOs();
	}

	@Override
	public Integer countByTeacherNumber(String teacherNumber) {
		// TODO Auto-generated method stub
		return teacherDAO.countUniqueKey(teacherNumber, "teacherNumber", new Teacher());
	}

	public TeacherDAO getTeacherDAO() {
		return teacherDAO;
	}

	public void setTeacherDAO(TeacherDAO teacherDAO) {
		this.teacherDAO = teacherDAO;
	}

	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	public AppSettingDAO getAppSettingDAO() {
		return appSettingDAO;
	}

	public void setAppSettingDAO(AppSettingDAO appSettingDAO) {
		this.appSettingDAO = appSettingDAO;
	}
	
	
}