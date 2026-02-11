package securityincident.controller;

import securityincident.model.dao.CompanyDao;
import securityincident.model.dto.CompanyDto;

import java.util.ArrayList;

// 작업자 이연지, 이태현,
public class CompanyController {
    private CompanyController(){}
    private static final CompanyController instance = new CompanyController();
    public static CompanyController getInstance(){return instance;}

    private CompanyDao cd = CompanyDao.getInstance();

    // * 기업 전체 조회
    public ArrayList<CompanyDto> companyFindAll(){
        ArrayList<CompanyDto> companyDtos = cd.companyFindAll();
        return companyDtos;
    }
}
