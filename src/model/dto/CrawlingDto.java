package model.dto;
// 작업자 김재현
import java.security.Timestamp;

public class CrawlingDto {
    private int crawlId;
    private Timestamp crawlTime;
    private String crawlingStatus;
    private int collectedCount;
    private String message;

    //생성자
    public CrawlingDto(int crawlId, Timestamp crawlTime, String crawlingStatus, int collectedCount, String message) {
        this.crawlId = crawlId;
        this.crawlTime = crawlTime;
        this.crawlingStatus = crawlingStatus;
        this.collectedCount = collectedCount;
        this.message = message;
    }

    //getter, setter
    public int getCrawlId() {return crawlId;}
    public void setCrawlId(int crawlId) {this.crawlId = crawlId;}

    public Timestamp getCrawlTime() {return crawlTime;}
    public void setCrawlTime(Timestamp crawlTime) {this.crawlTime = crawlTime;}

    public String getCrawlingStatus() {return crawlingStatus;}
    public void setCrawlingStatus(String crawlingStatus) {this.crawlingStatus = crawlingStatus;}

    public int getCollectedCount() {return collectedCount;}
    public void setCollectedCount(int collectedCount) {this.collectedCount = collectedCount;}

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    //toString
    @Override
    public String toString() {
        return "CrawlingDto{" +
                "crawlId=" + crawlId +
                ", crawlTime=" + crawlTime +
                ", crawlingStatus='" + crawlingStatus + '\'' +
                ", collectedCount=" + collectedCount +
                ", message='" + message + '\'' +
                '}';
    }
}
