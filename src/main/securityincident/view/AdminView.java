package main.securityincident.view;

import main.securityincident.controller.AdminController;
import main.securityincident.controller.IncidentController;
import main.securityincident.model.dto.IncidentDto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminView {
    private AdminView(){}
    private static final AdminView instance = new AdminView();
    public static AdminView getInstance(){return instance;}

    private AdminController ac;
    private CompanyView cv;
    private IncidentView icv;

    Scanner scan = new Scanner(System.in);

    // 1. 관리자 로그인 페이지
    public void adminLogin(){
        if(ac == null) {ac = AdminController.getInstance();}
        for(;;){
            try {
                System.out.println("──┤ 관리자 로그인 ├─────────────────────────────────────────\n[ 관리자 로그인 ]");
                System.out.print("관리자 비밀번호 입력 > ");   String pw = scan.nextLine();
                boolean result = ac.adminLogin(pw);
                if(result){
                    System.out.println("********\n\n[ 로그인 성공 ]\n관리자 권한으로 접속합니다.");
                    adminMenu();
                    if(ac.getLoinSession() == 0) {return;}
                }else{
                    System.out.println("********\n\n[ 로그인 실패 ]\n비밀번호가 올바르지 않습니다.\n다시 시도해주세요.");
                }
            }catch (InputMismatchException e){
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan = new Scanner(System.in); // 입력 객체 초기화 (잘못된 입력값 제거)
            }catch (Exception e){ // Exception은 예외 중 슈퍼클래스로 모든 예외 처리가 가능하다.
                System.out.println("[시스템오류] 관리자에게 문의하세요.");
            }
        }//for end
    }//adminLogin end

    // 2. 관리자 메인메뉴 (로그인 후)
    public void adminMenu(){ //메소드명 안 정해서 임의지정함
        if(ac == null) {ac = AdminController.getInstance();}
        if(cv == null) {cv = CompanyView.getInstance();}
        if(icv == null) {icv = IncidentView.getInstance();}
        for(;;){
            try{
                System.out.println("──┤ 관리자 메뉴 ├─────────────────────────────────────────");
                System.out.println("1. 기업 정보 관리");
                System.out.println("2. 보안 사고 관리");
                System.out.println("3. 사고 승인 처리");
                System.out.println("4. 크롤링 상태");
                System.out.println("5. 로그아웃\n");
                System.out.print("선택 > ");      int ch = scan.nextInt();
                if (ch == 1) {cv.index();}
                else if (ch == 2) {icv.incidentMenu();}
                else if (ch == 3) {

                    scan.nextLine();

                    System.out.println("──┤ 승인 대기 사고 목록 ├────────────────────────");

                    ArrayList<IncidentDto> list =
                            IncidentController.getInstance().findPendingIncidents();

                    if(list.isEmpty()){
                        System.out.println("승인 대기 사고가 없습니다.");
                        break;
                    }

                    for(IncidentDto dto : list){
                        System.out.println("사고번호: " + dto.getIncidentId()
                                + " | 기업: " + dto.getCompanyName()
                                + " | 유형: " + dto.getIncidentType()
                                + " | 날짜: " + dto.getIncidentDate());
                    }

                    System.out.print("승인할 사고 번호 입력 > ");
                    int incidentId = scan.nextInt();

                    boolean result =
                            IncidentController.getInstance().approveIncident(incidentId);

                    if(result){
                        System.out.println("[완료] 사고 승인 처리되었습니다.");
                    }else{
                        System.out.println("[실패] 승인 가능한 사고가 아닙니다.");
                    }
                }


                else if (ch == 4) {
                    scan.nextLine(); // 버퍼 정리
                    System.out.println("──┤ 크롤링 상태 수정 ├─────────────────────────────────────────");
                    System.out.print("크롤링 실행 ID 입력 > ");
                    int crawId = scan.nextInt();
                    scan.nextLine();

                    System.out.print("상태 입력 (SUCCESS/PARTIAL/FAIL) > ");
                    String status = scan.nextLine();

                    System.out.print("수집 기사 수 입력 > ");
                    int count = scan.nextInt();
                    scan.nextLine();

                    System.out.print("메시지 입력 > ");
                    String msg = scan.nextLine();

                    boolean result = ac.updateCrawling(crawId, status, count, msg);

                    if(result){
                        System.out.println("[완료] 크롤링 상태가 수정되었습니다.");
                    }else{
                        System.out.println("[실패] 해당 크롤링 ID를 찾을 수 없습니다.");
                    }
                }

                else if (ch == 5) {adminLogout(); return;}
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            }catch (InputMismatchException e){
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan = new Scanner(System.in);
            }catch (Exception e){
                System.out.println("[시스템오류] 관리자에게 문의하세요.");e.printStackTrace();
            }
        }
    }

    // 3. 로그아웃
    public void adminLogout(){
        for(;;){
            try{
                System.out.println("정말 로그아웃 하시겠습니까?\n1. 예\n2. 아니오");
                System.out.print("선택 > ");      int ch = scan.nextInt();
                if (ch == 1){ac.adminLogout();System.out.println("[ 로그아웃 완료 ]\n"+"메인 메뉴로 돌아갑니다.");return;}
                else if (ch == 2) {return;}
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            }catch (InputMismatchException e){
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan = new Scanner(System.in);
            }catch (Exception e){
                System.out.println("[시스템오류] 관리자에게 문의하세요.");e.printStackTrace();
            }
        }
    }

}