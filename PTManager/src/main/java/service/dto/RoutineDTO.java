package service.dto;

import java.sql.Date;

public class RoutineDTO {
	private int routineId = 0;
	private String routineName = null;
	private Date createdDate = null;
	private String creatorName = null;
	private String note = null;
	
	public RoutineDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoutineDTO(int routineId, String routineName, Date createdDate, String creatorName, String note) {
		super();
		this.routineId = routineId;
		this.routineName = routineName;
		this.createdDate = createdDate;
		this.creatorName = creatorName;
		this.note = note;
	}

	public int getRoutineId() {
		return routineId;
	}

	public void setRoutineId(int routineId) {
		this.routineId = routineId;
	}

	public String getRoutineName() {
		return routineName;
	}

	public void setRoutineName(String routineName) {
		this.routineName = routineName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "RoutineDTO [routineId=" + routineId + ", routineName=" + routineName + ", createdDate=" + createdDate
				+ ", creatorName=" + creatorName + ", note=" + note + "]";
	}
}