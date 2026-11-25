package model.service;

import model.dao.mybatis.AccountRepository;

/*
 * 사용자 계정 관리 API를 사용하는 개발자들이 직접 접근하는 클래스
 * DAO와 repository를 이용하여 데이터베이스 연동 및 처리 작업을 가능하게 함
 * 데이터베이스의 데이터들을 이용하여 비즈니스 로직을 수행하는 역할
 */

public class UserManager {
	private static UserManager userManager = new UserManager();
	private AccountRepository accRepo;
	
	private UserManager() {
		try {
			accRepo = new AccountRepository();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static UserManager getInstance() {
		return userManager;
	}
	
	
}
