package securityincident.model.dao;

import securityincident.model.dto.CompanyDto;
import securityincident.model.dto.IncidentDto;

import java.sql.*;
import java.util.ArrayList;

public class IncidentDao {

    // 싱글톤
    private IncidentDao() {
        connect();
    }

    private static final IncidentDao instance = new IncidentDao();

    public static IncidentDao getInstance() {
        return instance;
    }

    // 데이터 베이스 연동
    private String url = "jdbc:mysql://localhost:3306/security_db";
    private String user = "root";
    private String password = "1234";

    private Connection conn;

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("데이터베이스 연동 성공");
        } catch (Exception e) {
            System.out.println("데이터베이스 연동 실패");
            e.printStackTrace();
        }
    }

    // 1. 보안사고 관리
    // 보안사고 등록 DAO
    public boolean incidentAddByAdmin(IncidentDto incidentDto) {
        try {
            String sql = "INSERT INTO incident " +
                    "(incidentYear, incidentType, incidentDescription, actionTaken, companyId) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, incidentDto.getIncidentYear());
            ps.setString(2, incidentDto.getIncidentType());
            ps.setString(3, incidentDto.getIncidentDescription());
            ps.setString(4, incidentDto.getActionTaken());
            ps.setInt(5, incidentDto.getCompanyId());

            int result = ps.executeUpdate(); // 1이면 성공, 0이면 실패
            if (result == 1) return true;


        } catch (SQLException e) {
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
    public boolean incidentDelete(int incidentId) {
        try {
            String sql = "delete from incident where incidentid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,incidentId);

            int count = ps.executeUpdate();
            if (count == 1) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("sql 문법 오류" + e);
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

    // * 사고 상세 정보 조회
    public ArrayList<IncidentDto> incidentFindOne(int incidentId) {
        ArrayList<IncidentDto> incidentDtos = new ArrayList<>();
        try {
            String sql = "SELECT i.*, c.companyName, ind.industryName " +
                    "FROM incident i " +
                    "INNER JOIN company c ON i.companyId = c.companyId " +
                    "INNER JOIN industry ind ON c.industryId = ind.industryId " +
                    "WHERE i.incidentId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, incidentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int rsId = rs.getInt("incidentId");
                String companyName = rs.getString("companyName");
                String industryName = rs.getString("industryName");
                String incidentType = rs.getString("incidentType");
                String incidentDate = rs.getString("incidentDate");
                String approvalStatus = rs.getString("approvalStatus");
                String incidentDescription = rs.getString("incidentDescription");
                String actionTaken = rs.getString("actionTaken");
                IncidentDto dto = new IncidentDto(rsId, companyName, industryName, incidentType, incidentDate, approvalStatus, incidentDescription, actionTaken);
                incidentDtos.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생: " + e);
        }
        return incidentDtos;
    }

    // * 기업별 보안 사고 조회
    public ArrayList<IncidentDto> incidentFindByCompany(String companyName) {
        ArrayList<IncidentDto> incidentDtos = new ArrayList<>();
        try {
            String sql = "SELECT i.*, c.companyName, ind.industryName " +
                    "FROM incident i " +
                    "INNER JOIN company c ON i.companyId = c.companyId " +
                    "INNER JOIN industry ind ON c.industryId = ind.industryId " +
                    "WHERE c.companyName = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, companyName.trim());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int incidentId = rs.getInt("incidentId");
                String incidentType = rs.getString("incidentType");
                String incidentDate = rs.getString("incidentDate");
                IncidentDto dto = new IncidentDto(incidentId, incidentType, incidentDate);
                incidentDtos.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생: " + e);
        }
        return incidentDtos;
    }

    // 보안사고수정
    public boolean incidentUpdate(int incidentId, String incidentYear, String incidentDate,
                                  String incidentType, String incidentDescription, String actionTaken){
        try{
            String sql = "UPDATE incident SET incidentYear=?, incidentDate=?, incidentType=?, incidentDescription=?, actionTaken=? WHERE incidentId=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, incidentYear);
            ps.setString(2, incidentDate);
            ps.setString(3, incidentType);
            ps.setString(4, incidentDescription);
            ps.setString(5, actionTaken);
            ps.setInt(6, incidentId);

            int count = ps.executeUpdate();
            if (count == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL 문법 오류" + e);
        }
        return false;
    }

    //연도별 보안 사고 검색
    public ArrayList<IncidentDto> incidentFindByYear(String year) {
        ArrayList<IncidentDto> db = new ArrayList<>();
        try {
            String sql = "SELECT s.incidentId, s.incidentYear, s.incidentDate, " +
                    "s.incidentType, s.incidentDescription, s.actionTaken, " +
                    "s.approvalStatus, s.approvalTime, s.companyId, " +
                    "c.companyName " +
                    "FROM incident s " +
                    "JOIN company c ON s.companyId = c.companyId " +
                    "WHERE s.incidentYear = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, year);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int incidentId = rs.getInt("incidentId");
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
        } catch (SQLException e) {
            System.out.println("SQL 문법 오류");
        }
        return db;
    }

    // 사고 유형 목록 조회
    public ArrayList<String> getIncidentTypeList() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT incidentType FROM incident";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("incidentType"));
            }

        } catch (SQLException e) {
            System.out.println("SQL 오류");
        }
        return list;
    }


    //사고 유형 검색
    public ArrayList<IncidentDto> incidentFindByType(String type) {
        ArrayList<IncidentDto> db = new ArrayList<>();
        try {
            String sql = "SELECT s.incidentId, s.incidentYear, s.incidentDate, " +
                    "s.incidentType, s.incidentDescription, s.actionTaken, " +
                    "s.approvalStatus, s.approvalTime, s.companyId, " +
                    "c.companyName " +
                    "FROM incident s " +
                    "JOIN company c ON s.companyId = c.companyId " +
                    "WHERE s.incidentType = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                db.add(new IncidentDto(
                        rs.getInt("incidentId"),
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

    // 산업군 목록
    public ArrayList<String> getIndustryList() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT industryName FROM industry";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("industryName"));
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류");
        }
        return list;
    }

    // ============================================================
    // 8) 승인 처리 (대기 상태인 것만 승인)
    // ============================================================
    public boolean approveIncident(int incidentId) {
        try {
            String sql =
                    "UPDATE incident " +
                            "SET approvalStatus='승인 완료', approvalTime=NOW() " +
                            "WHERE incidentId=? AND approvalStatus='대기'";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, incidentId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("SQL 오류 " + e);
        }
        return false;
    }

    // ============================================================
    // 9) 자동 사고 등록 (중복 방지 포함)  ✅ 여기만 남김 (중복 제거 완료)
    //    - 같은 회사(companyId)가 같은 날(CURDATE()) 같은 유형(incidentType)이면 추가 생성 안됨
    // ============================================================
    public boolean autoInsertIncident(String incidentYear,
                                      String incidentType,
                                      String description,
                                      int companyId) {

        try {
            String sql =
                    "INSERT INTO incident (incidentYear, incidentDate, incidentType, incidentDescription, approvalStatus, companyId) " +
                            "SELECT ?, CURDATE(), ?, ?, '대기', ? " +
                            "FROM DUAL " +
                            "WHERE NOT EXISTS ( " +
                            "   SELECT 1 FROM incident " +
                            "   WHERE companyId=? AND incidentType=? AND incidentDate=CURDATE() " +
                            ")";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, incidentYear);
            ps.setString(2, incidentType);
            ps.setString(3, description);
            ps.setInt(4, companyId);

            ps.setInt(5, companyId);
            ps.setString(6, incidentType);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("자동 사고 등록 실패: " + e);
            e.printStackTrace();
        }
        return false;
    }

    // 산업군별 검색
    public ArrayList<IncidentDto> incidentFindByIndustry(String industryName) {
        ArrayList<IncidentDto> db = new ArrayList<>();
        try {
            String sql = "SELECT s.incidentId, s.incidentYear, s.incidentDate, " +
                    "s.incidentType, s.incidentDescription, s.actionTaken, " +
                    "s.approvalStatus, s.approvalTime, s.companyId, c.companyName " +
                    "FROM securityIncident s " +
                    "JOIN company c ON s.companyId = c.companyId " +
                    "JOIN industry i ON c.industryId = i.industryId " +
                    "WHERE i.industryName = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, industryName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                db.add(new IncidentDto(
                        rs.getInt("incidentId"),
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
        } catch (SQLException e) {
            System.out.println("SQL 오류");
        }
        return db;
    }

    // 기업별 사고 건수
    public ArrayList<String> statByCompany() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "SELECT c.companyName, COUNT(s.incidentId) AS cnt " +
                    "FROM securityIncident s " +
                    "JOIN company c ON s.companyId = c.companyId " +
                    "GROUP BY c.companyName";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("companyName") + " : " + rs.getInt("cnt") + "건");
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류");
        }
        return list;
    }

    // 연도별 사고 건수
    public ArrayList<String> statByYear() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "SELECT incidentYear, COUNT(incidentId) AS cnt " +
                    "FROM securityIncident GROUP BY incidentYear ORDER BY incidentYear";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("incidentYear") + "년 : " + rs.getInt("cnt") + "건");
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류");
        }
        return list;
    }

    // 유형별 사고 건수
    public ArrayList<String> statByType() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "SELECT incidentType, COUNT(incidentId) AS cnt " +
                    "FROM securityIncident GROUP BY incidentType";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("incidentType") + " : " + rs.getInt("cnt") + "건");
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류");
        }
        return list;
    }

    // ============================================================
    // 10) 승인 대기 목록 조회  ✅ DTO 생성자 호출 수정 완료
    // ============================================================
    public ArrayList<IncidentDto> findPendingIncidents() {
        ArrayList<IncidentDto> list = new ArrayList<>();

        try{
            String sql =
                    "SELECT i.incidentId, c.companyName, i.incidentType, i.incidentDate " +
                            "FROM incident i " +
                            "JOIN company c ON i.companyId = c.companyId " +
                            "WHERE i.approvalStatus='대기' " +
                            "ORDER BY i.incidentDate DESC, i.incidentId DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                // ✅ IncidentDto(int incidentId, String companyName, String incidentType, String incidentDate)
                list.add(new IncidentDto(
                        rs.getInt("incidentId"),
                        rs.getString("companyName"),
                        rs.getString("incidentType"),
                        rs.getString("incidentDate")
                ));
            }

        }catch(SQLException e){
            System.out.println("대기 조회 실패: " + e);
            e.printStackTrace();
        }

        return list;
    }
}