package service.dto;

import java.time.LocalDateTime;

public class PtContractDTO {
	private int ptContractId = 0;
	private String dayOfTheWeek = null;
	private LocalDateTime trainingAt = null;
	private String note = null;
	private String status = null;
	private String tName = null;
	private String tPhone = null;
	private String mName = null;
	private String mPhone = null;
	
	public PtContractDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PtContractDTO(int ptContractId, String dayOfTheWeek, LocalDateTime trainingAt, String note, String status,
			String tName, String tPhone, String mName, String mPhone) {
		super();
		this.ptContractId = ptContractId;
		this.dayOfTheWeek = dayOfTheWeek;
		this.trainingAt = trainingAt;
		this.note = note;
		this.status = status;
		this.tName = tName;
		this.tPhone = tPhone;
		this.mName = mName;
		this.mPhone = mPhone;
	}
	public int getPtContractId() {
		return ptContractId;
	}
	public void setPtContractId(int ptContractId) {
		this.ptContractId = ptContractId;
	}
	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}
	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}
	public LocalDateTime getTrainingAt() {
		return trainingAt;
	}
	public void setTrainingAt(LocalDateTime trainingAt) {
		this.trainingAt = trainingAt;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTName() {
		return tName;
	}
	public void setTName(String tName) {
		this.tName = tName;
	}
	public String getTPhone() {
		return tPhone;
	}
	public void setTPhone(String tPhone) {
		this.tPhone = tPhone;
	}
	public String getMName() {
		return mName;
	}
	public void setMName(String mName) {
		this.mName = mName;
	}
	public String getMPhone() {
		return mPhone;
	}
	public void setMPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	@Override
	public String toString() {
		return "MembershipDTO [ptContractId=" + ptContractId + ", dayOfTheWeek=" + dayOfTheWeek
				+ ", trainingAt=" + trainingAt + ", note=" + note + ", status=" + status + ", tName=" + tName
				+ ", tPhone=" + tPhone + ", mName=" + mName + ", mPhone=" + mPhone + "]";
	}
}
