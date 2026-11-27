package model.dao.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.dto.BodyPartCountDTO;
import model.dto.ExerciseCandidateDTO;

/*
 * 추천 흐름 (RecommendManager에서 조합):
 *   1. getBodyPartCounts()      → 최근 N일 부위별 수행 횟수 (적은 순)
 *   2. 횟수가 적은 부위부터 선택
 *   3. getCandidatesByBodyPart() → 그 부위 운동 중 오래 안 한 운동부터
 *   4. 결과를 RoutineItem 형태로 만들어 화면에 출력, [저장] 시 RoutineDAO로 insert
*/

public class RecommendDAO {
	private JDBCUtil jdbcUtil = null; // JDBCUtil 필드 선언

	public RecommendDAO() { // 생성자
		jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
	}

	/* 
	 * 회원이 실제로 수행한 (운동기록ID, 운동ID, 날짜) 목록 - 서브쿼리로 재사용
	 * 세트 수 중복으로 세지 않되 운동 종목 몇 번 했는지 셀 수 있게. 예: 벤치1, 딥스1하면 가슴 2 한거로 침
	 */
	private static final String PERFORMED_EXERCISES =
			"SELECT DISTINCT er.\"exerciseRecordId\", ri.\"exerciseId\", er.\"exerciseDate\" "
			+ "FROM \"ExerciseRecord\" er "
			+ "JOIN \"PerformedSet\" ps ON er.\"exerciseRecordId\" = ps.\"exerciseRecordId\" "
			+ "JOIN \"RoutineItem\" ri ON ps.\"routineItemId\" = ri.\"routineItemId\" "
			+ "WHERE er.\"userId\" = ? ";

	/*
	 * 최근 periodDays일 동안 부위별 운동 수행 횟수 (R34)
	 * - BodyPart 기준 LEFT JOIN이라 한 번도 안 한 부위도 count = 0으로 포함됨
	 * - 수행 횟수 적은 순 정렬 → 앞쪽이 추천 우선 부위
	 * - 한 운동이 여러 부위를 자극하면(ExerciseTarget 여러 행) 각 부위에 1회씩 집계
	 * - 같은 날 같은 운동을 여러 세트 해도 (운동기록, 운동) 기준 1회로 셈
	 */
	public List<BodyPartCountDTO> getBodyPartCounts(String memberId, int periodDays) {
		List<BodyPartCountDTO> list = new ArrayList<BodyPartCountDTO>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT bp.\"bodyPartId\", bp.\"name\" AS \"bName\", COUNT(p.\"exerciseRecordId\") AS \"cnt\" ");
		query.append("FROM \"BodyPart\" bp ");
		query.append("LEFT JOIN \"ExerciseTarget\" et ON bp.\"bodyPartId\" = et.\"bodyPartId\" ");
		query.append("LEFT JOIN (" + PERFORMED_EXERCISES);
		query.append("           AND er.\"exerciseDate\" >= TRUNC(SYSDATE) - ?) p ");
		query.append("       ON et.\"exerciseId\" = p.\"exerciseId\" ");
		query.append("GROUP BY bp.\"bodyPartId\", bp.\"name\" ");
		query.append("ORDER BY \"cnt\" ASC, bp.\"bodyPartId\" ASC");

		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] { memberId, periodDays });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while (rs.next()) {
				list.add(new BodyPartCountDTO(rs.getInt("bodyPartId"), rs.getString("bName"), rs.getInt("cnt")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return list;
	}

	/*
	 * 특정 부위의 추천 후보 운동 목록 (R35)
	 * - 이 회원이 각 운동을 마지막으로 수행한 날짜를 함께 조회 (기간 제한 없음)
	 * - 한 번도 안 한 운동(lastDate = null) → 오래전에 한 운동 → 최근 한 운동 순
	 */
	public List<ExerciseCandidateDTO> getCandidatesByBodyPart(String memberId, int bodyPartId) {
		List<ExerciseCandidateDTO> list = new ArrayList<ExerciseCandidateDTO>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT e.\"exerciseId\", e.\"eName\", bp.\"bodyPartId\", bp.\"name\" AS \"bName\", ");
		query.append("       h.\"lastDate\" ");
		query.append("FROM \"Exercise\" e ");
		query.append("JOIN \"ExerciseTarget\" et ON e.\"exerciseId\" = et.\"exerciseId\" ");
		query.append("JOIN \"BodyPart\" bp ON et.\"bodyPartId\" = bp.\"bodyPartId\" ");
		query.append("LEFT JOIN (SELECT p.\"exerciseId\", MAX(p.\"exerciseDate\") AS \"lastDate\" ");
		query.append("           FROM (" + PERFORMED_EXERCISES + ") p ");
		query.append("           GROUP BY p.\"exerciseId\") h ON e.\"exerciseId\" = h.\"exerciseId\" ");
		query.append("WHERE bp.\"bodyPartId\" = ? ");
		query.append("ORDER BY h.\"lastDate\" ASC NULLS FIRST, e.\"exerciseId\" ASC");

		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] { memberId, bodyPartId });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while (rs.next()) {
				list.add(new ExerciseCandidateDTO(rs.getInt("exerciseId"), rs.getString("eName"),
						rs.getInt("bodyPartId"), rs.getString("bName"), rs.getDate("lastDate")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return list;
	}

	/* 기간 내 운동 기록이 하나라도 있는지 (0이면 "기록 없음" → 기본 추천 루틴 제공) */
	public int countRecentRecords(String memberId, int periodDays) {
		int result = 0;

		String query = "SELECT COUNT(*) FROM \"ExerciseRecord\" "
				+ "WHERE \"userId\" = ? AND \"exerciseDate\" >= TRUNC(SYSDATE) - ?";

		jdbcUtil.setSqlAndParameters(query, new Object[] { memberId, periodDays });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return result;
	}
}
