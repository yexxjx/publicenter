package main.securityincident.crawling;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import main.securityincident.model.dto.ArticleDto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsCrawler {

    // ── 필터링: 10개 기업명 ──────────────────────────
    private static final String[] COMPANIES = {
            "카카오", "네이버", "메타", "당근", "야놀자",
            "삼성전자", "LG전자", "현대자동차", "TSMC", "SK하이닉스"
    };

    // ── 필터링: 사고성 보안 키워드만 (보안/사이버/개인정보 제거) ──
    private static final String[] SECURITY_KEYWORDS = {
            "유출", "해킹", "랜섬웨어", "침해", "악성코드", "공격", "취약점"
    };

    // ── 크롤링 페이지 수 ───────────────────────────────
    private static final int MAX_PAGES = 2;

    // ─────────────────────────────────────────────────
    private WebDriver createDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--lang=ko-KR");
        options.addArguments(
                "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/120.0.0.0 Safari/537.36"
        );
        return new ChromeDriver(options);
    }

    // ─────────────────────────────────────────────────
    // 외부 호출 진입점
    // ─────────────────────────────────────────────────
    public List<ArticleDto> crawlAll(boolean headless) {
        List<ArticleDto> result = new ArrayList<>();
        WebDriver driver = createDriver(headless);

        try {
            for (int page = 1; page <= MAX_PAGES; page++) {
                System.out.println("[NewsCrawler] " + page + "페이지 크롤링 시작...");
                List<ArticleDto> pageArticles = crawlPage(driver, page);
                result.addAll(pageArticles);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("[NewsCrawler] 오류: " + e.getMessage());
        } finally {
            driver.quit();
        }

        System.out.println("[NewsCrawler] 전체 수집 완료: " + result.size() + "건");
        return result;
    }

    // ─────────────────────────────────────────────────
    // 페이지 1개 크롤링
    // ─────────────────────────────────────────────────
    private List<ArticleDto> crawlPage(WebDriver driver, int page) throws Exception {
        List<ArticleDto> articles = new ArrayList<>();

        String listUrl = "https://www.boannews.com/media/t_list.asp?kind=1&page=" + page;
        driver.get(listUrl);
        Thread.sleep(3000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.news_list")
            ));
        } catch (TimeoutException e) {
            System.err.println("[타임아웃] " + page + "페이지 로딩 실패");
            return articles;
        }

        // ── STEP 1: 목록에서 제목 + URL + 날짜 미리 수집 ──
        List<WebElement> items = driver.findElements(By.cssSelector("div.news_list > a"));
        System.out.println("[NewsCrawler] " + page + "페이지 기사 수: " + items.size() + "건");

        List<String[]> articleInfos = new ArrayList<>(); // [title, url, date]

        for (WebElement item : items) {
            try {
                String title = "";
                try {
                    title = item.findElement(By.cssSelector("p.news_txt, .news_tit, p, strong"))
                            .getText().trim();
                } catch (NoSuchElementException e) {
                    title = item.getText().trim();
                }
                if (title.isEmpty()) continue;

                String articleUrl = item.getAttribute("href");
                if (articleUrl == null || articleUrl.isBlank()) continue;

                String dateText = "";
                try {
                    dateText = item.findElement(
                            By.cssSelector("p.news_writer span, span.date, .date")
                    ).getText().trim();
                } catch (NoSuchElementException ignored) {}

                // ✅ 1차 필터: 기업명 AND 사고키워드 둘 다 포함된 경우만 후보로
                if (findMatchedCompany(title) != null && hasSecurityKeyword(title)) {
                    articleInfos.add(new String[]{title, articleUrl, dateText});
                    System.out.println("  [후보] " + title);
                }

            } catch (Exception e) {
                System.err.println("  [목록오류] " + e.getMessage());
            }
        }

        System.out.println("[NewsCrawler] " + page + "페이지 후보: " + articleInfos.size() + "건");

        // ── STEP 2: 후보 기사만 상세 페이지 진입해서 본문 수집 ──
        for (String[] info : articleInfos) {
            String title    = info[0];
            String url      = info[1];
            String dateText = info[2];

            try {
                String content = crawlArticleContent(driver, url);

                // ✅ 2차 필터: 제목+본문 합쳐서 기업명 AND 사고키워드 최종 확인
                String fullText = title + " " + content;
                String matchedCompany = findMatchedCompany(fullText);
                boolean hasSecurity  = hasSecurityKeyword(fullText);

                // 기업명 없거나 사고키워드 없으면 저장 안 함
                if (matchedCompany == null || !hasSecurity) continue;

                Date articleDate = parseDate(dateText);
                int companyId = getCompanyId(matchedCompany);

                ArticleDto dto = new ArticleDto(
                        0, companyId, title, content, "보안뉴스", articleDate, null
                );
                dto.setArticleUrl(url);
                articles.add(dto);

                System.out.println("  [수집] [" + matchedCompany + "] " + title);
                Thread.sleep(1500);

            } catch (Exception e) {
                System.err.println("  [오류] " + title + " → " + e.getMessage());
            }
        }

        System.out.println("[NewsCrawler] " + page + "페이지 수집 완료: " + articles.size() + "건");
        return articles;
    }

    // ─────────────────────────────────────────────────
    // 상세 페이지 본문 추출
    // ─────────────────────────────────────────────────
    private String crawlArticleContent(WebDriver driver, String url) {
        if (url == null || url.isBlank()) return "본문 없음";
        try {
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

            String[] contentSelectors = {
                    "div#news_content",
                    "td#news_content",
                    "#news_content",
                    "div#news_content_txt"
            };

            for (String sel : contentSelectors) {
                List<WebElement> els = driver.findElements(By.cssSelector(sel));
                if (!els.isEmpty()) {
                    System.out.println("  [본문선택자 성공] " + sel);
                    String cleaned = cleanContent(els.get(0).getText().trim());
                    return cleaned.length() > 1000 ? cleaned.substring(0, 1000) + "..." : cleaned;
                }
            }
            return "본문 추출 실패";

        } catch (Exception e) {
            System.err.println("  [본문오류] " + e.getMessage());
            return "본문 추출 실패";
        }
    }

    // ─────────────────────────────────────────────────
    // 기업명 매칭
    // ─────────────────────────────────────────────────
    private String findMatchedCompany(String text) {
        for (String company : COMPANIES) {
            if (text.contains(company)) return company;
        }
        return null;
    }

    // 기업명 → companyId 매핑
    private int getCompanyId(String companyName) {
        switch (companyName) {
            case "카카오":      return 1;
            case "네이버":      return 2;
            case "메타":        return 3;
            case "당근":        return 4;
            case "야놀자":      return 5;
            case "삼성전자":    return 6;
            case "LG전자":      return 7;
            case "현대자동차":  return 8;
            case "TSMC":        return 9;
            case "SK하이닉스":  return 10;
            default:            return 1;
        }
    }

    // ─────────────────────────────────────────────────
    // 사고성 보안 키워드 포함 여부
    // ─────────────────────────────────────────────────
    private boolean hasSecurityKeyword(String text) {
        for (String keyword : SECURITY_KEYWORDS) {
            if (text.contains(keyword)) return true;
        }
        return false;
    }

    // ─────────────────────────────────────────────────
    // 날짜 파싱
    // ─────────────────────────────────────────────────
    private Date parseDate(String text) {
        if (text == null || text.isBlank()) return new Date(System.currentTimeMillis());
        text = text.trim();

        if (text.contains("시간 전") || text.contains("분 전"))
            return new Date(System.currentTimeMillis());

        if (text.contains("일 전")) {
            try {
                int days = Integer.parseInt(text.replace("일 전", "").trim());
                return java.sql.Date.valueOf(LocalDate.now().minusDays(days));
            } catch (NumberFormatException ignored) {}
        }

        String[] patterns = {"yyyy-MM-dd", "yyyy.MM.dd", "yy-MM-dd", "yy.MM.dd"};
        for (String pattern : patterns) {
            try {
                String cleaned = text.replaceAll("[^0-9.\\-]", "");
                if (cleaned.length() >= pattern.length())
                    cleaned = cleaned.substring(0, pattern.length());
                return java.sql.Date.valueOf(
                        LocalDate.parse(cleaned, DateTimeFormatter.ofPattern(pattern))
                );
            } catch (Exception ignored) {}
        }
        return new Date(System.currentTimeMillis());
    }

    // ─────────────────────────────────────────────────
    // 본문 정제
    // ─────────────────────────────────────────────────
    private String cleanContent(String raw) {
        if (raw == null) return "";
        return raw
                .replaceAll("\\[.*?\\]", "")
                .replaceAll("\\(.*?기자\\)", "")
                .replaceAll("저작권자.*", "")
                .replaceAll("[\\r\\n]{3,}", "\n\n")
                .trim();
    }
}