package main.securityincident.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class StatsDao {
    private StatsDao(){}
    private static final StatsDao instance = new StatsDao();
    public static StatsDao getInstance(){return instance;}

    // 데이터 베이스 연동
    private String url = "jdbc:mysql://localhost:3306/security_db";
    private String user = "root";
    private String password ="1234";

    private Connection conn;

    private void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("데이터베이스 연동 성공");
        }catch (Exception e){
            System.out.println("데이터베이스 연동 실패");
            e.printStackTrace();
        }
    }



}
