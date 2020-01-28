package com.adaptive.module.master.course.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.bean.CommonBean;
import com.adaptive.module.master.course.service.CourseService;
import com.adaptive.module.master.course.vo.CourseVO;

@ManagedBean(name = "courseInputBean")
@ViewScoped
public class CourseInputBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 7557463890002982830L;
	
	@ManagedProperty(value = "#{courseService}")
	private CourseService courseService;
	
	private String MODE_TYPE;
	
	private CourseVO courseVO;
	
	private String modeTitle;
	
	@PostConstruct
    public void postConstruct(){
            super.init();
            //modeAdd();
    }
	
	public void modeAdd(){
		MODE_TYPE = "ADD";
		courseVO = new CourseVO();
		System.out.println("CourseVO=="+ courseVO.getCourseCode());
		modeTitle = facesUtils.retrieveMessage("formCourseAddTitle");
	}
	
	public void modeEdit(CourseVO courseVO){
		MODE_TYPE = "EDIT";
		this.courseVO = courseVO;
		modeTitle = facesUtils.retrieveMessage("formCourseEditTitle");
	}
	
	public void save(){
		try{
			if(validateCourse()){
				if(MODE_TYPE.equalsIgnoreCase("ADD")){
					courseService.save(courseVO, "Admin");
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
							facesUtils.retrieveMessage("common_msg_saved"), null);
					MODE_TYPE = "SEARCH";
					modeAdd();
				} else if(MODE_TYPE.equalsIgnoreCase("EDIT")){
					courseService.update(courseVO, "Admin");
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
								facesUtils.retrieveMessage("common_msg_updated"), null);
					MODE_TYPE = "SEARCH";
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("errorSavedFailed"), null);
		}
	}
	
	private boolean validateCourse(){
		boolean validate = true;
		if(courseVO.getCourseCode().trim().isEmpty()){
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formCourse:courseCodeAdd",
					facesUtils.retrieveMessage("formCourseCode")+ " "
						+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			validate = false;
		}
		
		if(courseVO.getCourseName().trim().isEmpty()){
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formCourse:courseName",
					facesUtils.retrieveMessage("formCourseName")+ " "
						+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			validate = false;
		}
		
		if(MODE_TYPE.equalsIgnoreCase("ADD")){
			Integer countCode = courseService.countCourseByCourseCode(courseVO.getCourseCode());
			System.out.println("countCode=="+countCode);
			if(countCode != 0){
				facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formCourse:courseCodeAdd",
						facesUtils.retrieveMessage("formCourseCode")+ " "
							+ facesUtils.retrieveMessage("errorDuplicate"),
						null);
				validate = false;
				MODE_TYPE = "ADD";
			}
		}
		return validate;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public CourseVO getCourseVO() {
		return courseVO;
	}

	public void setCourseVO(CourseVO courseVO) {
		this.courseVO = courseVO;
	}

	public String getModeTitle() {
		return modeTitle;
	}

	public void setModeTitle(String modeTitle) {
		this.modeTitle = modeTitle;
	}
}