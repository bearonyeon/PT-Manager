package model.dto;

import java.util.ArrayList;
import java.util.List;

/*
 * 루틴 추천 결과 (recommendedRoutineResultView.jsp에 넘길 묶음)
 * - bodyPartCounts      : 부위별 최근 수행 횟수 (적은 순) → 화면의 "부위별 빈도" 표
 * - recommendedExercises: 추천 운동 목록 (순서대로 루틴 아이템이 됨)
 * - noRecord            : 기간 내 운동 기록이 없어서 기본 추천을 한 경우 true
 * - latestBodyRecord    : 참고용 최근 신체 기록 (R36, 없으면 null)
 * - bodyNote            : 신체 기록 기반 안내 문구 (없으면 null)
*/

public class RecommendResultDTO {
	private int periodDays = 0;
	private boolean noRecord = false;
	private List<BodyPartCountDTO> bodyPartCounts = new ArrayList<BodyPartCountDTO>();
	private List<ExerciseCandidateDTO> recommendedExercises = new ArrayList<ExerciseCandidateDTO>();
	private BodyRecordDTO latestBodyRecord = null;
	private String bodyNote = null;

	public int getPeriodDays() {
		return periodDays;
	}

	public void setPeriodDays(int periodDays) {
		this.periodDays = periodDays;
	}

	public boolean isNoRecord() {
		return noRecord;
	}

	public void setNoRecord(boolean noRecord) {
		this.noRecord = noRecord;
	}

	public List<BodyPartCountDTO> getBodyPartCounts() {
		return bodyPartCounts;
	}

	public void setBodyPartCounts(List<BodyPartCountDTO> bodyPartCounts) {
		this.bodyPartCounts = bodyPartCounts;
	}

	public List<ExerciseCandidateDTO> getRecommendedExercises() {
		return recommendedExercises;
	}

	public void setRecommendedExercises(List<ExerciseCandidateDTO> recommendedExercises) {
		this.recommendedExercises = recommendedExercises;
	}

	public BodyRecordDTO getLatestBodyRecord() {
		return latestBodyRecord;
	}

	public void setLatestBodyRecord(BodyRecordDTO latestBodyRecord) {
		this.latestBodyRecord = latestBodyRecord;
	}

	public String getBodyNote() {
		return bodyNote;
	}

	public void setBodyNote(String bodyNote) {
		this.bodyNote = bodyNote;
	}

	@Override
	public String toString() {
		return "RecommendResultDTO [periodDays=" + periodDays + ", noRecord=" + noRecord + ", bodyPartCounts="
				+ bodyPartCounts + ", recommendedExercises=" + recommendedExercises + ", latestBodyRecord="
				+ latestBodyRecord + ", bodyNote=" + bodyNote + "]";
	}
}
