import securityincident.crawling.CrawlingScheduler;
import securityincident.crawling.CrawlingService;
import securityincident.view.MainView;

public class Main {
    public static void main(String[] args) {

        CrawlingService crawlingService = new CrawlingService();

        // ── Thread 1: 매일 오전 10시 자동 크롤링 스케줄러 ──
        CrawlingScheduler scheduler = new CrawlingScheduler(crawlingService);
        scheduler.start();

        // ── Thread 2: 시작 즉시 1회 백그라운드 크롤링 ──
        Thread initCrawl = new Thread(() -> {
            System.out.println("[Main] 초기 크롤링 실행 중 (백그라운드)...");
            crawlingService.executeCrawling(true);
        }, "InitCrawler");
        initCrawl.setDaemon(true);
        initCrawl.start();

        // ── Thread 3 (메인): 콘솔 메뉴 ──
        MainView.getInstance().index();
    }
}