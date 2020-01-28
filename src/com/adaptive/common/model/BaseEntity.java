package com.adaptive.common.model;

import java.sql.Timestamp;

public class BaseEntity {
	
	protected Timestamp createOn;
	protected String createBy;
	
	protected Timestamp createdDT;
	protected String createdBy;
	
	protected Timestamp updateOn;
	protected Timestamp updateDT;
	protected String updateBy;
	
	protected Timestamp changedDT;
	protected String changedBy;
	
	protected String activeFlag;
	
	
	public Timestamp getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Timestamp createOn) {
		this.createOn = createOn;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Timestamp updateOn) {
		this.updateOn = updateOn;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Timestamp getChangedDT() {
		return changedDT;
	}
	public void setChangedDT(Timestamp changedDT) {
		this.changedDT = changedDT;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public Timestamp getCreatedDT() {
		return createdDT;
	}
	public void setCreatedDT(Timestamp createdDT) {
		this.createdDT = createdDT;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getUpdateDT() {
		return updateDT;
	}
	public void setUpdateDT(Timestamp updateDT) {
		this.updateDT = updateDT;
	}	
	
	
	
}
