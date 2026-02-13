package securityincident.view;
// 작업자 이태현,
import securityincident.controller.CompanyController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    private MainView(){}
    private static final MainView instance = new MainView();
    public static MainView getInstance(){return instance;}

    private CompanyView cv;
    private AdminView av;

    Scanner scan = new Scanner(System.in);

    // 0. [공통] 메인 진입 화면
    public void index(){
        if(cv == null) {cv = CompanyView.getInstance();}
        if(av == null) {av = AdminView.getInstance();}
        for(;;){
            try {
                System.out.println("──┤ \uD83C\uDF1F\uD83D\uDD10 publiccenter Console \uD83D\uDD10\uD83C\uDF1F ├───────────────────────────────────");
                System.out.println("1. 기업 정보 조회\n2. 보안 사고 조회\n3. 사고 검색/필터\n4. 통계 보기\n5. 관리자 로그인\n6. 프로그램 종료\n");
                System.out.print("> 선택 : ");
                int ch = scan.nextInt();
                scan.nextLine();
                if (ch == 1) {companyIndex();}
                else if (ch == 2) { }
                else if (ch == 3) { }
                else if (ch == 4) { }
                else if (ch == 5) { av.adminLogin();}
                else if (ch == 6) { }
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            }catch (InputMismatchException e){
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan.nextLine(); // 입력 객체 초기화 (잘못된 입력값 제거)
            }catch (Exception e){ // Exception은 예외 중 슈퍼클래스로 모든 예외 처리가 가능하다.
                System.out.println("[시스템오류] 관리자에게 문의하세요.");
            }
        }//for end
    }//index end

    // 1. 기업 정보 조회 페이지
    public void companyIndex(){
        if(cv == null) {cv = CompanyView.getInstance();}
        for(;;){
            try {
                System.out.println("──┤ 기업 정보 조회 ├────────────────────────────────────────────────\n");
                System.out.println("1. 전체 기업 목록 조회\n2. 산업군별 기업 조회\n3. 기업 상세 정보 조회\n4. 이전 메뉴로 돌아가기\n\n");
                System.out.print("선택 > ");
                int ch = scan.nextInt();
                scan.nextLine();
                if (ch == 1) {cv.companyFindAll();}
                else if (ch == 2) { }
                else if (ch == 3) { }
                else if (ch == 4) { }
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            }catch (InputMismatchException e){
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan.nextLine(); // 입력 객체 초기화 (잘못된 입력값 제거)
            }catch (Exception e){ // Exception은 예외 중 슈퍼클래스로 모든 예외 처리가 가능하다.
                System.out.println("[시스템오류] 관리자에게 문의하세요.");
            }
        }//for end
    }//index end

    // 2. 보안 사고 조회 페이지

    // 3. 사고 검색/필터 페이지

    // 4. 통계 보기 페이지

    // 5. 관리자 페이지 <- adminView에 있습니다.
}