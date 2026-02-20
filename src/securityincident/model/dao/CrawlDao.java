package securityincident.model.dao;

import java.sql.*;

public class CrawlDao {

    private CrawlDao(){ connect(); }
    private static final CrawlDao instance = new CrawlDao();
    public static CrawlDao getInstance(){ return instance; }

    private String url = "jdbc:mysql://localhost:3306/security_db";
    private String user = "root";
    private String password ="1234";
    private Connection conn;

    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
        }catch(Exception e){
            System.out.println("DB 연결 실패");
        }
    }

    public boolean updateCrawlLog(int crawId, String status, int count, String message){
        try{
            String sql = "UPDATE crawl_log SET crawlingStatus=?, collectedCount=?, message=? WHERE crawId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, count);
            ps.setString(3, message);
            ps.setInt(4, crawId);

            int result = ps.executeUpdate();
            if(result == 1) return true;

        }catch(SQLException e){
            System.out.println("SQL 오류 " + e);
        }
        return false;
    }
}
