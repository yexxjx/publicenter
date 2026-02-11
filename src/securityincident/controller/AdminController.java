package securityincident.controller;

import securityincident.model.dao.AdminDao;

public class AdminController {
    private AdminController(){}
    private static final AdminController instance = new AdminController();
    public static AdminController getInstance(){return instance;}

    private AdminDao ad = AdminDao.getInstance();

    // +) 관리자 로그인 세션
    private int loinSession = 0;
    public int getLoinSession(){
        return loinSession;
    }

    // 1. 관리자 로그인
    public boolean adminLogin(String pw){
        boolean result = ad.adminLogin(pw);
        return result;
    }

    // 2. 관리자 로그아웃
    public boolean adminLogout(){
        loinSession = 0;
        return true;
    }
}
