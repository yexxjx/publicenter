package securityincident.controller;

import securityincident.model.dao.AdminDao;
import securityincident.model.dao.IncidentDao;
import securityincident.model.dao.CrawlDao;

public class AdminController {

    private static AdminController instance = new AdminController();
    private int loginSession = 0;

    private AdminDao ad = AdminDao.getInstance();
    private IncidentDao id = IncidentDao.getInstance();
    private CrawlDao cd = CrawlDao.getInstance();

    private AdminController(){}

    public static AdminController getInstance(){
        return instance;
    }

    // 관리자 로그인
    public boolean adminLogin(String pw){
        if(ad.adminLogin(pw)){
            loginSession = 1;
            return true;
        }
        return false;
    }

    public int getLoinSession(){
        return loginSession;
    }

    public void adminLogout(){
        loginSession = 0;
    }

    // 사고 승인 처리
    public boolean approveIncident(int incidentId){
        return id.approveIncident(incidentId);
    }

    // 크롤링 상태 업데이트
    public boolean updateCrawling(int crawId, String status, int count, String message){
        return cd.updateCrawlLog(crawId, status, count, message);
    }
}
