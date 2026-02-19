package securityincident.controller;

import securityincident.model.dao.CrawlDao;

public class CrawlController {

    private CrawlController(){}
    private static final CrawlController instance = new CrawlController();
    public static CrawlController getInstance(){ return instance; }

    private CrawlDao cd = CrawlDao.getInstance();

    public boolean updateCrawlLog(int crawId, String status, int count, String message){
        return cd.updateCrawlLog(crawId, status, count, message);
    }
}
