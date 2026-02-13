package securityincident.controller;

import securityincident.model.dao.IndustryDao;
import securityincident.model.dto.IndustryDto;

import java.util.ArrayList;

public class IndustryController{
    private IndustryController(){}
    private static final IndustryController instance=new IndustryController();
    public static IndustryController getInstance() {return instance;}

    private IndustryDao id=IndustryDao.getInstance();

    // 산업군 조회
    public ArrayList<IndustryDto> industryFindAll(){
        ArrayList<IndustryDto> result=id.industryFindAll();
        return result;
    }

    // 산업군 등록
    public boolean industryAdd(int industryId, String industryName){
        boolean result=id.industryAdd(industryId, industryName);
        return result;
    }

    // 산업군 수정
    public boolean industryUpdate(int industryId, String industryName){
        boolean result=id.industryUpdate(industryId, industryName);
        return result;
    }

    // 산업군 삭제
    public boolean industryDelete(int ino){
        boolean result=id.industryDelete(ino);
        return result;
    }
}
