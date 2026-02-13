package securityincident.controller;

import securityincident.model.dao.CompanyDao;
import securityincident.model.dto.CompanyDto;

import java.util.ArrayList;

public class CompanyController {
    private CompanyController(){}
    private static final CompanyController instance=new CompanyController();
    public static CompanyController getInstance() {return instance;}

    private CompanyDao cd = CompanyDao.getInstance();

    // * 기업 전체 조회
    public ArrayList<CompanyDto> companyFindAll(){
        ArrayList<CompanyDto> companyDtos = cd.companyFindAll();
        return companyDtos;
    }

    // * 기업 상세 정보 조회
    public ArrayList<CompanyDto> companyFindOne(int companyId){
        ArrayList<CompanyDto> companyDtos = cd.companyFindOne(companyId);
        return companyDtos;
    }

    // 기업 등록
    public boolean companyAdd(String companyName, String headOffice, int foundedYear, int industryId) {
        return cd.companyAdd(companyName, headOffice, foundedYear, industryId);
    }

    // 기업 수정
    public boolean companyUpdate(int cno, int companyId, String companyName, String headOffice, int foundedYear, String createdAt, int industryId){
        boolean result=cd.companyUpdate(cno, companyId, companyName, headOffice, foundedYear, null, industryId);
        return result;
    }

    // 기업 삭제
    public boolean companyDelete(int cno){
        boolean result=cd.companyDelete(cno);
        return result;
    }


}

