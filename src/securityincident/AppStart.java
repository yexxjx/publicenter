package securityincident;

import securityincident.crawling.CrawlingScheduler;
import securityincident.crawling.CrawlingService;
import securityincident.view.MainView;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AppStart {
    public static void main(String[] args) {

        // ── Selenium/OpenTelemetry 경고 로그 숨기기 ──
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        Logger.getLogger("io.opentelemetry").setLevel(Level.OFF);
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("otel.java.global-autoconfigure.enabled", "false");

        CrawlingService crawlingService = new CrawlingService();

        CrawlingScheduler scheduler = new CrawlingScheduler(crawlingService);
        scheduler.start();

        Thread initCrawl = new Thread(() -> {
        }, "InitCrawler");

        initCrawl.start();
        try {
            initCrawl.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[System] 크롤링 완료. 콘솔을 시작합니다.\n");
        MainView.getInstance().index();
    }
}