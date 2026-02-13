package securityincident.model.dao;

import securityincident.model.dto.IncidentDto;

import java.sql.*;

public class IncidentDao {
    private IncidentDao(){connect();}
    private static final IncidentDao instance = new IncidentDao();
    public static IncidentDao getInstance(){return instance;}

    private String url = "jdbc:mysql://localhost:3306/crawlerDB";
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

//    // 1. 보안사고 관리
//    // 1.1. 보안사고 등록 DAO - 이한승
//    public boolean incidentAddByAdmin(IncidentDto incidentDto){
//        try{
//            String sql = "INSERT INTO securityIncident " +
//                    "(incidentYear, incidentDate, incidentType, " +
//                    "incidentDescription, actionTaken, approvalStatus, companyId) " +
//                    "VALUES (?, NOW(), ?, ?, ?, 'PENDING', ?)";
//
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//
//        }catch (SQLException e){
//
//        }
//    }

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

}
