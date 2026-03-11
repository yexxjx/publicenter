package main.securityincident.model.dao;

import main.securityincident.model.dto.ArticleDto;
import main.securityincident.model.dto.CrawlingDto;

import java.sql.*;

public class CrawlingDao {

    private final Connection conn;

    public CrawlingDao(Connection conn) {
        this.conn = conn;
    }

    public boolean insertArticle(ArticleDto dto) throws SQLException {
        // URL 기준 중복 체크
        String checkSql = "SELECT COUNT(*) FROM article WHERE articleUrl = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1, dto.getArticleUrl());
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) return false; // 중복
        }

        String sql = "INSERT INTO article " +
                "(companyId, title, content, articleSource, articleDate, articleUrl, approvalStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, 'pending')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dto.getCompanyId());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, dto.getArticleSource());
            ps.setDate(5, new java.sql.Date(dto.getArticleDate().getTime()));
            ps.setString(6, dto.getArticleUrl());
            ps.executeUpdate();
        }
        return true;
    }

    public void insertCrawlingLog(CrawlingDto dto) throws SQLException {
        // DB 테이블명: crawl_log (crawling_log 아님!)
        String sql = "INSERT INTO crawl_log (crawlTime, crawlingStatus, collectedCount, message) " +
                "VALUES (NOW(), ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getCrawlingStatus());
            ps.setInt(2, dto.getCollectedCount());
            ps.setString(3, dto.getMessage());
            ps.executeUpdate();
        }
    }
}