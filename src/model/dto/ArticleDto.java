package model.dto;

import java.security.Timestamp;
import java.util.Date;

// 김재현 작업
public class ArticleDto {

    private int articleId;
    private int companyId;
    private String title;
    private String content;
    private String articleSource;
    private Date articleDate;
    private Timestamp createdAt;

    //생성자
    public ArticleDto(int articleId, int companyId, String title, String content, String articleSource, Date articleDate, Timestamp createdAt) {
        this.articleId = articleId;
        this.companyId = companyId;
        this.title = title;
        this.content = content;
        this.articleSource = articleSource;
        this.articleDate = articleDate;
        this.createdAt = createdAt;
    }

    // gettet, setter 설정
    public int getArticleId() {return articleId;}
    public void setArticleId(int articleId) {this.articleId = articleId;}

    public int getCompanyId() {return companyId;}
    public void setCompanyId(int companyId) {this.companyId = companyId;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public String getArticleSource() {return articleSource;}
    public void setArticleSource(String articleSource) {this.articleSource = articleSource;}

    public Date getArticleDate() {return articleDate;}
    public void setArticleDate(Date articleDate) {this.articleDate = articleDate;}

    public Timestamp getCreatedAt() {return createdAt;}
    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}
    // toString
    @Override
    public String toString() {
        return "ArticleDto{" +
                "articleId=" + articleId +
                ", companyId=" + companyId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", articleSource='" + articleSource + '\'' +
                ", articleDate=" + articleDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
