package model.dto;

import java.sql.Date;

/*
 * 신체 측정 기록 (BodyRecord 테이블 1행)
 * - 골격근량, 체지방률은 인바디를 안 잰 날도 있으므로 null 허용(Double)
*/
public class BodyRecordDTO {
	private int bodyId = 0;
	private String memberId = null;
	private Date measureDate = null;
	private Double weight = null;
	private Double muscleMass = null;
	private Double bodyFatRate = null;

	public BodyRecordDTO() {
	}

	// 등록용 (bodyId는 시퀀스로 생성)
	public BodyRecordDTO(String memberId, Date measureDate, Double weight, Double muscleMass, Double bodyFatRate) {
		this.memberId = memberId;
		this.measureDate = measureDate;
		this.weight = weight;
		this.muscleMass = muscleMass;
		this.bodyFatRate = bodyFatRate;
	}

	// 조회/수정용
	public BodyRecordDTO(int bodyId, String memberId, Date measureDate, Double weight, Double muscleMass,
			Double bodyFatRate) {
		this(memberId, measureDate, weight, muscleMass, bodyFatRate);
		this.bodyId = bodyId;
	}

	/** 
	 * BMI 계산 (키는 account.height, cm 단위). 키나 체중이 없으면 null
	 * account에 height가 없다?
	 */
	public Double getBmi(double heightCm) {
		if (weight == null || heightCm <= 0)
			return null;
		double m = heightCm / 100.0;
		return Math.round(weight / (m * m) * 10) / 10.0;
	}

	public int getBodyId() {
		return bodyId;
	}

	public void setBodyId(int bodyId) {
		this.bodyId = bodyId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getMeasureDate() {
		return measureDate;
	}

	public void setMeasureDate(Date measureDate) {
		this.measureDate = measureDate;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getMuscleMass() {
		return muscleMass;
	}

	public void setMuscleMass(Double muscleMass) {
		this.muscleMass = muscleMass;
	}

	public Double getBodyFatRate() {
		return bodyFatRate;
	}

	public void setBodyFatRate(Double bodyFatRate) {
		this.bodyFatRate = bodyFatRate;
	}

	@Override
	public String toString() {
		return "BodyRecordDTO [bodyId=" + bodyId + ", memberId=" + memberId + ", measureDate=" + measureDate
				+ ", weight=" + weight + ", muscleMass=" + muscleMass + ", bodyFatRate=" + bodyFatRate + "]";
	}
}
