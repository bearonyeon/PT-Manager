package model.dto;

import java.sql.Date;

/*
 * 추천 후보 운동 (R35)
 * - 특정 부위의 운동 종목 + 이 회원이 마지막으로 수행한 날짜
 * - lastDate가 null이면 한 번도 안 한 운동 → 우선 추천
*/
public class ExerciseCandidateDTO {
	private int exerciseId = 0;
	private String eName = null;
	private int bodyPartId = 0;
	private String bName = null;
	private Date lastDate = null;

	public ExerciseCandidateDTO() {
	}

	public ExerciseCandidateDTO(int exerciseId, String eName, int bodyPartId, String bName, Date lastDate) {
		this.exerciseId = exerciseId;
		this.eName = eName;
		this.bodyPartId = bodyPartId;
		this.bName = bName;
		this.lastDate = lastDate;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getEName() {
		return eName;
	}

	public void setEName(String eName) {
		this.eName = eName;
	}

	public int getBodyPartId() {
		return bodyPartId;
	}

	public void setBodyPartId(int bodyPartId) {
		this.bodyPartId = bodyPartId;
	}

	public String getBName() {
		return bName;
	}

	public void setBName(String bName) {
		this.bName = bName;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	@Override
	public String toString() {
		return "ExerciseCandidateDTO [exerciseId=" + exerciseId + ", eName=" + eName + ", bodyPartId=" + bodyPartId
				+ ", bName=" + bName + ", lastDate=" + lastDate + "]";
	}
}
