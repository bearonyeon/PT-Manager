package model.service.exception;

/** 기록이 없거나, 본인/담당 회원의 기록이 아니라서 접근할 수 없을 때 */
public class BodyRecordNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public BodyRecordNotFoundException(String message) {
		super(message);
	}
}
