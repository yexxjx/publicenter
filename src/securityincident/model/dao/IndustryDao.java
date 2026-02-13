package securityincident.model.dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import securityincident.model.dto.IndustryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IndustryDao {
    private IndustryDao(){}
    private static final IndustryDao instance=new IndustryDao();
    public static IndustryDao getInstance(){return instance;}

    private String url = "jdbc:mysql://localhost:3306/crawlerDB";
    private String user = "root";
    private String password = "1234";
    private Connection conn;

    // 산업군 전체 조회
    public ArrayList<IndustryDto> industryFindAll(){
        ArrayList<IndustryDto> industryDtos = new ArrayList<>();
        try{
            String sql = "select*from industry";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int ino=rs.getInt("ino");
                int industryId = rs.getInt("industryId");
                String industryName = rs.getString("industryName");
                IndustryDto industryDto = new IndustryDto(ino, industryId, industryName);
                industryDtos.add(industryDto);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생: "+e);
        } return industryDtos;
    }

    // 산업군 등록
    public boolean industryAdd(int industryId, String industryName){
        try{
            String sql="insert into industry(industryId, industryName) values(?.?)";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1, industryId);
            ps.setString(2 ,industryName);
            int count=ps.executeUpdate();
            if(count==1){return true;}
            else{return false;}
        }catch(Exception e){
            System.out.println("[시스템오류] SQL 문법 문제 발생:"+e);
        } return false;
    }

    // 산업군 수정
    public boolean industryUpdate(int ino, int industryId, String industryName){
        try{
            String sql="update industry set industryId=?, industryName=? where ino=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1, ino);
            ps.setInt(2, industryId);
            ps.setString(3, industryName);
            int count=ps.executeUpdate();
            if(count==1){return true;}
            else{return false;}
        }catch(Exception e){
            System.out.println("[시스템오류] SQL 문법 문제 발생"+e);
        } return false;
    }


    // 산업군 삭제
    public boolean industryDelete(int ino){
        try{
            String sql="delete from industry where ino=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setInt(1, ino);
            int count=ps. executeUpdate();
            if(count==1){return true;}
            else{return false;}
        }catch (Exception e){
            System.out.println("[시스템오류] SQL 문법 문제 발생"+e);
        } return false;
    }

}
