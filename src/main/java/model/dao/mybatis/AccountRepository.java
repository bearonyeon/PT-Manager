package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;

// 사용자 계정 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스

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
	
	
}
