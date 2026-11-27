package model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import model.dao.jdbc.BodyRecordDAO;
import model.dto.BodyRecordDTO;
import model.dto.WeeklyBodyStatDTO;
import model.service.exception.BodyRecordNotFoundException;
import model.service.exception.ExistingBodyRecordException;

/*
 * 신체 기록 관리 (R37, R38, R15)
 * Controller는 이 클래스만 호출하고, DAO를 직접 부르지 않음.
 *
 * 사용 예) BodyRecordManager manager = BodyRecordManager.getInstance();
 *          List<BodyRecordDTO> list = manager.findBodyRecordList(userId, null, null);
*/

public class BodyRecordManager {
	private static BodyRecordManager instance = new BodyRecordManager();
	private BodyRecordDAO bodyRecordDAO;

	private BodyRecordManager() {
		bodyRecordDAO = new BodyRecordDAO();
	}

	public static BodyRecordManager getInstance() {
		return instance;
	}

	/* 목록 조회 (날짜가 null이면 전체). 시작일이 종료일보다 늦으면 서로 바꿔서 조회 */
	public List<BodyRecordDTO> findBodyRecordList(String userId, Date startDate, Date endDate) {
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			Date tmp = startDate;
			startDate = endDate;
			endDate = tmp;
		}
		return bodyRecordDAO.getBodyRecordList(userId, startDate, endDate);
	}

	/* 상세 조회 - 본인 기록이 아니거나 없으면 예외 */
	public BodyRecordDTO findBodyRecord(int bodyId, String userId) throws BodyRecordNotFoundException {
		BodyRecordDTO record = bodyRecordDAO.getBodyRecordById(bodyId, userId);
		if (record == null) {
			throw new BodyRecordNotFoundException("해당 신체 기록을 찾을 수 없습니다.");
		}
		return record;
	}

	/* 등록 - 값 검사 → 같은 날짜 중복 검사 → insert */
	public int create(BodyRecordDTO record) throws ExistingBodyRecordException {
		validate(record);
		if (bodyRecordDAO.existsBodyRecord(record.getMemberId(), record.getMeasureDate(), 0) > 0) {
			throw new ExistingBodyRecordException(record.getMeasureDate() + " 날짜의 신체 기록이 이미 있습니다.");
		}
		return bodyRecordDAO.insertBodyRecord(record);
	}

	/* 수정 - 자기 자신을 제외하고 같은 날짜가 있는지 확인 */
	public int update(BodyRecordDTO record) throws ExistingBodyRecordException, BodyRecordNotFoundException {
		validate(record);
		if (bodyRecordDAO.existsBodyRecord(record.getMemberId(), record.getMeasureDate(), record.getBodyId()) > 0) {
			throw new ExistingBodyRecordException(record.getMeasureDate() + " 날짜의 신체 기록이 이미 있습니다.");
		}
		int result = bodyRecordDAO.updateBodyRecord(record);
		if (result == 0) {
			throw new BodyRecordNotFoundException("수정할 신체 기록을 찾을 수 없습니다.");
		}
		return result;
	}

	/* 삭제 */
	public int remove(int bodyId, String userId) throws BodyRecordNotFoundException {
		int result = bodyRecordDAO.deleteBodyRecord(bodyId, userId);
		if (result == 0) {
			throw new BodyRecordNotFoundException("삭제할 신체 기록을 찾을 수 없습니다.");
		}
		return result;
	}

	/* 주간별 변화 (R38) - 날짜가 null이면 최근 8주 */
	public List<WeeklyBodyStatDTO> findWeeklyStats(String userId, Date startDate, Date endDate) {
		if (endDate == null) {
			endDate = Date.valueOf(LocalDate.now());
		}
		if (startDate == null) {
			startDate = Date.valueOf(endDate.toLocalDate().minusWeeks(8));
		}
		return bodyRecordDAO.getWeeklyBodyStats(userId, startDate, endDate);
	}

	/* 가장 최근 기록 (추천에서 사용, 없으면 null) */
	public BodyRecordDTO findLatestRecord(String userId) {
		return bodyRecordDAO.getLatestBodyRecord(userId);
	}

	/* 트레이너가 담당 회원의 신체 기록 목록을 볼 때 (R15) */
	public List<BodyRecordDTO> findMemberBodyRecordList(String trainerId, String memberId, Date startDate,
			Date endDate) throws BodyRecordNotFoundException {
		if (bodyRecordDAO.countPtRelation(trainerId, memberId) == 0) {
			throw new BodyRecordNotFoundException("담당 회원의 기록만 조회할 수 있습니다.");
		}
		return findBodyRecordList(memberId, startDate, endDate);
	}

	/* 입력값 검사 - 범위는 DDL 컬럼 크기(NUMBER(4,1) 등)에 맞춤 */
	private void validate(BodyRecordDTO r) {
		if (r.getMemberId() == null) {
			throw new IllegalArgumentException("로그인이 필요합니다.");
		}
		if (r.getMeasureDate() == null) {
			throw new IllegalArgumentException("측정 날짜를 입력하세요.");
		}
		if (r.getMeasureDate().toLocalDate().isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("미래 날짜는 입력할 수 없습니다.");
		}
		if (r.getWeight() == null && r.getMuscleMass() == null && r.getBodyFatRate() == null) {
			throw new IllegalArgumentException("체중, 골격근량, 체지방률 중 하나 이상 입력하세요.");
		}
		checkRange(r.getWeight(), 1, 500, "체중");
		checkRange(r.getMuscleMass(), 1, 200, "골격근량");
		checkRange(r.getBodyFatRate(), 0, 99.9, "체지방률");
	}

	private void checkRange(Double value, double min, double max, String label) {
		if (value != null && (value < min || value > max)) {
			throw new IllegalArgumentException(label + "은(는) " + min + " ~ " + max + " 사이로 입력하세요.");
		}
	}
}
