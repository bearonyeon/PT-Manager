package model.service.exception;

/** 같은 날짜에 신체 기록이 이미 있을 때 (등록/수정 폼으로 돌아가 메시지 표시) */
public class ExistingBodyRecordException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExistingBodyRecordException(String message) {
		super(message);
	}
}
