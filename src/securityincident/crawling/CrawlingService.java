package securityincident.crawling;

import securityincident.model.dao.CrawlingDao;
import securityincident.model.dto.ArticleDto;
import securityincident.model.dto.CrawlingDto;
import securityincident.etc.DBUtil;

import java.sql.Connection;
import java.util.List;

public class CrawlingService {

    // ───────────────────────────────────────────
    // 크롤링 전체 프로세스
    // headless = true  : 스케줄러 자동 실행 (백그라운드)
    // headless = false : 콘솔 수동 실행 (브라우저 창 표시)
    // ───────────────────────────────────────────
    public void executeCrawling(boolean headless) {
        Connection conn = null;
        int savedCount = 0;
        String status = "SUCCESS";
        String message = "";

        System.out.println("========================================");
        System.out.println("[CrawlingService] 크롤링 시작 "
                + java.time.LocalDateTime.now()
                + (headless ? " [자동/백그라운드]" : " [수동]"));
        System.out.println("========================================");

        try {
            conn = DBUtil.getConnection();
            CrawlingDao crawlingDao = new CrawlingDao(conn);
            NewsCrawler crawler = new NewsCrawler();

            // 1단계: 크롤링 실행
            List<ArticleDto> articles = crawler.crawlAll(headless);
            System.out.println("[CrawlingService] 크롤링 수집: " + articles.size() + "건");

            // 2단계: DB 저장 (중복 제외)
            for (ArticleDto dto : articles) {
                try {
                    boolean inserted = crawlingDao.insertArticle(dto);
                    if (inserted) {
                        savedCount++;
                        System.out.println("  [DB 저장] " + dto.getTitle());
                    } else {
                        System.out.println("  [중복 스킵] " + dto.getTitle());
                    }
                } catch (Exception e) {
                    System.err.println("  [DB 오류] " + dto.getTitle() + " → " + e.getMessage());
                }
            }

            message = "수집 " + articles.size() + "건 / 신규 저장 " + savedCount + "건";

        } catch (Exception e) {
            status = "FAIL";
            message = "크롤링 오류: " + e.getMessage();
            System.err.println("[CrawlingService] " + message);
            e.printStackTrace();

        } finally {
            // 3단계: 크롤링 로그 저장
            if (conn != null) {
                try {
                    CrawlingDao logDao = new CrawlingDao(conn);
                    logDao.insertCrawlingLog(new CrawlingDto(status, savedCount, message));
                    conn.close();
                } catch (Exception e) {
                    System.err.println("[로그 저장 오류] " + e.getMessage());
                }
            }
            System.out.println("[CrawlingService] 완료 ─ " + message);
            System.out.println("========================================\n");
        }
    }
}