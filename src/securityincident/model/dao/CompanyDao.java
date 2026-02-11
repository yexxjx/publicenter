package securityincident.model.dao;

import securityincident.model.dto.CompanyDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyDao {
    private static final CompanyDao instance = new CompanyDao();
    public static CompanyDao getInstance() { return instance; }

    private String url = "jdbc:mysql://localhost:3306/crawlerDB";
    private String user = "root";
    private String password = "1234";
    private Connection conn;

    private CompanyDao() {connect();}


    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("[준비] 데이터베이스 연동 성공");
        } catch(Exception e) {
            // [여기를 고치세요!] e.getMessage()를 찍어야 진짜 이유를 압니다.
            System.out.println("[경고] 연동 실패 원인: " + e.getMessage());
        }
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

    // 기업 등록
    public boolean companyAdd(String companyName, String headOffice, int foundedYear, int industryId) {
        try {
            String sql = "insert into company(companyName, headOffice, foundedYear, industryId, createdAt) " +
                    "values(?, ?, ?, ?, now())";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, companyName);
            ps.setString(2, headOffice);
            ps.setInt(3, foundedYear);
            ps.setInt(4, industryId);

            int count= ps.executeUpdate();
            if(count==1){return true;}
            else{return false;}
        } catch (SQLException e) {
            System.out.println("[시스템오류] 등록 실패: " + e.getMessage());
            return false;
        }
    }

    // 기업 수정
    public boolean companyUpdate(int cno, int companyId, String companyName, String headOffice, int foundedYear, String createdAt, int industryId) {
        try {
            String sql = "update company set companyName=?, headOffice=?, foundedYear=?, industryId=? where companyId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, companyName);
            ps.setString(2, headOffice);
            ps.setInt(3, foundedYear);
            ps.setInt(4, industryId);
            ps.setInt(5, cno);
            return ps.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("[시스템오류] SQL 문법 문제 발생"+e);}
        return false;
    }
}
