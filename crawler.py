import pymysql
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from urllib.parse import quote
from datetime import datetime
import time

# -------------------------------
# 1. DB 설정
# -------------------------------
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '1234',      # 본인 비밀번호
    'db': 'security_db',
    'charset': 'utf8mb4'
}

# -------------------------------
# 2. 기업 목록
# -------------------------------
COMPANIES = {
    1: "카카오",
    2: "네이버",
    3: "메타",
    4: "당근",
    5: "야놀자",
    6: "삼성전자",
    7: "LG전자",
    8: "현대자동차",
    9: "TSMC",
    10: "SK하이닉스"
}

# -------------------------------
# 3. 보안 키워드
# -------------------------------
SECURITY_KEYWORDS = [
    "해킹","침해","유출","랜섬웨어","피싱",
    "취약점","사이버","악성코드","보안","공격"
]

# -------------------------------
# 4. 유틸 함수
# -------------------------------
def euckr_quote(keyword: str) -> str:
    return quote(keyword.encode("euc-kr"))

def build_url(keyword: str, page=1) -> str:
    base = "https://www.boannews.com/search/news_total.asp"
    if page == 1:
        return f"{base}?search=title&find={euckr_quote(keyword)}"
    return f"{base}?Page={page}&search=title&find={euckr_quote(keyword)}"

def make_driver():
    options = Options()
    options.add_argument("--headless")  # Java 연동 대비
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")
    options.add_argument("--disable-blink-features=AutomationControlled")
    service = Service(ChromeDriverManager().install())
    return webdriver.Chrome(service=service, options=options)

# -------------------------------
# 5. 크롤링 실행
# -------------------------------
def run_crawling():
    start_time = datetime.now()
    collected_count = 0
    status = "SUCCESS"
    message = ""

    print(f"[{start_time}] 크롤링 시작")

    driver = make_driver()

    try:
        conn = pymysql.connect(**DB_CONFIG)
        cursor = conn.cursor()

        for cid, cname in COMPANIES.items():
            print(f"[{cname}] 검색 시작", flush=True)

            driver.get(build_url(cname, 1))
            time.sleep(1)

            links = driver.find_elements(By.CSS_SELECTOR, "div.news_list a")

            urls = list(dict.fromkeys([
                a.get_attribute("href")
                for a in links
                if a.get_attribute("href")
                and "view.asp" in a.get_attribute("href")
            ]))

            # 최신 10개 기사만 확인
            for link in urls[:10]:

                driver.get(link)
                time.sleep(0.5)

                try:
                    title = driver.find_element(By.CSS_SELECTOR, "h1").text.strip()
                    content = driver.find_element(By.ID, "news_content").text.strip()

                    full_text = title + content

                    # 기업명 + 보안 키워드 필터
                    if cname in full_text and any(k in full_text for k in SECURITY_KEYWORDS):

                        try:
                            date_text = driver.find_element(By.ID, "news_util01").text
                            article_date = date_text.replace("입력 :", "").strip().split()[0]
                        except:
                            article_date = datetime.now().strftime("%Y-%m-%d")

                        sql = """
                        INSERT IGNORE INTO article
                        (companyId, title, content, articleSource, articleDate, createdAt, articleUrl)
                        VALUES (%s, %s, %s, %s, %s, %s, %s)
                        """

                        cursor.execute(sql, (
                            cid,
                            title[:200],
                            content[:3000],
                            "보안뉴스",
                            article_date,
                            datetime.now(),
                            link
                        ))

                        if cursor.rowcount > 0:
                            collected_count += 1
                            print(f"   >> 신규 기사 저장: {title[:40]}...")

                except Exception as e:
                    print(f"   기사 처리 실패: {e}")

        conn.commit()
        cursor.close()
        conn.close()

    except Exception as e:
        status = "FAIL"
        message = str(e)
        print(f"[에러 발생] {e}")

    finally:
        driver.quit()

    # -------------------------------
    # 6. crawl_log 기록
    # -------------------------------
    try:
        conn = pymysql.connect(**DB_CONFIG)
        cursor = conn.cursor()

        log_sql = """
        INSERT INTO crawl_log
        (crawlTime, crawlingStatus, collectedCount, message)
        VALUES (%s, %s, %s, %s)
        """

        cursor.execute(log_sql, (
            start_time,
            status,
            collected_count,
            message
        ))

        conn.commit()
        cursor.close()
        conn.close()

    except Exception as e:
        print(f"[로그 기록 실패] {e}")

    print(f"\n크롤링 종료 - 신규 저장 건수: {collected_count}")

# -------------------------------
# 7. 메인 실행
# -------------------------------
if __name__ == "__main__":
    run_crawling()
