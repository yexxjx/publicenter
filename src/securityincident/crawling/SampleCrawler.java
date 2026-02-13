package securityincident.crawling;
//Selenium 관련 클래스 import
import org.openqa.selenium.By; //By: HTML 요소를 찾기 위한 선택자
import org.openqa.selenium.WebDriver; //WebDriver : 브라우저 제어 인터페이스
import org.openqa.selenium.WebElement; // WebElement : 화면에 있는 HTML 요소 하나
import org.openqa.selenium.chrome.ChromeDriver; //ChromDriver : 크롬 브라우저 구현체

import securityincident.model.dao.CrawlingDao; //DB에 기사/크롤링 로그 저장을 담당하는 DAO
import securityincident.model.dto.ArticleDto; // ArticleDto : 뉴스 기사 1건
import securityincident.model.dto.CrawlingDto; // CrawlingLogDto : 크롤링 실행 로그 1건
import securityincident.util.DBUtil; // DB 연결을 생성해주는 유틸 클래스

import java.sql.Connection; //connction : JDBC DB 연결 객체
import java.sql.Date;       // Date : SQL DATE 타입
import java.util.List;      // List : 여러 개 요소 저장용

public class SampleCrawler { //샘플 크롤러 클래스

    public static void main(String[] args) { //프로그램 실행 시작 지점

        Connection conn = null; //DB 연결 객체 선언
        WebDriver driver = null; // Selenium 크롬 드라이버 객체
        int savedCount = 0;      // DB에 몇건의 기사를 저장했는지 기록하는 변수

        try { // 크롤링 + DB 저장 전체를 감싸는 예외 처리 블록
            conn = DBUtil.getConnection(); // DBUtil을 통해 MYSQL DB 생성
            CrawlingDao crawlingDao = new CrawlingDao(conn);

            driver = new ChromeDriver(); //크롬 드라이버 생성
            driver.get("https://search.naver.com/search.naver?query=보안+사고"); //네이버 뉴스 검색 페이지로 이동, 검색어: "보안 사고"

            //검색 결과 페이지에서 .news_area 클래스를 가진 요소들 전부 선택 기사 목록 전체를 리스트로 가져옴
            List<WebElement> articles =
                    driver.findElements(By.cssSelector(".news_area"));

            for (int i = 0; i < 3 && i < articles.size(); i++) { //기사 최대 3건만 처리

                //i번째 기사 블록에서 .news_tit 클래스(기사 제목 링크)를 찾고 제목 텍스트만 추출
                String title = articles.get(i)
                        .findElement(By.cssSelector(".news_tit"))
                        .getText();

                //기사 정보를 담을 DTO 객체 생성 시작
                ArticleDto dto = new ArticleDto(
                        1, //company_id
                        title,                //방금 크롤링한 기사 제목
                        "본문 샘플 데이터", //아직 본문 크롤링 전이므로 임시 문자열
                        "네이버 뉴스",          //기사 출처
                        Date.valueOf("2026-02-01") //기사 날짜
                );

                crawlingDao.insertArticle(dto); //DAO를 통해 news_article 테이블에 INSERT
                savedCount++;                   //기사 1건 저장 성공 → 카운트 증가
            }

            crawlingDao.insertCrawlingLog(      //크롤링 실행 결과를 로그 테이블에 저장
                    new CrawlingLogDto(         //크롤링 로그 DTO 생성
                            "SUCCESS",          //크롤링 상태: 성공
                            savedCount,         //실제 저장된 기사 수
                            "샘플 크롤링 완료"     //실행 결과 메시지
                    )
            );

        } catch (Exception e) {                 //크롤링 또는 DB 처리 중 예외 발생 시 진입

            if (conn != null) {                 //DB 연결이 이미 생성된 상태라면
                try {                           //로그 저장 중 에러가 나도 프로그램 죽지 않게 한 번 더 try
                       new CrawlingDao(conn).insertCrawlingLog(    //실패 로그 저장 시도
                            new CrawlingLogDto(
                                    "FAIL",
                                    savedCount,
                                    "크롤링 중 오류 발생"
                            )
                    );
                } catch (Exception ignored) {}
            }

            e.printStackTrace();

        } finally {

            if (driver != null) {
                driver.quit();
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ignored) {}
            }
        }
    }
}
