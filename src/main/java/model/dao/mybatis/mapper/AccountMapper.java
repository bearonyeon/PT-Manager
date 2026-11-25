package model.dao.mybatis.mapper;

import java.util.List;

import model.dto.AccountDTO;

public interface AccountMapper {
	public List<AccountDTO> selectAllMember();
	
	public List<AccountDTO> selectMember();
	
	public List<AccountDTO> selectPtMember();
	
	public AccountDTO selectMemberById(String userId);
	
	public AccountDTO selectMemberByName(String userName);
	
	public AccountDTO selectAccountById(String userId);
	
	public int selectUserId(String userId);
	
	public int insertAccount(AccountDTO account);
	
	public int deleteAccount(String userId);
	
	public int updateAccount(AccountDTO account);
}
