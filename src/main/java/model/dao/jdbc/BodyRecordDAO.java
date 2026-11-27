package model.dao.jdbc;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.BodyRecordDTO;
import model.dto.WeeklyBodyStatDTO;

/*
 * - 조회/수정/삭제는 항상 userId 조건을 함께 걸어서 다른 회원 기록에 접근하지 못하게 함
 *   (트레이너가 담당 회원 기록을 볼 때는 Manager에서 PT 관계를 확인한 뒤 회원 ID로 호출)
 * - 같은 날짜 중복 등록 여부는 Manager에서 existsBodyRecord()로 먼저 확인
 */

public class BodyRecordDAO {
	private JDBCUtil jdbcUtil = null; // JDBCUtil 필드 선언

	public BodyRecordDAO() { // 생성자
		jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
	}

	private static final String DATE_NAME = "mesureDate";            // DDL 오타있어서 날짜 따로 빼둠
	private static final String DATE_COL = "\"" + DATE_NAME + "\""; 
	private static final String COLUMNS = "\"bodyRecordId\", \"userId\", " + DATE_COL + ", \"weight\", \"muscleMass\", \"bodyFatRate\" ";

	/* 신체 기록 등록 (R37) - 성공 시 1 */
	public int insertBodyRecord(BodyRecordDTO record) {
		int result = 0;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO \"BodyRecord\" (" + COLUMNS + ") ");
		sql.append("VALUES (\"body_record_seq\".NEXTVAL, ?, ?, ?, ?, ?)");

		Object[] param = new Object[] { record.getMemberId(), record.getMeasureDate(), record.getWeight(),
				record.getMuscleMass(), record.getBodyFatRate() };

		jdbcUtil.setSqlAndParameters(sql.toString(), param);

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return result;
	}

	/* 신체 기록 수정 (R37) - 본인 기록만 수정됨, 성공 시 1 */
	public int updateBodyRecord(BodyRecordDTO record) {
		int result = 0;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE \"BodyRecord\" SET ");
		sql.append(DATE_COL + " = ?, ");
		sql.append("\"weight\" = ?, ");
		sql.append("\"muscleMass\" = ?, ");
		sql.append("\"bodyFatRate\" = ? ");
		sql.append("WHERE \"bodyRecordId\" = ? AND \"userId\" = ?");

		Object[] param = new Object[] { record.getMeasureDate(), record.getWeight(), record.getMuscleMass(),
				record.getBodyFatRate(), record.getBodyId(), record.getMemberId() };

		jdbcUtil.setSqlAndParameters(sql.toString(), param);

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return result;
	}

	/* 신체 기록 삭제 (R37) - 본인 기록만 삭제됨, 성공 시 1 */
	public int deleteBodyRecord(int bodyId, String memberId) {
		int result = 0;

		String sql = "DELETE FROM \"BodyRecord\" WHERE \"bodyRecordId\" = ? AND \"userId\" = ?";

		jdbcUtil.setSqlAndParameters(sql, new Object[] { bodyId, memberId });

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return result;
	}

	/* 신체 기록 상세 조회 (있으면 DTO, 없으면 null) */
	public BodyRecordDTO getBodyRecordById(int bodyId, String memberId) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT " + COLUMNS);
		query.append("FROM \"BodyRecord\" ");
		query.append("WHERE \"bodyRecordId\" = ? AND \"userId\" = ?");

		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] { bodyId, memberId });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				return toDTO(rs);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	/*
	 * 기간별 신체 기록 목록 (R37 조회, 목록 화면)
	 * startDate/endDate가 null이면 해당 조건 없이 전체 조회. 최신 날짜 순.
	 * DATE 컬럼에 시각이 섞여 있을 수 있으므로(DEFAULT sysdate) TRUNC로 날짜만 비교
	 */
	public List<BodyRecordDTO> getBodyRecordList(String memberId, Date startDate, Date endDate) {
		List<BodyRecordDTO> list = new ArrayList<BodyRecordDTO>();
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT " + COLUMNS);
		query.append("FROM \"BodyRecord\" ");
		query.append("WHERE \"userId\" = ? ");
		params.add(memberId);

		if (startDate != null) {
			query.append("AND TRUNC(" + DATE_COL + ") >= ? ");
			params.add(startDate);
		}
		if (endDate != null) {
			query.append("AND TRUNC(" + DATE_COL + ") <= ? ");
			params.add(endDate);
		}
		query.append("ORDER BY " + DATE_COL + " DESC");

		jdbcUtil.setSqlAndParameters(query.toString(), params.toArray());

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while (rs.next()) {
				list.add(toDTO(rs));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return list;
	}

	/* 가장 최근 신체 기록 (루틴 추천 R36에서 참고용, 없으면 null) */
	public BodyRecordDTO getLatestBodyRecord(String memberId) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT " + COLUMNS);
		query.append("FROM (SELECT " + COLUMNS + " FROM \"BodyRecord\" ");
		query.append("      WHERE \"userId\" = ? ORDER BY " + DATE_COL + " DESC) ");
		query.append("WHERE ROWNUM = 1");

		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] { memberId });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				return toDTO(rs);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	/*
	 * 같은 날짜에 이미 기록이 있는지 확인 (0이면 등록 가능)
	 * 수정 시에는 자기 자신을 제외해야 하므로 excludeBodyId에 수정 중인 bodyId 전달 (등록 시 0)
	 */
	public int existsBodyRecord(String memberId, Date measureDate, int excludeBodyId) {
		int result = 0;

		String query = "SELECT COUNT(*) FROM \"BodyRecord\" "
				+ "WHERE \"userId\" = ? AND TRUNC(" + DATE_COL + ") = TRUNC(?) AND \"bodyRecordId\" <> ?";

		jdbcUtil.setSqlAndParameters(query, new Object[] { memberId, measureDate, excludeBodyId });

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

	/*
	 * 주간별 신체 변화 (R38)
	 * TRUNC(날짜, 'IW') = 그 주의 월요일 → 주 단위로 묶어서 평균. 오래된 주부터.
	 */
	public List<WeeklyBodyStatDTO> getWeeklyBodyStats(String memberId, Date startDate, Date endDate) {
		List<WeeklyBodyStatDTO> list = new ArrayList<WeeklyBodyStatDTO>();

		StringBuffer query = new StringBuffer();
		query.append("SELECT TRUNC(" + DATE_COL + ", 'IW') AS \"weekStart\", ");
		query.append("       COUNT(*) AS \"cnt\", ");
		query.append("       ROUND(AVG(\"weight\"), 1) AS \"avgWeight\", ");
		query.append("       ROUND(AVG(\"muscleMass\"), 1) AS \"avgMuscleMass\", ");
		query.append("       ROUND(AVG(\"bodyFatRate\"), 1) AS \"avgBodyFatRate\" ");
		query.append("FROM \"BodyRecord\" ");
		query.append("WHERE \"userId\" = ? AND TRUNC(" + DATE_COL + ") BETWEEN ? AND ? ");
		query.append("GROUP BY TRUNC(" + DATE_COL + ", 'IW') ");
		query.append("ORDER BY \"weekStart\" ASC");

		jdbcUtil.setSqlAndParameters(query.toString(), new Object[] { memberId, startDate, endDate });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while (rs.next()) {
				list.add(new WeeklyBodyStatDTO(rs.getDate("weekStart"), rs.getInt("cnt"),
						getNullableDouble(rs, "avgWeight"), getNullableDouble(rs, "avgMuscleMass"),
						getNullableDouble(rs, "avgBodyFatRate")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return list;
	}

	/*
	 * 트레이너가 이 회원을 담당하는지 (R15: 트레이너가 담당 회원 신체 기록 조회)
	 * "PersonalTraining" 테이블에 (trainerId, memberId) 행이 있으면 1 이상
	 * 트레이너가 담당 회원 기록을 볼 때는 Manager에서 PT 관계를 확인한 뒤 회원 ID로 호출해야하는 거 때문에 적었는데
	 * PT 관리 담당 DAO(PtContractDAO)에 들어가야하는 내용 같기도..?
	 */
	public int countPtRelation(String trainerId, String memberId) {
		int result = 0;

		String query = "SELECT COUNT(*) FROM \"PersonalTraining\" WHERE \"trainerId\" = ? AND \"memberId\" = ?";

		jdbcUtil.setSqlAndParameters(query, new Object[] { trainerId, memberId });

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

	/* ResultSet 한 행 → BodyRecordDTO */
	private BodyRecordDTO toDTO(ResultSet rs) throws SQLException {
		return new BodyRecordDTO(rs.getInt("bodyRecordId"), rs.getString("userId"), rs.getDate(DATE_NAME),
				getNullableDouble(rs, "weight"), getNullableDouble(rs, "muscleMass"),
				getNullableDouble(rs, "bodyFatRate"));
	}

	/* rs.getDouble()은 NULL을 0.0으로 돌려주므로, NULL은 null로 유지 */
	private Double getNullableDouble(ResultSet rs, String column) throws SQLException {
		double value = rs.getDouble(column);
		return rs.wasNull() ? null : value;
	}
}
