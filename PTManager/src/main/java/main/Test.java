package main;

import service.dto.AccountDTO;

import java.util.Iterator;
import java.util.List;

import persistence.dao.AccountDAO;

public class Test {
    
    public static void main(String[] args) {
        AccountDAO accountDao = new AccountDAO();
                
        System.out.println("회원 관리 테스트\n");

        try {
            //AccountDTO a1 = new AccountDTO("444", "333", "SAM", "sam@gmail.com", "010-3333-3333", "member", 175);
            //int rslt1 = accountDao.insertAccount(a1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            List<AccountDTO> allMemberList = accountDao.getAllMemberList();
            
            System.out.println("조회된 회원 수: " + allMemberList.size());
            
            Iterator<AccountDTO> iter = allMemberList.iterator();
            while (iter.hasNext()) {
                AccountDTO account = iter.next();
                String userId = account.getUserId();
                String name = account.getName();
                String phone = account.getPhone();
                String role = account.getRole();
                double height = account.getHeight();
                System.out.println(userId + ", " + name + ", " + phone + ", " + role + ", " + height);
            }
        }
    }

}
