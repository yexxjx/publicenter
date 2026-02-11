package securityincident.view;
// 작업자 이태현
import securityincident.controller.AdminController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminView {
    private AdminView(){}
    private static final AdminView instance = new AdminView();
    public static AdminView getInstance(){return instance;}

    private AdminController ac = AdminController.getInstance();
    private CompanyView cv = CompanyView.getInstance();

    Scanner scan = new Scanner(System.in);

    // 1. 관리자 로그인 페이지
    public void adminLogin(){
        for(;;){
            try {
                scan.nextLine();
                System.out.println("▶ 메뉴 선택 : 5\n──┤ 관리자 로그인 ├─────────────────────────────────────────\n[ 관리자 로그인 ]");
                System.out.print("관리자 비밀번호 입력 > ");   String pw = scan.nextLine();
                boolean result = ac.adminLogin(pw);
                if(result){
                    System.out.println("********\n\n[ 로그인 성공 ]\n관리자 권한으로 접속합니다.");
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
                else if (ch == 2) {}
                else if (ch == 3) {}
                else if (ch == 4) {}
                else if (ch == 5) {ac.adminLogout();}
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            }catch (InputMismatchException e){
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan = new Scanner(System.in);
            }catch (Exception e){
                System.out.println("[시스템오류] 관리자에게 문의하세요.");
            }
        }
    }

}

