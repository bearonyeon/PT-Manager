package model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.dao.jdbc.BodyRecordDAO;
import model.dao.jdbc.RecommendDAO;
import model.dto.BodyPartCountDTO;
import model.dto.BodyRecordDTO;
import model.dto.ExerciseCandidateDTO;
import model.dto.RecommendResultDTO;

/*
 * 루틴 추천 알고리즘 (R34, R35, R36)
 *
 * 1. 최근 periodDays일 동안 부위별 수행 횟수를 구함 (적은 순)
 * 2. 횟수가 적은 부위부터 돌면서, 부위마다 "오래 안 한 운동"을 최대 PER_PART개씩 고름
 *    (여러 부위를 자극하는 운동은 한 번만 들어가도록 중복 제거)
 * 3. exerciseCount개가 찰 때까지 반복. 부위를 한 바퀴 돌았는데도 모자라면 PER_PART 제한 없이 한 번 더 채움
 * 4. 기간 내 운동 기록이 없으면 noRecord = true (모든 부위 0회 → 부위 순서대로 기본 추천이 됨)
 * 5. 최근 신체 기록이 있으면 참고 문구를 붙임 (R36, Could-Have)
*/

public class RecommendManager {
	private static RecommendManager instance = new RecommendManager();
	private RecommendDAO recommendDAO;
	private BodyRecordDAO bodyRecordDAO;

	public static final int DEFAULT_PERIOD_DAYS = 14;
	public static final int DEFAULT_EXERCISE_COUNT = 5;
	private static final int PER_PART = 2; // 한 부위에서 1차로 고를 최대 운동 수

	private RecommendManager() {
		recommendDAO = new RecommendDAO();
		bodyRecordDAO = new BodyRecordDAO();
	}

	public static RecommendManager getInstance() {
		return instance;
	}

	public RecommendResultDTO recommendRoutine(String userId, int periodDays, int exerciseCount) {
		if (periodDays < 1 || periodDays > 365) {
			periodDays = DEFAULT_PERIOD_DAYS;
		}
		if (exerciseCount < 1 || exerciseCount > 10) {
			exerciseCount = DEFAULT_EXERCISE_COUNT;
		}

		RecommendResultDTO result = new RecommendResultDTO();
		result.setPeriodDays(periodDays);
		result.setNoRecord(recommendDAO.countRecentRecords(userId, periodDays) == 0);

		// 1. 부위별 수행 횟수 (적은 순)
		List<BodyPartCountDTO> counts = recommendDAO.getBodyPartCounts(userId, periodDays);
		result.setBodyPartCounts(counts);

		// 부위별 후보는 한 번만 조회해서 재사용
		List<List<ExerciseCandidateDTO>> candidatesByPart = new ArrayList<List<ExerciseCandidateDTO>>();
		for (BodyPartCountDTO part : counts) {
			candidatesByPart.add(recommendDAO.getCandidatesByBodyPart(userId, part.getBodyPartId()));
		}

		// 2~3. 적은 부위부터 채우기
		List<ExerciseCandidateDTO> picked = new ArrayList<ExerciseCandidateDTO>();
		Set<Integer> pickedIds = new HashSet<Integer>();
		pick(candidatesByPart, PER_PART, exerciseCount, picked, pickedIds);
		if (picked.size() < exerciseCount) {
			pick(candidatesByPart, Integer.MAX_VALUE, exerciseCount, picked, pickedIds);
		}
		result.setRecommendedExercises(picked);

		// 5. 신체 기록 참고 (R36)
		BodyRecordDTO latest = bodyRecordDAO.getLatestBodyRecord(userId);
		result.setLatestBodyRecord(latest);
		result.setBodyNote(makeBodyNote(latest));

		return result;
	}

	private void pick(List<List<ExerciseCandidateDTO>> candidatesByPart, int perPart, int max,
			List<ExerciseCandidateDTO> picked, Set<Integer> pickedIds) {
		for (List<ExerciseCandidateDTO> candidates : candidatesByPart) {
			int takenFromPart = 0;
			for (ExerciseCandidateDTO c : candidates) {
				if (picked.size() >= max) {
					return;
				}
				if (takenFromPart >= perPart) {
					break;
				}
				if (pickedIds.add(c.getExerciseId())) { // 처음 고르는 운동이면 true
					picked.add(c);
					takenFromPart++;
				}
			}
		}
	}

	/* 단순 참고 문구 - 기준값은 팀에서 정하면 바꾸기 */
	private String makeBodyNote(BodyRecordDTO latest) {
		if (latest == null) {
			return "신체 기록을 등록하면 추천에 참고할 수 있어요.";
		}
		Double fat = latest.getBodyFatRate();
		if (fat != null && fat >= 30) {
			return "최근 체지방률이 " + fat + "%예요. 추천 루틴 뒤에 유산소 운동을 함께 하는 것을 권장해요.";
		}
		return null;
	}
}
