package model.dao;

import model.dto.ArticleDto;
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

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.getCompanyId());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, dto.getArticleSource());
            ps.setDate(5, dto.getArticleDate());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* 크롤링 실행 로그 저장 */
    public void insertCrawlingLog(CrawlingDto dto) {

        String sql = """
            INSERT INTO crawling_log
            (crawl_time, crawling_status, collected_count, message)
            VALUES (NOW(), ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getCrawlingStatus());
            ps.setInt(2, dto.getCollectedCount());
            ps.setString(3, dto.getMessage());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
