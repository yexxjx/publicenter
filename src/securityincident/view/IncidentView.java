package securityincident.view;

import securityincident.controller.IncidentController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IncidentView {
    private IncidentView() {
    }

    private static final IncidentView instance = new IncidentView();

    public static IncidentView getInstance() {
        return instance;
    }

    private IncidentController ic = IncidentController.getInstance();

    private Scanner scan = new Scanner(System.in);


    // * 보안 사고 관리 (임시)
    public void index() {
        for ( ; ; ) {
            try {
                System.out.println("──┤ 보안 사고 관리 ├──────────────────────────────────────");
                System.out.println("1. 사고 목록 조회");
                System.out.println("2. 사고 등록");
                System.out.println("3. 사고 정보 수정");
                System.out.println("4. 사고 삭제");
                System.out.println("5. 관리자 메뉴로 돌아가기");
                System.out.print("선택 > ");
                int ch = scan.nextInt();
                if (ch == 1) {SearchView();}
                else if (ch == 2) { }
                else if (ch == 3) { }
                else if (ch == 4) { }
                else if (ch == 5 ){return;}
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("[시스템오류] 원인: " + e);
            }
        }
    }

    // 2. 검색 필터 페이지
    public void SearchView() {
        for (; ; ) {
            System.out.println("──┤ 사고 검색 / 필터 ├──────────────────────────────────────");
            System.out.println("1. 연도별 보안 사고 검색");
            System.out.println("2. 사고 유형별 검색");
            System.out.println("3. 산업군별 검색");
            System.out.println("4. 이전 메뉴로 돌아가기");
            System.out.print("선택 > ");
            int ch = scan.nextInt();
            if (ch == 1) {}
            else if (ch == 2) {}
            else if (ch == 3) {}
            else if (ch == 4) {}

        }
    }

}//class end