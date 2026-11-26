package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import model.dao.mybatis.mapper.AccountMapper;
import model.dto.AccountDTO;

// 사용자 계정 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
// account 테이블에서 사용자 계정 정보를 추가, 수정, 삭제, 검색 수행

public class AccountRepository {
	private SqlSessionFactory sqlSessionFactory;
	
	public AccountRepository() {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	// 회원 계정 추가
	public int insertAccount(AccountDTO account) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(AccountMapper.class).insertAccount(account);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	// 회원 정보 수정
	public int updateAccount(AccountDTO account) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(AccountMapper.class).updateAccount(account);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	// 회원 정보 삭제
	public int deleteAccount(String userId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(AccountMapper.class).deleteAccount(userId);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	// 전체 회원 조회
	public List<AccountDTO> getAllMemberList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AccountMapper.class).selectAllMember();
		} finally {
			sqlSession.close();
		}
	}
	
	// 일반 회원 조회
	public List<AccountDTO> getMemberList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AccountMapper.class).selectMember();
		} finally {
			sqlSession.close();
		}
	}
	
	// PT 회원 조회
	public List<AccountDTO> getPtMemberList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AccountMapper.class).selectPtMember();
		} finally {
			sqlSession.close();
		}
	}
	
	// ID로 일반 회원 조회
	public AccountDTO getMemberById(String userId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AccountMapper.class).selectMemberById(userId);
		} finally {
			sqlSession.close();
		}
	}
	
	// 이름으로 일반 회원 조회
	public List<AccountDTO> getMemberByNameList(String name) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AccountMapper.class).selectMemberByName(name);
		} finally {
			sqlSession.close();
		}
	}
	
	// 모든 사용자 중 ID로 조회 (로그인)
	public AccountDTO getAccountById(String userId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(AccountMapper.class).selectAccountById(userId);
		} finally {
			sqlSession.close();
		}
	}
	
	// ID 중복 조회
	public boolean existingAccount(String userId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int count = sqlSession.getMapper(AccountMapper.class).selectUserId(userId);
			return (count == 1 ? true : false);
		} finally {
			sqlSession.close();
		}
	}
}
