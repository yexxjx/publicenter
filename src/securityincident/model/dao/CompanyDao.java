package securityincident.model.dao;
// 작업자 이연지, 이태현,
import securityincident.model.dto.CompanyDto;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDao {
    private CompanyDao(){connect();}
    private static final CompanyDao instance = new CompanyDao();
    public static CompanyDao getInstance(){return instance;}

    private String url = "jdbc:mysql://localhost:3306/crawlerDB";
    private String user = "root";   private String password = "1234";
    private Connection conn;
    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection( url , user , password );
        }catch (Exception e){ System.out.println("[경고] 데이터베이스 연동 실패: 관리자에게 문의"); }
    }

    // * 기업 전체 조회
    public ArrayList<CompanyDto> companyFindAll(){
        ArrayList<CompanyDto> companyDtos = new ArrayList<>();
        try{
            String sql = "SELECT*FROM crawlerDB";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int companyId = rs.getInt("companyId");
                String companyName = rs.getString("companyName");
                String headOffice = rs.getString("headOffice");
                int foundedYear = rs.getInt("foundedYear");
                String createdAt = rs.getString("createdAt");
                int industryId  = rs.getInt("industryId");
                CompanyDto companyDto = new CompanyDto(companyId,companyName,headOffice,foundedYear,createdAt,industryId);
                companyDtos.add(companyDto);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생: "+e);
        }
        return companyDtos;
    }


}
