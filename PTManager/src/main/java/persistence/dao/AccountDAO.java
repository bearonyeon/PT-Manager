package persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.util.JDBCUtil;
import service.dto.AccountDTO;

public class AccountDAO {
    private JDBCUtil jdbcUtil = null; // JDBCUtil 필드 선언
    
    public AccountDAO() { // 생성자
        jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
    }
    
    // 회원(일반, PT 전체) 리스트
    public List<AccountDTO> getAllMemberList() {
        List<AccountDTO> allMemberList = new ArrayList<AccountDTO>();
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT userid, name, email, phone, role, height "); // 비밀번호 제외
        query.append("FROM account2 ");
        query.append("WHERE role IN ('MEMBER', 'PTMEMBER')");
        
        jdbcUtil.setSql(query.toString());
        
        try {
            ResultSet rs = null;
            rs = jdbcUtil.executeQuery();
            
            if (rs.next()) {
                do {
                    String userid = rs.getString("userid");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String role = rs.getString("role");
                    double height = rs.getDouble("height");
                    
                    AccountDTO account = new AccountDTO(userid, name, email, phone, role, height);
                    
                    allMemberList.add(account);
                } while (rs.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return allMemberList;
    }
    
    // 일반 회원 리스트
    public List<AccountDTO> getMemberList() {
        List<AccountDTO> memberList = new ArrayList<AccountDTO>();
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT userid, name, email, phone, role, height "); // 비밀번호 제외
        query.append("FROM account2 ");
        query.append("WHERE role = 'MEMBER'");
        
        jdbcUtil.setSql(query.toString());
        
        try {
            ResultSet rs = null;
            rs = jdbcUtil.executeQuery();
            
            if (rs.next()) {
                do {
                    String userid = rs.getString("userid");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String role = rs.getString("role");
                    double height = rs.getDouble("height");
                    
                    AccountDTO account = new AccountDTO(userid, name, email, phone, role, height);
                    
                    memberList.add(account);
                } while (rs.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return memberList;
    }
    
    // PT 회원 리스트
    public List<AccountDTO> getPtMemberList() {
        List<AccountDTO> ptMemberList = new ArrayList<AccountDTO>();
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT userid, name, email, phone, role, height "); // 비밀번호 제외
        query.append("FROM account2 ");
        query.append("WHERE role = 'PTMEMBER'");
        
        jdbcUtil.setSql(query.toString());
        
        try {
            ResultSet rs = null;
            rs = jdbcUtil.executeQuery();
            
            if (rs.next()) {
                do {
                    String userid = rs.getString("userid");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String role = rs.getString("role");
                    double height = rs.getDouble("height");
                    
                    AccountDTO account = new AccountDTO(userid, name, email, phone, role, height);
                    
                    ptMemberList.add(account);
                } while (rs.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return ptMemberList;
    }
    
    // id로 회원 검색 (있으면 AccountDTO, 없으면 null 반환)
    public AccountDTO getMemberById(String userid) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT userid, name, email, phone, role, height ");
        query.append("FROM account2 ");
        query.append("WHERE userid = ? AND role = 'MEMBER'");
        
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {userid});
        
        try {
            ResultSet rs = null;
            AccountDTO account = null;
            
            rs = jdbcUtil.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");
                double height = rs.getDouble("height");
                
                account = new AccountDTO(userid, name, email, phone, role, height);
            }
            
            return account;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return null;
    }
    
    // 회원 이름으로 검색(여러 명 출력 가능)
    public List<AccountDTO> getMemberByNameList(String name) {
        List<AccountDTO> memberList = new ArrayList<AccountDTO>();
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT userid, name, email, phone, role, height "); // 비밀번호 제외
        query.append("FROM account2 ");
        query.append("WHERE name = ? AND role = 'MEMBER'");
        
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {name});
        
        try {
            ResultSet rs = null;
            rs = jdbcUtil.executeQuery();
            
            if (rs.next()) {
                do {
                    String userid = rs.getString("userid");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String role = rs.getString("role");
                    double height = rs.getDouble("height");
                    
                    AccountDTO account = new AccountDTO(userid, name, email, phone, role, height);
                    
                    memberList.add(account);
                } while (rs.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return memberList;
    }
    
    // 아이디로 회원 조회
    public AccountDTO getAccountById(String userid) {
        String query = "SELECT userid, name, email, phone, role, height FROM account2 WHERE userid = ?";
        
        jdbcUtil.setSqlAndParameters(query, new Object[] {userid});
        
        try {
            ResultSet rs = null;
            rs = jdbcUtil.executeQuery();
            
            if (rs.next()) {
                AccountDTO account = null;
                
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");
                double height = rs.getDouble("height");
                
                account = new AccountDTO(userid, name, email, phone, role, height);
                
                return account;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return null;
    }
    
    // 아이디 중복 확인 (0이면 사용 가능, 아니면 이미 존재하는 아이디)
    public int checkUserId(String userid) {
        int result = 0;
        
        String query = "SELECT COUNT(*) FROM account2 WHERE userid = ?";
        
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {userid});
        
        try {
            ResultSet rs = null;
            rs = jdbcUtil.executeQuery();
            
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
    
    // 회원가입
    public int insertAccount(AccountDTO account) {
        int result = 0;
        
        // 아이디 중복 확인
        int check = checkUserId(account.getUserId());
        
        if (check > 0) {
            System.out.println("이미 존재하는 아이디입니다.");
            return 0;
        }
        
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO account2 (userid, password, name, email, phone, role, height) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
        
        Object[] param = new Object[] {account.getUserId(), account.getPassword(), account.getName(), account.getEmail(), account.getPhone(), account.getRole(), account.getHeight()};
        
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
    
    // 회원 탈퇴
    public int deleteAccount(String userid) {
        int result = 0;
        
        String query = "DELECT FROM account2 WHERE userid = ?";
        
        jdbcUtil.setSqlAndParameters(query, new Object[] {userid});
        
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
    
    // 회원 정보 수정
    public int updateAccount(AccountDTO account) {
        int result = 0;
        
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE account SET ");
        sql.append("password = ?, ");
        sql.append("name = ?, ");
        sql.append("email = ?, ");
        sql.append("phone = ?, ");
        sql.append("height = ? ");
        sql.append("WHERE userid = ?");
        
        Object[] param = new Object[] {account.getPassword(), account.getName(), account.getEmail(), account.getPhone(), account.getHeight(), account.getUserId()};
        
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


}
