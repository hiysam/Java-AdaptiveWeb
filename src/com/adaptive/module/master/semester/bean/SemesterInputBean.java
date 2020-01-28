package com.adaptive.module.master.semester.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.adaptive.common.bean.CommonBean;
import com.adaptive.module.master.semester.service.SemesterService;
import com.adaptive.module.master.semester.vo.SemesterVO;

@ManagedBean(name = "semesterInputBean")
@ViewScoped
public class SemesterInputBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 7557463890002982830L;
	
	@ManagedProperty(value = "#{semesterService}")
	private SemesterService semesterService;
	
	private String MODE_TYPE;
	
	private SemesterVO semesterVO;
	
	private String modeTitle;
	
	@PostConstruct
    public void postConstruct(){
            super.init();
            //modeAdd();
    }
	
	public void modeAdd(){
		MODE_TYPE = "ADD";
		System.out.println("coba : "+MODE_TYPE);
		semesterVO = new SemesterVO();
		System.out.println("SemesterVO=="+ semesterVO.getSemesterType());
		modeTitle = facesUtils.retrieveMessage("formSemesterAddTitle");
	}
	
	public void modeEdit(SemesterVO semesterVO){
		MODE_TYPE = "EDIT";
		this.semesterVO = semesterVO;
		modeTitle = facesUtils.retrieveMessage("formSemesterEditTitle");
	}
	
	public void save(){
		try{
			if(validateSemester()){
				if(MODE_TYPE.equalsIgnoreCase("ADD")){
					semesterService.save(semesterVO, "Admin");
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
							facesUtils.retrieveMessage("common_msg_saved"), null);
					MODE_TYPE = "SEARCH";
					modeAdd();
				} else if(MODE_TYPE.equalsIgnoreCase("EDIT")){
					semesterService.update(semesterVO, "Admin");
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
	
	private boolean validateSemester(){
		boolean validate = true;
		if(semesterVO.getSemesterType().trim().isEmpty()){
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formSemester:semesterTypeAdd",
					facesUtils.retrieveMessage("formSemesterType")+ " "
						+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			validate = false;
		}
		
		if(semesterVO.getSemesterType().trim().isEmpty()){
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formSemester:periode",
					facesUtils.retrieveMessage("formSemesterYear")+ " "
						+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			validate = false;
		}
		
		if(MODE_TYPE.equalsIgnoreCase("ADD")){
			Integer countType = semesterService.countSemesterBySemesterType(semesterVO.getSemesterType());
			System.out.println("countCode=="+countType);
			if(countType != 0){
				facesUtils.addFacesMsg(FacesMessage.SEVERITY_WARN, "formSemester:semesterTypeAdd",
						facesUtils.retrieveMessage("formSemesterType")+ " "
							+ facesUtils.retrieveMessage("errorDuplicate"),
						null);
				validate = false;
				MODE_TYPE = "ADD";
			}
		}
		return validate;
	}
	
	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public SemesterVO getSemesterVO() {
		return semesterVO;
	}

	public void setSemesterVO(SemesterVO semesterVO) {
		this.semesterVO = semesterVO;
	}

	public String getModeTitle() {
		return modeTitle;
	}

	public void setModeTitle(String modeTitle) {
		this.modeTitle = modeTitle;
	}

	public SemesterService getSemesterService() {
		return semesterService;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}
}