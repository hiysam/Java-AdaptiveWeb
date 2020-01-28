package com.adaptive.module.transaction.teacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.adaptive.common.bean.CommonBean;
import com.adaptive.module.master.course.service.CourseService;
import com.adaptive.module.master.course.vo.CourseVO;
import com.adaptive.module.transaction.teacher.service.TeacherService;
import com.adaptive.module.transaction.teacher.vo.TeacherVO;
import com.adaptive.module.transaction.teacherClass.vo.TeacherClassVO;

@ManagedBean(name = "teacherInput")
@ViewScoped
public class TeacherInputBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 5742842336431597721L;
	
	@ManagedProperty(value = "#{teacherService}")
	private TeacherService teacherService;
	
	@ManagedProperty(value = "#{courseService}")
	private CourseService courseService;
	
	private List<TeacherClassVO> tcrClassVOList;
	private List<SelectItem> courseList;
	
	private String MODE_TYPE;

	private TeacherVO teacherVO;

	private String modeTitle;
	
	private TeacherInputBean teacherInput;
	
	@PostConstruct
	public void postCosntruct(){
		super.init();
		
		tcrClassVOList = new ArrayList<TeacherClassVO>();
		courseList = new ArrayList<SelectItem>();
		List<CourseVO> courseVOList = courseService.selectCourseList();
		for(CourseVO CourseVO : courseVOList){
			courseList.add(new SelectItem(CourseVO.getCourseId(), CourseVO.getCourseCode()
					+ " - " + CourseVO.getCourseName()));
		}
	}

	public void deleteTeacherClass(TeacherClassVO teacherClassVO) {
		tcrClassVOList.remove(teacherClassVO);
	}
	
	public void modeAdd() {
		MODE_TYPE = "ADD";
		teacherVO = new TeacherVO();
		tcrClassVOList = new ArrayList<TeacherClassVO>();
		modeTitle = facesUtils.retrieveMessage("formTeacherAddTitle");
		
	}
	
	public void modeAddTeacher() {
		if (tcrClassVOList.size() == 0 && validateTeacher()) {
			TeacherClassVO tcrClassVO = new TeacherClassVO();
			tcrClassVOList.add(tcrClassVO);
		} else if (validateTeacher() && validateTeacherClass()) {
			TeacherClassVO tcrClassVO = new TeacherClassVO();
			tcrClassVOList.add(tcrClassVO);
		}
	}

	public void modeSave(){
		teacherInput.save();
		MODE_TYPE = teacherInput.getMODE_TYPE();
	}
	
	public void save() {
		try {
			if (validateTeacher() && isHaveTeacherClass() && validateTeacherClass()) {
				if (MODE_TYPE.equalsIgnoreCase("ADD")) {

					teacherVO.setTcrClassVOList(tcrClassVOList);
					teacherService.save(teacherVO, "Admin");
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
							facesUtils.retrieveMessage("common_msg_saved"), null);
					MODE_TYPE = "SEARCH";
					// modeAdd();
				} else if (MODE_TYPE.equalsIgnoreCase("EDIT")) {
					teacherService.update(teacherVO, "Admin");
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
							facesUtils.retrieveMessage("common_msg_updated"), null);
					MODE_TYPE = "SEARCH";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("errorSavedFailed"),
					null);
		}
	}
	
	public boolean isHaveTeacherClass() {
		boolean validate = true;
		if (tcrClassVOList.size() == 0) {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:validateTeacherClass",
					facesUtils.retrieveMessage("formTeacherClassTitle") + " "
							+ facesUtils.retrieveMessage("errorDetailAtLeastOne"),
					null);
			validate = false;
		}
		return validate;
	}
	
	public boolean validateTeacherClass() {
		boolean validate = true;
		if (tcrClassVOList.size() > 1) {
			for (int z = 0; z < tcrClassVOList.size() - 1; z++) {
				if (tcrClassVOList.get(z).getCourseId() == tcrClassVOList.get(tcrClassVOList.size() - 1)
						.getCourseId()) {

					facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:validateTeacherClass",
							facesUtils.retrieveMessage("formCourseCode") + " "
									+ facesUtils.retrieveMessage("errorDuplicate"),
							null);
					validate = false;
					break;
				}
			}
		}
		
		if (tcrClassVOList.size() > 0) {
			TeacherClassVO teacherClassVO = new TeacherClassVO();
			
			for (int z = 0; z < tcrClassVOList.size(); z++) {

				teacherClassVO = (TeacherClassVO) tcrClassVOList.get(z);
				if (teacherClassVO.getCourseId() == 0 || teacherClassVO.getCourseId() == null) {
					facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:validateTeacherClass",
							facesUtils.retrieveMessage("formMatpel") + " dibaris " + (z + 1) + " "
									+ facesUtils.retrieveMessage("errorMustBeChoosen"),
							null);
					validate = false;
				}
				if (teacherClassVO.getDayOfWeek() == 0 || teacherClassVO.getDayOfWeek() == null) {
					facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN,
							"formTeacher:validateTeacherClass", facesUtils.retrieveMessage("formTeacherDayOfWeek")
									+ " dibaris " + (z + 1) + " " + facesUtils.retrieveMessage("errorMustBeFilled"),
							null);
					validate = false;
				}
				if (teacherClassVO.getTimeStart().trim().isEmpty()) {
					facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN,
							"formTeacher:validateTeacherClass", facesUtils.retrieveMessage("formTeacherTimeStart")
									+ " dibaris " + (z + 1) + " " + facesUtils.retrieveMessage("errorMustBeFilled"),
							null);
					validate = false;
				}
				if (teacherClassVO.getTimeEnd().trim().isEmpty()) {
					facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN,
							"formTeacher:validateTeacherClass", facesUtils.retrieveMessage("formTeacherTimeEnd")
									+ " dibaris " + (z + 1) + " " + facesUtils.retrieveMessage("errorMustBeFilled"),
							null);
					validate = false;
				}
			}
		}
		return validate;
	}
	
	private boolean validateTeacher() {
		boolean validate = true;

		if (teacherVO.getTeacherNumber().trim().isEmpty()) {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:teacherNoAdd",
					facesUtils.retrieveMessage("formTeacherNo") + " " + facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			validate = false;
		}

		if (teacherVO.getTeacherName().trim().isEmpty()) {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:teacherName",
					facesUtils.retrieveMessage("formTeacherName") + " "
							+ facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			validate = false;
		}
		
		if (teacherVO.getTeacherGender().trim().isEmpty()) {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:gender",
					facesUtils.retrieveMessage("formGenderG") + " " + facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			validate = false;
		}
		
		if (teacherVO.getTeacherBirthPlace().trim().isEmpty()) {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:birthPlace",
					facesUtils.retrieveMessage("formTeacherBirthPlace") + " "
							+ facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			validate = false;
		}
		
		if (teacherVO.getTeacherBirthDate() == null) {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:birthDate",
					facesUtils.retrieveMessage("formTeacherBirthDate") + " "
							+ facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			validate = false;
		}
		
		if (teacherVO.getTeacherAddress().trim().isEmpty()) {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:address",
					facesUtils.retrieveMessage("formTeacherAddress") + " "
							+ facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			validate = false;
		}

		if (MODE_TYPE.equalsIgnoreCase("ADD")) {
			Integer countNo = teacherService.countByTeacherNumber(teacherVO.getTeacherNumber());
			if (countNo != 0) {
				facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formTeacher:teacherNoAdd",
						facesUtils.retrieveMessage("formTeacherNo") + " "
								+ facesUtils.retrieveMessage("errorDuplicate"),
						null);
				validate = false;
				MODE_TYPE = "ADD";
			}
		}
		return validate;
	}

	public void modeEdit(TeacherVO teacherVO) {
		MODE_TYPE = "EDIT";
		this.teacherVO = teacherVO;
		tcrClassVOList = teacherVO.getTcrClassVOList();
		modeTitle = facesUtils.retrieveMessage("formTeacherEditTitle");
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public List<TeacherClassVO> getTcrClassVOList() {
		return tcrClassVOList;
	}

	public void setTcrClassVOList(List<TeacherClassVO> tcrClassVOList) {
		this.tcrClassVOList = tcrClassVOList;
	}

	public List<SelectItem> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<SelectItem> courseList) {
		this.courseList = courseList;
	}

	public TeacherVO getTeacherVO() {
		return teacherVO;
	}

	public void setTeacherVO(TeacherVO teacherVO) {
		this.teacherVO = teacherVO;
	}

	public String getModeTitle() {
		return modeTitle;
	}

	public void setModeTitle(String modeTitle) {
		this.modeTitle = modeTitle;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public TeacherInputBean getTeacherInput() {
		return teacherInput;
	}

	public void setTeacherInput(TeacherInputBean teacherInput) {
		this.teacherInput = teacherInput;
	}
	
	
	
}