package securityincident.controller;

import securityincident.model.dao.CrawlDao;

public class CrawlController {

    private CrawlController(){}
    private static final CrawlController instance = new CrawlController();
    public static CrawlController getInstance(){ return instance; }

    private CrawlDao cd = CrawlDao.getInstance();
    private IncidentController ic = IncidentController.getInstance();

    // 보안 키워드 정의
    private static final String[] SECURITY_KEYWORDS = {
            "해킹", "랜섬웨어", "정보 유출", "개인정보", "침해", "악성코드"
    };

    // 크롤링 상태 수정
    public boolean updateCrawlLog(int crawId, String status, int count, String message){
        return cd.updateCrawlLog(crawId, status, count, message);
    }

    // ------------------------------
    // 기사 분석 → 사고 자동 생성
    // ------------------------------
    public void detectAndCreateIncident(String articleTitle,
                                        String articleContent,
                                        int companyId){

        String text = (articleTitle + " " + articleContent);

        for(String keyword : SECURITY_KEYWORDS){

            if(text.contains(keyword)){

                ic.autoInsertIncident(
                        String.valueOf(java.time.Year.now().getValue()),
                        keyword,
                        articleTitle,
                        companyId
                );

                break; // 하나만 등록
            }
        }
    }
}
