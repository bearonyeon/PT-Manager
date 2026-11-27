package model.service;

import java.sql.SQLException;

import model.dao.mybatis.AccountRepository;
import model.dto.AccountDTO;
import model.service.exception.ExistingUserException;
import model.service.exception.PasswordMismatchException;
import model.service.exception.UserNotFoundException;

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
	
	public int insertAccount(AccountDTO account) throws SQLException, ExistingUserException {
		if (accRepo.existingAccount(account.getUserId()) == true) {
			throw new ExistingUserException(account.getUserId() + "는 존재하는 아이디입니다.");
		}
		return accRepo.insertAccount(account);
	}
	
	public int updateAccount(AccountDTO account) {
		return 1;
	}
	
	public AccountDTO findAccount(String userId) throws SQLException, UserNotFoundException {
		AccountDTO account = accRepo.getAccountById(userId);
		
		if (account == null) {
			throw new UserNotFoundException(userId + "는 존재하지 않는 아이디입니다.");
		}
		return account;
	}
	
	public boolean login(String userId, String password) throws SQLException, UserNotFoundException, PasswordMismatchException {
		AccountDTO account = findAccount(userId);
		
		if (!account.matchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return true;
	}
}
