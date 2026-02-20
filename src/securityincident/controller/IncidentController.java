package securityincident.controller;

import securityincident.model.dao.CompanyDao;
import securityincident.model.dao.IncidentDao;
import securityincident.model.dto.IncidentDto;

import java.util.ArrayList;

public class IncidentController {
    // 싱글톤
    private IncidentController(){}
    private static final IncidentController instance = new IncidentController();
    public static IncidentController getInstance(){return instance;}
    private IncidentDao id = IncidentDao.getInstance();

    // 1. 보안사고관리
    // 보안사고 등록 controller
    public boolean incidentAddByAdmin(String companyName,String incidentYear, String incidentType, String incidentDescription,String actionTaken){

        // 1. 기업 존재 확인
        int companyId = id.getCompanyIdByName(companyName);

        // 2. 기업이 존재하지 않으면 등록 불가
        if (companyId == -1) {
            System.out.println("❌ 존재하지 않는 기업입니다. 기업을 먼저 등록해주세요.");
            return false;
        }

        // 3. dto 객체 생성
        IncidentDto incidentDto = new IncidentDto();
        incidentDto.setIncidentYear(incidentYear);
        incidentDto.setIncidentType(incidentType);
        incidentDto.setIncidentDescription(incidentDescription);
        incidentDto.setActionTaken(actionTaken);
        incidentDto.setCompanyId(companyId);

        boolean result = id.incidentAddByAdmin(incidentDto);

        return result;
    } // m end

    // 보안사고삭제
    public boolean incidentDelete(int incidentId){
        boolean result = id.incidentDelete(incidentId);
        return result;
    }



    // * 보안사고 전체 조회
    public ArrayList<IncidentDto> incidentFindAll(){
        ArrayList<IncidentDto> incidentDtos = id.incidentFindAll();
        return incidentDtos;
    }

    // * 기업별 보안 사고 조회
    public ArrayList<IncidentDto> incidentFindByCompany(String companyName){
        ArrayList<IncidentDto> incidentDtos = id.incidentFindByCompany();
        return incidentDtos;
    }

    // * 사고 상세 정보 조회
    public ArrayList<IncidentDto> incidentFindOne(int incidentId){
        ArrayList<IncidentDto> incidentDtos = id.incidentFindOne(incidentId);
        return incidentDtos;
    }

    // 보안사고수정
    public boolean incidentUpdate(int incidentId, String incidentYear, String incidentDate,
                                  String incidentType, String incidentDescription, String actionTaken){

        boolean result = id.incidentUpdate(
                incidentId,
                incidentYear,
                incidentDate,
                incidentType,
                incidentDescription,
                actionTaken
        );
        return result;

    }

    //연도별 보안 사고 검색
    public ArrayList<IncidentDto> incidentFindByYear(String year){
        ArrayList<IncidentDto>db = id.incidentFindByYear(year);
        return db;
    } // m end


    // 유형 목록 가져오기
    public ArrayList<String> getIncidentTypeList(){
        return id.getIncidentTypeList();
    }

    // 유형별 검색
    public ArrayList<IncidentDto> incidentFindByType(String type){
        return id.incidentFindByType(type);
    }



} // class end
