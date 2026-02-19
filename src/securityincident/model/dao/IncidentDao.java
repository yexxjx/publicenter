package securityincident.model.dao;

import securityincident.model.dto.IncidentDto;
import java.sql.*;

public class IncidentDao {

    // 싱글톤
    private IncidentDao(){connect();}
    private static final IncidentDao instance = new IncidentDao();
    public static IncidentDao getInstance(){
        return instance;
    }

    // DB 연동 정보
    private String url = "jdbc:mysql://localhost:3306/security_db";
    private String user = "root";
    private String password ="1234";

    private Connection conn;

    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("[DB] 데이터베이스 연동 성공");
        }catch (Exception e){
            System.out.println("[DB] 데이터베이스 연동 실패: " + e.getMessage());
        }
    }

    // 1. 보안사고 등록 DAO (Dto 필드명에 맞게 수정)
    public boolean incidentAddByAdmin(IncidentDto incidentDto){
        try{
            // IncidentDto의 필드와 article 테이블 구조를 매핑
            String sql = "INSERT INTO article " +
                    "(companyId, title, content, articleSource, articleDate, createdAt) " +
                    "VALUES (?, ?, ?, ?, NOW(), NOW())";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, incidentDto.getCompanyId());
            // getTitle() 대신 getIncidentType()이나 Description을 제목으로 활용
            ps.setString(2, incidentDto.getIncidentType());
            ps.setString(3, incidentDto.getIncidentDescription());
            ps.setString(4, "관리자 입력");

            return ps.executeUpdate() == 1;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

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
}