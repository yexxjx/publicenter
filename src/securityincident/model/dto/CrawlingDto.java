package securityincident.model.dto;

import java.sql.Timestamp;

public class CrawlingDto {

    private int crawId;
    private Timestamp crawlTime;
    private String crawlingStatus;  // "SUCCESS" or "FAIL"
    private int collectedCount;
    private String message;

    // ── 기본 생성자 ──────────────────────────────
    public CrawlingDto() {}

    // ── 로그 저장용 생성자 (crawId, crawlTime은 DB AUTO) ──
    public CrawlingDto(String crawlingStatus, int collectedCount, String message) {
        this.crawlingStatus = crawlingStatus;
        this.collectedCount = collectedCount;
        this.message = message;
    }

    // ── 전체 생성자 ──────────────────────────────
    public CrawlingDto(int crawId, Timestamp crawlTime,
                       String crawlingStatus, int collectedCount, String message) {
        this.crawId = crawId;
        this.crawlTime = crawlTime;
        this.crawlingStatus = crawlingStatus;
        this.collectedCount = collectedCount;
        this.message = message;
    }

    // ── Getter & Setter ──────────────────────────
    public int getCrawId() { return crawId; }
    public void setCrawId(int crawId) { this.crawId = crawId; }

    public Timestamp getCrawlTime() { return crawlTime; }
    public void setCrawlTime(Timestamp crawlTime) { this.crawlTime = crawlTime; }

    public String getCrawlingStatus() { return crawlingStatus; }
    public void setCrawlingStatus(String crawlingStatus) { this.crawlingStatus = crawlingStatus; }

    public int getCollectedCount() { return collectedCount; }
    public void setCollectedCount(int collectedCount) { this.collectedCount = collectedCount; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    // ── toString ─────────────────────────────────
    @Override
    public String toString() {
        return "CrawlingDto{" +
                "crawId=" + crawId +
                ", crawlTime=" + crawlTime +
                ", crawlingStatus='" + crawlingStatus + '\'' +
                ", collectedCount=" + collectedCount +
                ", message='" + message + '\'' +
                '}';
    }
}