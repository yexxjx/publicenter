package securityincident.model.dao;

import securityincident.model.dto.ArticleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrawlingDao {

    private Connection conn; // JDBC에서 사용하는 DB 연결 객체, 반드시 필요함

    public CrawlingDao(Connection conn) { //외부에서 만든 DB연결을 주입받음(이건 왜 필요한지 모르겠음)
        this.conn = conn;
    }

    /* 기사 저장 */
    public boolean insertArticle(ArticleDto dto) { // 기사 1건을 DB에 저장하는 메서드 //성공하면 true, 실패하면 false
        // 실행할 SQL 문
        // ?는 나중에 값이 들어갈 자리
        // now() -> DB 서버 시간 기준 저장일
        String sql = """
            INSERT INTO news_article
            (company_id, title, content, article_source, article_date, created_at)
            VALUES (?, ?, ?, ?, ?, NOW())
        """;

        // SQL을 DB에 전달한 준비
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.getCompanyId()); //첫번째 ? 자리에 companyId값 세팅
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, dto.getArticleSource());
            ps.setDate(5, dto.getArticleDate()); //기사 날짜를 Date 타입으로 저장

            // SQL 실행, 1행이 INSERT되면 성공 -> true
            return ps.executeUpdate() == 1;
            // 에러 발생 시 로그 출력
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* 크롤링 실행 로그 저장 */
    // 크롤링 실행 결과 1건을 로그 테이블에 저장
    public void insertCrawlingLog(CrawlingLogDto dto) {
        // 크롤링 실행 시각 + 결과 저장
        String sql = """
            INSERT INTO crawling_log
            (crawl_time, crawling_status, collected_count, message)
            VALUES (NOW(), ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getCrawlingStatus()); //SUCESS / FAIL / PRATIAL 같은 상태
            ps.setInt(2, dto.getCollectedCount());   // 몇건 수집했는지
            ps.setString(3, dto.getMessage());       // 결과 메시지(에러 원인 등)

            ps.executeUpdate(); // 로그 저장 실행

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
