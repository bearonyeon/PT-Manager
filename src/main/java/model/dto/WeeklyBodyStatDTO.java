package model.dto;

import java.sql.Date;

/*
 * 주간별 신체 변화 (R38)
 * - 여러 BodyRecord를 주 단위로 묶은 집계 결과라 도메인 클래스로는 표현 불가 → 별도 DTO
*/

public class WeeklyBodyStatDTO {
	private Date weekStart = null;      // 해당 주의 월요일
	private int recordCount = 0;        // 그 주에 측정한 횟수
	private Double avgWeight = null;
	private Double avgMuscleMass = null;
	private Double avgBodyFatRate = null;

	public WeeklyBodyStatDTO() {
	}

	public WeeklyBodyStatDTO(Date weekStart, int recordCount, Double avgWeight, Double avgMuscleMass,
			Double avgBodyFatRate) {
		this.weekStart = weekStart;
		this.recordCount = recordCount;
		this.avgWeight = avgWeight;
		this.avgMuscleMass = avgMuscleMass;
		this.avgBodyFatRate = avgBodyFatRate;
	}

	public Date getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(Date weekStart) {
		this.weekStart = weekStart;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public Double getAvgWeight() {
		return avgWeight;
	}

	public void setAvgWeight(Double avgWeight) {
		this.avgWeight = avgWeight;
	}

	public Double getAvgMuscleMass() {
		return avgMuscleMass;
	}

	public void setAvgMuscleMass(Double avgMuscleMass) {
		this.avgMuscleMass = avgMuscleMass;
	}

	public Double getAvgBodyFatRate() {
		return avgBodyFatRate;
	}

	public void setAvgBodyFatRate(Double avgBodyFatRate) {
		this.avgBodyFatRate = avgBodyFatRate;
	}

	@Override
	public String toString() {
		return "WeeklyBodyStatDTO [weekStart=" + weekStart + ", recordCount=" + recordCount + ", avgWeight="
				+ avgWeight + ", avgMuscleMass=" + avgMuscleMass + ", avgBodyFatRate=" + avgBodyFatRate + "]";
	}
}
