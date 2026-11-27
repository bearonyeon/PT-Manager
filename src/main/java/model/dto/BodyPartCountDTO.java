package model.dto;

/*
운동 부위별 최근 수행 횟수 (R34)
- BodyPart + ExerciseRecord 집계 결과
*/
public class BodyPartCountDTO {
	private int bodyPartId = 0;
	private String bName = null;
	private int count = 0;    // 기간 내 해당 부위 운동 수행 횟수 (0이면 한 번도 안 함)

	public BodyPartCountDTO() {
	}

	public BodyPartCountDTO(int bodyPartId, String bName, int count) {
		this.bodyPartId = bodyPartId;
		this.bName = bName;
		this.count = count;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "BodyPartCountDTO [bodyPartId=" + bodyPartId + ", bName=" + bName + ", count=" + count + "]";
	}
}
