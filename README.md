크롤링 및 자동화 담당 - 김재현

1. 담당 역할
    보안 뉴스 사이트 기반 기사 크롤러 설계 및 구현
    Python(Selenium) 기반 자동 수집 시스템 구축
    Java ↔ Python 연동 구조 설계
    Windows 작업 스케줄러 기반 자동 실행 구성
    중복 기사 방지 및 실행 로그 관리 설계

2. 사용 기술 스택
    Python 3.x
    Selenium (Headless Chrome)
    PyMySQL
    Java ProcessBuilder
    MySQL
    Windows Task Scheduler

3. 크롤링 시스템 설계 구조
Windows 작업 스케줄러 (매일 10시)
        ↓
java -jar publicenter.jar
        ↓
Java ProcessBuilder
        ↓
python crawler.py
        ↓
보안뉴스 사이트 크롤링
        ↓
MySQL article 테이블 저장
        ↓
crawl_log 실행 기록 저장

4. 주요 구현 기능
보안 뉴스 키워드 기반 기사 수집
    기업명 + 보안 키워드 필터링
    최근 기사 기준 수집

중복 기사 방지
    articleUrl UNIQUE 제약 조건 활용
    INSERT IGNORE 사용

실행 로그 관리
    crawl_log 테이블에 SUCCESS / FAIL 기록
    수집 건수 및 에러 메시지 저장

Java ↔ Python 통합
    Java에서 ProcessBuilder를 이용해 Python 실행
    UTF-8 인코딩 처리
    상대경로 기반 협업 구조 설계

자동화
    Windows 작업 스케줄러 매일 10시 실행
    배치 기반 안정적 수집 구조 설계

5. 설계 의도
    크롤링 로직은 Python이 가장 적합하다고 판단
    기존 Java MVC 시스템과 분리하여 유지보수성 확보
    운영 환경에서는 OS 스케줄러 기반 배치 방식 채택
    DB 레벨에서 UNIQUE 제약으로 데이터 무결성 확보
