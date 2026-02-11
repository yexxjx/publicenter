package securityincident.view;

import securityincident.controller.AdminController;
import securityincident.controller.CompanyController;
import securityincident.model.dto.CompanyDto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CompanyView {
    private CompanyView(){}
    private static final CompanyView instance = new CompanyView();
    public static CompanyView getInstance(){return instance;}

    private CompanyController cc;

    public Scanner scan = new Scanner(System.in);
    public void index() {
        for ( ; ; ) {
            try {
                System.out.println("──┤ 기업 정보 관리 ├──────────────────────────────────────");
                System.out.println("1.기업 목록 조회ㅣ2.기업 등록|3.기업 수정ㅣ4.기업 삭제ㅣ5.관리자 메뉴로 돌아가기");
                System.out.print("선택 > ");
                int ch = scan.nextInt();
                if (ch == 1) {companyFindAll();}
                else if (ch == 2) {companyAdd();}
                else if (ch == 3) {companyUpdate();}
                else if (ch == 4) {companyDelete();}
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

    // * 기업 전체 조회
    public void companyFindAll(){
        if(cc == null) cc = CompanyController.getInstance();
        ArrayList<CompanyDto> companyDtos = cc.companyFindAll();
        for(;;){
            try {
                System.out.println("──┤ 전체 기업 목록 ├───────────────────────────────────────────────\n");
                System.out.println("기업ID |   기업명   |   산업군   |   본사 위치");
                System.out.println("------------------------------------");
                for(CompanyDto list : companyDtos){
                    System.out.printf("  %d  |  %s  |  %s  |  %s  \n",
                            list.getCompanyId(), list.getCompanyName(), list.getIndustryIdName(), list.getHeadOffice());                }
                System.out.println("======================== 다음 동작 선택 ========================");
                System.out.println("1. 기업 상세 정보 조회");
                System.out.println("2. 이전 메뉴로 돌아가기");
                System.out.print("선택> ");                int ch = scan.nextInt();
                if (ch == 1) { }
                else if (ch == 2) { return;}
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            }catch (InputMismatchException e){
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan = new Scanner(System.in);
            }catch (Exception e){
                System.out.println("[시스템오류] 관리자에게 문의하세요.");
            }
        }//for end
    }

    public void companyAdd() {
        if(cc == null) cc = CompanyController.getInstance();
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
        if(cc == null) {cc = CompanyController.getInstance();}
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

    public void companyDelete(){
        if(cc == null) {cc = CompanyController.getInstance();}
        System.out.println("삭제할 게시물 번호");
        int cno=scan.nextInt();
        boolean result=cc.companyDelete(cno);
        if(result){
            System.out.println("[안내] 게시물 삭제 성공");}
        else{
            System.out.println("[경고] 게시물 삭제 실패 또는 없는 게시물 번호 입니다.");}
    }
}