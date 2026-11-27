package model.dao.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.dto.PtContractDTO;

public class PtContractDAO {
private JDBCUtil jdbcUtil = null;   // JDBCUtil 필드 선언
    
    public PtContractDAO() {               // 생성자 
        jdbcUtil = new JDBCUtil();      // JDBCUtil 객체 생성
    }
    /* pt 정보 */
    public PtContractDTO findPtContractById(int ptContractId) {
    	StringBuffer query = new StringBuffer();
        query.append("SELECT \"personalTrainingId\" AS \"ptContractId\", \"dayOfTheWeek\", \"trainingAt\", \"note\", \"status\", ");
        query.append("\"tName\", \"tPhone\", \"mName\", \"mPhone\" ");
        query.append("FROM \"PtContractDetail\" ");
        query.append("WHERE \"personalTrainingId\" = ?");
        
     // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {ptContractId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//            if (!rs.next())
//				해당 ptContract가 테이블에 존재하지 않습니다.
            PtContractDTO pt = new PtContractDTO();
            // 검색 결과 존재
            if (rs.next()) {
            	pt.setPtContractId(rs.getInt("ptContractId"));
            	pt.setDayOfTheWeek(rs.getString("dayOfTheWeek"));
            	pt.setTrainingAt(rs.getTimestamp("trainingAt").toLocalDateTime());
            	pt.setNote(rs.getString("note"));
            	pt.setStatus(rs.getString("status"));
            	pt.setTName(rs.getString("tName"));
            	pt.setTPhone(rs.getString("tPhone"));
            	pt.setMName(rs.getString("mName"));
            	pt.setMPhone(rs.getString("mPhone"));
            }
            	
            return pt;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    /* 트레이너의 pt 리스트 */
    public List<PtContractDTO> getAllContractsByTrainer(String trainerId) {
    	StringBuffer query = new StringBuffer();
        query.append("SELECT \"personalTrainingId\" AS \"ptContractId\", \"dayOfTheWeek\", \"trainingAt\", \"note\", \"status\", ");
        query.append("\"tName\", \"tPhone\", \"mName\", \"mPhone\" ");
        query.append("FROM \"PtContractDetail\" ");
        query.append("WHERE \"trainerId\" = ?");
        // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {trainerId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//          if (!rs.next())
//				해당 trainer의 ptContract가 테이블에 존재하지 않습니다.
            List<PtContractDTO> contractList = new ArrayList<>();
            while (rs.next()) {
                PtContractDTO pt = new PtContractDTO();
            	pt.setPtContractId(rs.getInt("ptContractId"));
            	pt.setDayOfTheWeek(rs.getString("dayOfTheWeek"));
            	pt.setTrainingAt(rs.getTimestamp("trainingAt").toLocalDateTime());
            	pt.setNote(rs.getString("note"));
            	pt.setStatus(rs.getString("status"));
            	pt.setTName(rs.getString("tName"));
            	pt.setTPhone(rs.getString("tPhone"));
            	pt.setMName(rs.getString("mName"));
            	pt.setMPhone(rs.getString("mPhone"));
            	
            	contractList.add(pt);
            }

            return contractList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    /* member의 pt 리스트 */
    public List<PtContractDTO> getAllContractsByMember(String memberId) {
    	StringBuffer query = new StringBuffer();
        query.append("SELECT \"personalTrainingId\" AS \"ptContractId\", \"dayOfTheWeek\", \"trainingAt\", \"note\", \"status\", ");
        query.append("\"tName\", \"tPhone\", \"mName\", \"mPhone\" ");
        query.append("FROM \"PtContractDetail\" ");
        query.append("WHERE \"memberId\" = ?");
        // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {memberId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//          if (!rs.next())
//				해당 member의 ptContract가 테이블에 존재하지 않습니다.
            List<PtContractDTO> contractList = new ArrayList<>();
            while (rs.next()) {
                PtContractDTO pt = new PtContractDTO();
            	pt.setPtContractId(rs.getInt("ptContractId"));
            	pt.setDayOfTheWeek(rs.getString("dayOfTheWeek"));
            	pt.setTrainingAt(rs.getTimestamp("trainingAt").toLocalDateTime());
            	pt.setNote(rs.getString("note"));
            	pt.setStatus(rs.getString("status"));
            	pt.setTName(rs.getString("tName"));
            	pt.setTPhone(rs.getString("tPhone"));
            	pt.setMName(rs.getString("mName"));
            	pt.setMPhone(rs.getString("mPhone"));
            	
            	contractList.add(pt);
            }

            return contractList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

}
