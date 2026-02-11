package securityincident.view;

import securityincident.controller.CompanyController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CompanyView {
    private CompanyView(){}
    private static final CompanyView instance=new CompanyView();
    public static CompanyView getInstance(){return instance;}

    private CompanyController cc=CompanyController.getInstance();

    public Scanner scan=new Scanner(System.in);
    public void index() {
        for ( ; ; ) {
            try {
                System.out.println("──┤ 기업 정보 관리 ├──────────────────────────────────────");
                System.out.println("1.기업 목록 조회ㅣ2.기업 등록|3.기업 수정ㅣ4.기업 삭제ㅣ5.관리자 메뉴로 돌아가기");
                System.out.println("선택 > ");
                int ch = scan.nextInt();
                if (ch == 1) {}
                else if (ch == 2) {companyAdd();}
                else if (ch == 3) {companyUpdate();}
                else if (ch == 4) {}
                else if (ch == 5 ){}
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan = new Scanner(System.in);
            } catch (Exception e) {
                System.out.println("[시스템오류] 원인: " + e);
            }
        }
    }
    public void companyAdd() {
        scan.nextLine();

        System.out.print("기업명: "); String companyName = scan.nextLine();
        System.out.print("본사위치: "); String headOffice = scan.nextLine();
        System.out.print("설립연도: "); int foundedYear = scan.nextInt();
        System.out.print("산업군아이디: "); int industryId = scan.nextInt();
        scan.nextLine();
        boolean result = cc.companyAdd(companyName, headOffice, foundedYear, industryId);
        if(result) {
            System.out.println("[안내] 등록 성공");
        } else {
            System.out.println("[경고] 등록 실패 (DB 오류 확인)");
        }
    }

    public void companyUpdate(){
        System.out.println("수정할 게시물 번호: "); int cno=scan.nextInt();
        System.out.println("기업번호: "); int companyId=scan.nextInt();
        scan.nextLine();
        System.out.println("기업명: "); String companyName=scan.nextLine();
        System.out.println("본사위치: "); String headOffice=scan.nextLine();
        System.out.println("설립연도: "); int foundedYear=scan.nextInt();
        System.out.println("산업군아이디: "); int industryId=scan.nextInt();
        boolean result=cc.companyUpdate(cno, companyId, companyName, headOffice, foundedYear, null, industryId);
        if(result){
            System.out.println("[안내] 게시물 수정 성공");}
        else{
            System.out.println("[경고] 게시물 수정 실패 또는 없는 게시물 번호 입니다.");}
    }
}
