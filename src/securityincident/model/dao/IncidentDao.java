package securityincident.model.dao;

import securityincident.model.dto.CompanyDto;
import securityincident.model.dto.IncidentDto;

import java.sql.*;
import java.util.ArrayList;

public class IncidentDao {

    // 싱글톤 - 이한승
    private IncidentDao(){connect();}
    private static final IncidentDao instance = new IncidentDao();
    public static IncidentDao getInstance(){
        return instance;
    }

    // 데이터 베이스 연동
    private String url = "jdbc:mysql://localhost:3306/security_db";
    private String user = "root";
    private String password ="1234";

    private Connection conn;

    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("데이터베이스 연동 성공");
        }catch (Exception e){
            System.out.println("데이터베이스 연동 실패");
        }
    }

    // 1. 보안사고 관리
    // 보안사고 등록 DAO
    public boolean incidentAddByAdmin(IncidentDto incidentDto){
        try{
            String sql = "INSERT INTO securityIncident " +
                    "(incidentYear, incidentType, incidentDescription, actionTaken, companyId) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, incidentDto.getIncidentYear());
            ps.setString(2, incidentDto.getIncidentType());
            ps.setString(3, incidentDto.getIncidentDescription());
            ps.setString(4, incidentDto.getActionTaken());
            ps.setInt(5, incidentDto.getCompanyId());

            int result = ps.executeUpdate(); // 1이면 성공, 0이면 실패
            if(result == 1) return true;


        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // 이름 존재하는지 확인
    public int getCompanyIdByName(String companyName) {
        try {
            String sql = "SELECT companyId FROM company WHERE companyName = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, companyName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("companyId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 보안사고 삭제
    public boolean incidentDelete(int incidentId){
        try{
            String sql ="delete from securityIncident where incidentid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,incidentId);

            int count = ps.executeUpdate();
            if(count==1){return true;}

        }catch (SQLException e){
            System.out.println("sql 문법 오류"+e);
        }
        return false;
    }

    // * 보안사고 전체 조회
    public ArrayList<IncidentDto> incidentFindAll(){
        ArrayList<IncidentDto> incidentDtos = new ArrayList<>();
        try{
            String sql = "SELECT i.*, c.companyName, ind.industryName " +
                "FROM incident i " +
                "LEFT JOIN company c ON i.companyId = c.companyId " +
                "LEFT JOIN industry ind ON c.industryId = ind.industryId;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int incidentId = rs.getInt("incidentId");
                String companyName = rs.getString("companyName");
                String industryName = rs.getString("industryName");
                String incidentType = rs.getString("incidentType");
                String incidentDate = rs.getString("incidentDate");
                String approvalStatus = rs.getString("approvalStatus");
                IncidentDto incidentDto = new IncidentDto(incidentId, companyName, industryName, incidentType, incidentDate, approvalStatus);
                incidentDtos.add(incidentDto);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생: "+e);
        }
            return incidentDtos;
    }

    // 보안사고수정
    public boolean incidentUpdate(int incidentId, String incidentYear, String incidentDate,
                                  String incidentType, String incidentDescription, String actionTaken){
        try{
            String sql = "UPDATE securityIncident SET incidentYear=?, incidentDate=?, incidentType=?, incidentDescription=?, actionTaken=? WHERE incidentId=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, incidentYear);
            ps.setString(2, incidentDate);
            ps.setString(3, incidentType);
            ps.setString(4, incidentDescription);
            ps.setString(5, actionTaken);
            ps.setInt(6, incidentId);

            int count = ps.executeUpdate();
            if(count==1){return true;}
        }catch (SQLException e){
            System.out.println("SQL 문법 오류"+e);
        }
        return false;
    }

    //연도별 보안 사고 검색
    public ArrayList<IncidentDto> incidentFindByYear(String year){
        ArrayList<IncidentDto>db = new ArrayList<>();
        try {
            String sql = "SELECT s.incidentId, s.incidentYear, s.incidentDate, " +
                    "s.incidentType, s.incidentDescription, s.actionTaken, " +
                    "s.approvalStatus, s.approvalTime, s.companyId, " +
                    "c.companyName " +
                    "FROM securityIncident s " +
                    "JOIN company c ON s.companyId = c.companyId " +
                    "WHERE s.incidentYear = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,year);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                long incidentId = rs.getLong("incidentId");
                String incidentYear = rs.getString("incidentYear");
                String incidentDate = rs.getString("incidentDate");
                String incidentType = rs.getString("incidentType");
                String incidentDescription = rs.getString("incidentDescription");
                String actionTaken = rs.getString("actionTaken");
                String approvalStatus = rs.getString("approvalStatus");
                String approvalTime = rs.getString("approvalTime");
                int companyId = rs.getInt("companyId");
                String companyName = rs.getString("companyName");

                IncidentDto incidentDto = new IncidentDto(
                        incidentId,
                        incidentYear,
                        incidentDate,
                        incidentType,
                        incidentDescription,
                        actionTaken,
                        approvalStatus,
                        approvalTime,
                        companyId,
                        companyName

                );

                db.add(incidentDto);
            } // while end
        }catch (SQLException e){
            System.out.println("SQL 문법 오류");
        }
        return db;
    }

    // 사고 유형 목록 조회
    public ArrayList<String> getIncidentTypeList(){
        ArrayList<String> list = new ArrayList<>();
        try{
            String sql = "SELECT DISTINCT incidentType FROM securityIncident";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(rs.getString("incidentType"));
            }

        }catch(SQLException e){
            System.out.println("SQL 오류");
        }
        return list;
    }


    //사고 유형 검색
    public ArrayList<IncidentDto> incidentFindByType(String type){
        ArrayList<IncidentDto> db = new ArrayList<>();
        try{
            String sql = "SELECT s.incidentId, s.incidentYear, s.incidentDate, " +
                    "s.incidentType, s.incidentDescription, s.actionTaken, " +
                    "s.approvalStatus, s.approvalTime, s.companyId, " +
                    "c.companyName " +
                    "FROM securityIncident s " +
                    "JOIN company c ON s.companyId = c.companyId " +
                    "WHERE s.incidentType = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                db.add(new IncidentDto(
                        rs.getLong("incidentId"),
                        rs.getString("incidentYear"),
                        rs.getString("incidentDate"),
                        rs.getString("incidentType"),
                        rs.getString("incidentDescription"),
                        rs.getString("actionTaken"),
                        rs.getString("approvalStatus"),
                        rs.getString("approvalTime"),
                        rs.getInt("companyId"),
                        rs.getString("companyName")
                ));
            }
        }catch(SQLException e){
            System.out.println("SQL 오류");
        }
        return db;
    }








    // 산업군별 검색



}// class end