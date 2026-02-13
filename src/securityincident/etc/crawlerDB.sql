drop database if exists crawlerDB;
create database crawlerDB;
use crawlerDB;
create table industry(
    industryId int not null auto_increment,
    constraint primary key (industryId),
    industryName varchar(50) not null
);
create table company (
    companyId int not null auto_increment,
    CONSTRAINT PRIMARY KEY (companyId),
    companyName VARCHAR(100) not null unique ,
    headOffice varchar(100),
    foundedYear INT ,
    createdAt varchar(20) not null unique,
    industryId int not null,
    constraint foreign key(industryId) references industry(industryId) on delete cascade on update cascade
);
create table securityIncident(
    incidentId int not null auto_increment,
    constraint primary key (incidentId) ,
    incidentYear char(4) , 
    incidentDate DATE DEFAULT (CURRENT_DATE),
    incidentType VARCHAR(50),
    incidentDescription text,
    actionTaken text,
    approvalStatus varchar(10),
    approvalTime datetime default now(),
    companyId int not null ,
    constraint foreign key(companyId) references company(companyId)  on delete cascade on update cascade
);
create table crawlingLog(
    crawlId int AUTO_INCREMENT,
    constraint primary key (crawlId),
    crawlTime DATETIME NOT NULL default now(),
    crawlingStatus VARCHAR(20)NOT NULL ,
    collectedCount INT NOT NULL,
    message VARCHAR(255)
);
create table article(
    articleId int not null auto_increment ,
    constraint primary key (articleId),
    companyId int not null ,
    constraint foreign key(companyId) references company(companyId)  on delete cascade on update cascade,
    title varchar(200) ,
    content text ,
    articleSource varchar(100),
    articleDate DATE DEFAULT (CURRENT_DATE),
    createdAt datetime
);

-- [1] industry
INSERT INTO industry (industryName)
VALUES('IT / 플랫폼'),('제조');
-- [2] company
INSERT INTO company(companyName, headOffice, foundedYear, createdAt, industryId) 
VALUES
('네이버', '서울', 1999, '2020-07-29 01:00:00', 1),
('카카오', '서울', 2010, '2020-07-30 13:00:00', 1),
('삼성전자', '수원', 1969, '2020-07-31 13:00:00', 1),
('LG전자', '강남', 1958, '2020-08-01 13:00:00', 1),
('국민은행', '서울', 2001, '2020-08-02 13:00:00', 3),
('신한은행', '서울', 1897, '2020-08-03 13:00:00', 3),
('한국전력공사', '나주', 1961, '2020-08-04 13:00:00', 4),
('국민건강보험공단', '원주', 2000, '2020-08-05 13:00:00', 4),
('쿠팡', '서울', 2010, '2020-08-06 13:00:00', 2),
('배달의민족', '서울', 2010, '2020-08-07 13:00:00', 2);
-- [3] Sample
INSERT INTO securityIncident 
(companyId, incidentYear, incidentDate, incidentType, incidentDescription, actionTaken, approvalStatus, approvalTime )
VALUES
(1, '2025', '2025-01-01', '외부 침입 및 해킹', '서버 취약점 공격 감지', 'IP 차단 및 패치 적용', '승인 완료', '2025-01-01 10:00:00' ),
(2, '2025', '2025-01-02', '랜섬웨어 감염', '직원 PC 파일 암호화', '네트워크 분리 및 복구', '승인 완료', '2025-01-02 11:00:00' ),
(3, '2025', '2025-01-03', '내부자 정보 유출', '기밀 문서 외부 반출 시도', '계정 권한 회수', '승인 완료', '2025-01-03 12:00:00' ),
(4, '2025', '2025-01-04', '개인정보 유출', '고객 DB 비정상 접근', 'DB 암호화 강화', '승인 완료', '2025-01-04 13:00:00' ),
(5, '2025', '2025-01-05', '물리적 보안 사고', '출입 통제 구역 무단 진입', '보안 요원 배치', '승인 완료', '2025-01-05 14:00:00' ),
(6, '2024', '2024-12-01', '외부 침입 및 해킹', '웹 어플리케이션 취약점 공격', '방화벽 룰 업데이트', NULL, '2024-12-01 15:00:00' ),
(7, '2024', '2024-12-02', '랜섬웨어 감염', '이메일 첨부파일을 통한 유포', '백신 정밀 검사', NULL, '2024-12-02 16:00:00' ),
(8, '2024', '2024-12-03', '내부자 정보 유출', '저장매체 무단 사용', 'USB 사용 통제', NULL, '2024-12-03 17:00:00' ),
(9, '2024', '2024-12-04', '개인정보 유출', '메일 오발송으로 인한 노출', '해당 메일 회수 요청', NULL, '2024-12-04 18:00:00' ),
(10, '2024', '2024-12-05', '물리적 보안 사고', '사무실 장비 도난 발생', 'CCTV 관제 강화', NULL, '2024-12-05 19:00:00' );
-- [4] article
INSERT INTO article(companyId, title, content, articleSource, articleDate, createdAt) VALUES 
(1, '네이버, 신종 SQL 인젝션 공격 방어 성공', '최근 발생한 대규모 웹 공격 시도를 보안 관제 시스템이 성공적으로 차단함...', '보안뉴스', '2025-01-10', '2025-02-01 09:00:00'),
(2, '카카오 일부 서비스 지연, 원인은 랜섬웨어 의심 악성코드', '내부 직원의 부주의로 유입된 악성코드가 일부 파일 서버를 암호화하여...', '데일리시큐', '2025-01-11', '2025-02-02 09:00:05'),
(3, '삼성전자, 전직원 대상 핵심 기술 유출 방지 교육 실시', '최근 발생한 내부자에 의한 정보 반출 시도 이후 보안 규정을 강화하고...', 'IT타임즈', '2025-01-12', '2025-02-03 09:00:05'),
(4, 'LG전자 클라우드 설정 오류로 고객 정보 일시 노출', '관리자의 부주의로 인해 약 2시간 동안 특정 고객 명단이 외부에 노출되었으나...', '디지털데일리', '2025-01-13', '2025-02-04 09:00:05'),
(5, '국민은행, 지점 서버실 보안 시스템 전면 교체', '미인가자 출입 시도 사건 이후 물리적 보안 강화를 위해 생체 인식 시스템을...', '금융플러스', '2025-01-14', '2025-02-05 09:00:05'),
(6, '신한은행, 해외 IP발 대규모 브루트 포스 공격 탐지', '해외 특정 국가 IP로부터 수만 번의 로그인 시도가 포착되어 즉각 차단 조치함...', '전자신문', '2025-01-15', '2025-02-06 09:00:05'),
(7, '한국전력공사, 국가 배후 해킹 조직의 APT 공격 시도 확인', '에너지 기반 시설을 타깃으로 한 지능형 지속 위협 공격이 있었으나 국정원과...', '연합뉴스', '2025-01-16', '2025-02-07 09:00:05'),
(8, '국민건강보험공단, 협력사 보안 점검 결과 발표', '외주 인력에 의한 데이터 무단 복제를 방지하기 위해 협력사 전수 조사를 실시함...', '청년의사', '2025-01-17', '2025-02-08 09:00:05'),
(9, '쿠팡, 배송 파트너 앱 개인정보 오노출 사과', '시스템 업데이트 과정에서 타인의 배송 정보가 잠시 표시되는 오류가 발생하여...', '뉴시스', '2025-01-18', '2025-02-09 09:00:05'),
(10, '배달의민족, 도난된 업무용 태블릿 기기 전량 원격 삭제 완료', '분실 신고된 모든 모바일 기기의 내부 데이터를 원격으로 소거하여 유출을 방지함...', '테크M', '2025-01-19', '2025-02-10 09:00:05');
-- [5] crawling
INSERT INTO crawlingLog( crawlTime, crawlingStatus, collectedCount, message) VALUES 
( '2026-02-01 09:00:00', 'SUCCESS', 128, '정상 수집 완료'),
( '2026-02-02 09:00:05', 'PARTIAL', 36, '일부 기사 로딩 실패'),
( '2026-02-03 09:00:05', 'FAIL', 0, '사이트 구조 변경 감지'),
( '2026-02-04 09:00:05', 'SUCCESS', 134, '정상 수집 완료'),
( '2026-02-05 09:00:05', 'PARTIAL', 48, '일부 기사 로딩 실패'),
( '2026-02-06 09:00:05', 'FAIL', 0, '타임아웃 발생'),
( '2026-02-07 09:00:05', 'SUCCESS', 140, '정상 수집 완료'),
( '2026-02-08 09:00:05', 'PARTIAL', 86, '네트워크 지연'),
( '2026-02-09 09:00:05', 'FAIL', 0, '사이트 구조 변경 감지'),
( '2026-02-10 09:00:05', 'SUCCESS', 146, '정상 수집 완료');

select*from industry;
select*from company;
select*from securityIncident;
select*from article;
select*from crawlingLog;