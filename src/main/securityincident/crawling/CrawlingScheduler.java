package main.securityincident.crawling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CrawlingScheduler {

    private static final LocalTime TARGET_TIME = LocalTime.of(10, 0); // 오전 10:00

    private final CrawlingService crawlingService;
    private final ScheduledExecutorService scheduler;

    public CrawlingScheduler(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "ScheduledCrawler");
            t.setDaemon(true); // 메인 종료 시 함께 종료
            return t;
        });
    }

    // ───────────────────────────────────────────
    // 스케줄러 시작
    // 매일 오전 10시에 headless 크롤링 자동 실행
    // ───────────────────────────────────────────
    public void start() {
        long initialDelay = calcInitialDelaySeconds();
        long period = TimeUnit.DAYS.toSeconds(1); // 24시간마다 반복

        System.out.println("[CrawlingScheduler] 스케줄러 등록 완료");
        System.out.println("  다음 실행 예정: " + nextRunTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("[CrawlingScheduler] 자동 크롤링 실행 - "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            crawlingService.executeCrawling(true); // headless = true
        }, initialDelay, period, TimeUnit.SECONDS);
    }

    // 스케줄러 중지
    public void stop() {
        scheduler.shutdownNow();
        System.out.println("[CrawlingScheduler] 스케줄러 종료");
    }

    // ───────────────────────────────────────────
    // 다음 오전 10시까지 남은 초 계산
    // ───────────────────────────────────────────
    private long calcInitialDelaySeconds() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.toLocalDate().atTime(TARGET_TIME);

        // 이미 오전 10시가 지났으면 내일로
        if (!now.isBefore(next)) {
            next = next.plusDays(1);
        }

        long delay = java.time.Duration.between(now, next).getSeconds();
        System.out.println("[CrawlingScheduler] 첫 실행까지 남은 시간: "
                + delay / 3600 + "시간 " + (delay % 3600) / 60 + "분");
        return delay;
    }

    private LocalDateTime nextRunTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.toLocalDate().atTime(TARGET_TIME);
        return now.isBefore(next) ? next : next.plusDays(1);
    }
}