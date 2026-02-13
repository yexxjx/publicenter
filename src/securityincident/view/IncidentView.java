package securityincident.view;

import securityincident.controller.IncidentController;
import securityincident.model.dao.IncidentDao;
import securityincident.model.dto.IncidentDto;

import java.util.ArrayList;
import java.util.Scanner;

public class IncidentView {

    // 싱글톤 - 이한승
    private IncidentView(){}
    private static final IncidentView instance = new IncidentView();
    public static IncidentView getInstance(){
        return instance;
    }

    private IncidentController ic = IncidentController.getInstance();

    // 스캐너
    private Scanner scan = new Scanner(System.in);


    //1. 보안사고관리
    public void incidentMenu(){
        for(;;){
            System.out.println("──┤ 보안 사고 관리 ├──────────────────────────────────────");
            System.out.println("1. 사고 목록 조회");
            System.out.println("2. 사고 등록");
            System.out.println("3. 사고 정보 수정");
            System.out.println("4. 사고 삭제");
            System.out.println("5. 관리자 메뉴로 돌아가기");
            System.out.print("선택 > "); int ch= scan.nextInt();scan.nextLine();

            if(ch==1){incidentFindAll();}
            else if(ch==2){incidentAddByAdmin();}
            else if(ch==3){incidentUpdate();}
            else if(ch==4){incidentDelete();}
            else{}
        }
    }
    //보안사고등록 view
    public void incidentAddByAdmin() {

        System.out.println("──┤ 보안 사고 등록 ├──────────────────────────────────────");

        System.out.print("기업명 입력 > ");
        String companyName = scan.nextLine();

        System.out.print("사고 년도 입력 > ");
        String incidentYear = scan.nextLine();

        System.out.print("사고 유형 입력 > ");
        String incidentType = scan.nextLine();

        System.out.print("사고 내용 입력 > ");
        String incidentDescription = scan.nextLine();

        System.out.print("조치 내용 입력 > ");
        String actionTaken = scan.nextLine();

        // 입력값만 Controller에 전달
        ic.incidentAddByAdmin(companyName,incidentYear, incidentType, incidentDescription,actionTaken);
    } // m end

    //보안사고삭제view
    public void incidentDelete(){
        System.out.println("──┤ 보안 사고 삭제 ├──────────────────────────────────────");
        System.out.print("삭제할 사고 번호(ID) 입력 : "); int incidentId = scan.nextInt();

        boolean result = ic.incidentDelete(incidentId);

        if(result){
            System.out.println("삭제 완료");
        }
        else{
            System.out.println("존재하지 않는 사고 번호 입니다.");
        }

    }

    //보안사고조회view
    public void incidentFindAll(){
        ArrayList<IncidentDto>db = ic.incidentFindAll();
        for(IncidentDto a : db){

            System.out.printf(
                    "사고번호: %d, 발생연도: %s, 발생일: %s, 유형: %s, 상세내용: %s, 조치사항: %s, 승인상태: %s, 승인시간: %s, 기업번호: %d \n",
                    a.getIncidentId(),
                    a.getIncidentYear(),
                    a.getIncidentDate(),
                    a.getIncidentType(),
                    a.getIncidentDescription(),
                    a.getActionTaken(),
                    a.getApprovalStatus(),
                    a.getApprovalTime(),
                    a.getCompanyId()
            );
        }
    }

    //보안사고수정view
    public void incidentUpdate() {
        System.out.println("──┤ 보안 사고 정보 수정 ├──────────────────────────────────────");

        // 1. 수정할 대상 식별
        System.out.print("수정할 사고 번호(ID) 입력 > ");
        int incidentId = scan.nextInt();
        scan.nextLine(); // 버퍼 비우기

        // 2. 수정할 데이터들 입력
        System.out.print("새로운 발생 연도 입력 > ");
        String incidentYear = scan.nextLine();

        System.out.print("새로운 발생 일자 입력(YYYY-MM-DD) > ");
        String incidentDate = scan.nextLine();

        System.out.print("새로운 사고 유형 입력 > ");
        String incidentType = scan.nextLine();

        System.out.print("새로운 사고 내용 입력 > ");
        String incidentDescription = scan.nextLine();

        System.out.print("새로운 조치 내용 입력 > ");
        String actionTaken = scan.nextLine();

        // 3. Controller 호출 (인자 순서 주의!)
        boolean result = ic.incidentUpdate(
                incidentId,
                incidentYear,
                incidentDate,
                incidentType,
                incidentDescription,
                actionTaken
        );

        if (result) {
            System.out.println("✅ 사고 정보가 모두 수정되었습니다.");
        } else {
            System.out.println("❌ 수정 실패: ID를 확인하거나 입력 형식을 확인하세요.");
        }
    }


    // 2. 검색 필터 페이지
    public void SearchView(){
        for(;;){
            System.out.println("──┤ 사고 검색 / 필터 ├──────────────────────────────────────");
            System.out.println("1. 연도별 보안 사고 검색");
            System.out.println("2. 사고 유형별 검색");
            System.out.println("3. 산업군별 검색");
            System.out.println("4. 이전 메뉴로 돌아가기");
            System.out.print("선택 > "); int ch = scan.nextInt(); scan.nextLine();

            if(ch==1){incidentFindByYear();}
            else if(ch==2){}
            else if(ch==3){}
            else if(ch==4){}

        }
    }
    //연도별 보안 사고 검색
    public void incidentFindByYear(){

        System.out.print("연도 입력 > "); String incidentYear = scan.nextLine();
        ArrayList<IncidentDto>db=ic.incidentFindByYear(incidentYear);

        if (db.isEmpty()) {
            System.out.println("❌ 해당 연도(" + incidentYear + ")에 등록된 사고 내역이 없습니다.");
        }
        else {
            System.out.println("\n[ " + incidentYear + "년도 사고 검색 결과 ]");
            for (IncidentDto a : db) {
                System.out.printf(
                        "사고번호: %d, 발생연도: %s, 발생일: %s, 유형: %s, 상세내용: %s, 조치사항: %s, 승인상태: %s, 승인시간: %s, 기업번호: %d \n",
                        a.getIncidentId(),
                        a.getIncidentYear(),
                        a.getIncidentDate(),
                        a.getIncidentType(),
                        a.getIncidentDescription(),
                        a.getActionTaken(),
                        a.getApprovalStatus(),
                        a.getApprovalTime(),
                        a.getCompanyId()
                );
             }




    }


    //사고 유형별 보안 사고 검색


    //산업군별 보안 사고 검색


}//class end
