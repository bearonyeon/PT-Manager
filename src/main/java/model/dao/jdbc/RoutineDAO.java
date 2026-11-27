package model.dao.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.dto.RoutineDTO;
import model.dto.RoutineItemDTO;
import model.dto.RoutinePlanDTO;

public class RoutineDAO {
	private JDBCUtil jdbcUtil = null;   // JDBCUtil 필드 선언
    
    public RoutineDAO() {               // 생성자 
        jdbcUtil = new JDBCUtil();      // JDBCUtil 객체 생성
    }
    
    /* 루틴 정보 */
    public RoutineDTO findRoutineById(int routineId) { 
        StringBuffer query = new StringBuffer();
        query.append("SELECT \"routineId\", \"routineName\", \"createdDate\", \"creatorName\", \"note\" ");
        query.append("FROM \"RoutineDetail\" ");
        query.append("WHERE \"routineId\" = ? ");
        
        // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {routineId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//            if (!rs.next())
//				해당 루틴이 테이블에 존재하지 않습니다.
        	RoutineDTO r = new RoutineDTO();
            // 검색 결과 존재
            if (rs.next()) {
                r.setRoutineId(routineId);
            	r.setRoutineName(rs.getString("routineName"));
            	r.setCreatedDate(rs.getDate("createdDate"));
            	r.setCreatorName(rs.getString("creatorName"));
            	r.setNote(rs.getString("note"));
            }
            	
            return r;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    } 
    
    /* 루틴의 아이템 */
    public List<RoutineItemDTO> getAllRoutineItems(int routineId) {
    	StringBuffer query = new StringBuffer();
        query.append("SELECT ri.\"routineItemId\", e.\"eName\", LISTAGG(bp.\"bName\", ',') WITHIN GROUP(ORDER BY bp.\"bName\") AS \"bNames\" ");
        query.append("FROM \"RoutineItem\" ri ");
        query.append("JOIN \"Exercise\" e ON ri.\"exerciseId\" = e.\"exerciseId\" ");
        query.append("LEFT JOIN \"ExerciseTarget\" et ON e.\"exerciseId\" = et.\"exerciseId\" ");
        query.append("LEFT JOIN \"BodyPart\" bp ON et.\"bodyPartId\" = bp.\"bodyPartId\" ");
        query.append("WHERE ri.\"routineId\" = ? ");
        query.append("GROUP BY ri.\"routineItemId\", ri.\"sequence\", e.\"eName\" ");
        query.append("ORDER BY ri.\"sequence\" ASC");
        // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {routineId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//          if (!rs.next())
//				해당 루틴의 아이템이 테이블에 존재하지 않습니다.
            rs = jdbcUtil.executeQuery();
            List<RoutineItemDTO> itemList = new ArrayList<>();
            while (rs.next()) {
            	RoutineItemDTO item = new RoutineItemDTO(rs.getString("eName"));
            	
                List<String> bodyPartList = new ArrayList<>();
            	String[] parts = rs.getString("bNames").split(",");
            	for (String part: parts) {
                	bodyPartList.add(part);
            	}
            	item.setBNames(bodyPartList);
            	itemList.add(item);
            }

            return itemList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    /* 루틴의 플랜 - 배정 아이템 */
    public List<RoutinePlanDTO> getAllRoutinePlans(int routineId) {
    	StringBuffer query = new StringBuffer();
        query.append("SELECT ri.\"routineItemId\", e.\"eName\", LISTAGG(bp.\"bName\", ',') WITHIN GROUP(ORDER BY bp.\"bName\") AS \"bNames\" ");
        query.append(", rip.\"targetWeight\", rip.\"targetReps\", rip.\"targetSets\" ");
        query.append("FROM \"RoutineItem\" ri ");
        query.append("JOIN \"Exercise\" e ON ri.\"exerciseId\" = e.\"exerciseId\" ");
        query.append("JOIN \"RoutineItemPlan\" rip ON ri.\"routineItemId\" = rip.\"routineItemId\" ");
        query.append("LEFT JOIN \"ExerciseTarget\" et ON e.\"exerciseId\" = et.\"exerciseId\" ");
        query.append("LEFT JOIN \"BodyPart\" bp ON et.\"bodyPartId\" = bp.\"bodyPartId\" ");
        query.append("WHERE ri.\"routineId\" = ? ");
        query.append("GROUP BY ri.\"routineItemId\", ri.\"sequence\", \"eName\", rip.\"targetWeight\", rip.\"targetReps\", rip.\"targetSets\" ");
        query.append("ORDER BY ri.\"sequence\" ASC");
        // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {routineId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//          if (!rs.next())
//				해당 루틴의 아이템이 테이블에 존재하지 않습니다.
            rs = jdbcUtil.executeQuery();
            List<RoutinePlanDTO> planList = new ArrayList<>();
            while (rs.next()) {
            	RoutinePlanDTO plan = new RoutinePlanDTO();
            	plan.setEName(rs.getString("eName"));
            	plan.setTargetWeight(rs.getDouble("targetWeight"));
            	plan.setTargetReps(rs.getInt("targetReps"));
            	plan.setTargetSets(rs.getInt("targetSets"));
            	
            	List<String> bodyPartList = new ArrayList<>();
            	String[] parts = rs.getString("bNames").split(",");
            	for (String part: parts) {
                	bodyPartList.add(part);
            	}
            	plan.setBNames(bodyPartList);
            	planList.add(plan);
            }

            return planList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    /* 배정된 루틴 리스트 */
    public List<RoutineDTO> getAllRoutinesByMember(String memeberId) {
    	StringBuffer query = new StringBuffer();
        query.append("SELECT \"routineId\", \"routineName\", \"createdDate\", \"creatorName\", \"note\" ");
        query.append("FROM \"RoutineDetail\" ");
        query.append("WHERE \"memberId\" = ? ");
        
        // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {memeberId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//          if (!rs.next())
//				해당하는 루틴리스트가 테이블에 존재하지 않습니다.
            // 검색 결과 존재
            List<RoutineDTO> routines = new ArrayList<>();
            while (rs.next()) {
            	RoutineDTO r = new RoutineDTO();
                r.setRoutineId(rs.getInt("routineId"));
            	r.setRoutineName(rs.getString("routineName"));
            	r.setCreatedDate(rs.getDate("createdDate"));
            	r.setCreatorName(rs.getString("creatorName"));
            	r.setNote(rs.getString("note"));
            	
            	routines.add(r);
            }
            	
            return routines;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    /* 생성한 루틴 리스트 */
    public List<RoutineDTO> getAllRoutinesByCreator(String creatorId) {
    	StringBuffer query = new StringBuffer();
        query.append("SELECT \"routineId\", \"routineName\", \"createdDate\", \"creatorName\", \"note\" ");
        query.append("FROM \"RoutineDetail\" ");
        query.append("WHERE \"creatorId\" = ? ");
        
        // jdbcUtil.setSqlAndParameters(...);       // JDBCUtil에 질의문과 파라미터 설정   
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[] {creatorId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
//          if (!rs.next())
//				해당하는 루틴리스트가 테이블에 존재하지 않습니다.
        	
            // 검색 결과 존재
            List<RoutineDTO> routines = new ArrayList<>();
            while (rs.next()) {
            	RoutineDTO r = new RoutineDTO();
                r.setRoutineId(rs.getInt("routineId"));
            	r.setRoutineName(rs.getString("routineName"));
            	r.setCreatedDate(rs.getDate("createdDate"));
            	r.setCreatorName(rs.getString("creatorName"));
            	r.setNote(rs.getString("note"));
            	
            	routines.add(r);
            }
            	
            return routines;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
}
