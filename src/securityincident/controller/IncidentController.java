package securityincident.controller;

import securityincident.model.dao.IncidentDao;
import securityincident.model.dto.IncidentDto;

import java.util.ArrayList;

public class IncidentController {
    private IncidentController(){}
    private static final IncidentController instance = new IncidentController();
    public static IncidentController getInstance(){
        return instance;
    }

    private IncidentDao id = IncidentDao.getInstance();

    // 1. 보안사고관리
    // 보안사고 등록 controller - 이한승
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
