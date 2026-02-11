package securityincident.view;

import securityincident.controller.IncidentController;
import securityincident.model.dao.IncidentDao;

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
    //1.1. 보안사고등록 view
    public void incidentAddByAdmin() {

        System.out.println("===== 사고 등록 =====");

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


}//class end
