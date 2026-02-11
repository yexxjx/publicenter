package securityincident.view;

import securityincident.controller.CompanyController;
import securityincident.model.dto.CompanyDto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// 작업자 이연지, 이태현,
public class CompanyView {
    private CompanyView(){}
    private static final CompanyView instance = new CompanyView();
    public static CompanyView getInstance(){return instance;}

    CompanyController cc = CompanyController.getInstance();
    MainView mv = MainView.getInstance();
    Scanner scan = new Scanner(System.in);

    // * 기업 전체 조회
    public ArrayList<CompanyDto> companyFindAll(){
        ArrayList<CompanyDto> companyDtos = cc.companyFindAll();
        for(;;){
            try {
                System.out.println("──┤ 전체 기업 목록 ├───────────────────────────────────────────────\n");
                System.out.println("기업ID | 기업명 | 산업군 | 본사 위치");
                System.out.println("------------------------------------");
                for(CompanyDto list : companyDtos){
                    System.out.printf("  %d  |  %s  |  %s  |  %s  ",
                            list.getCompanyId(), list.getCompanyName(), list.getIndustryId(), list.getHeadOffice());                }
                System.out.println("======================== 다음 동작 선택 ========================\n");
                System.out.println("1. 기업 상세 정보 조회");
                System.out.println("2. 이전 메뉴로 돌아가기");
                System.out.print("선택> ");                int ch = scan.nextInt();
                if (ch == 1) { }
                else if (ch == 2) { mv.index();}
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

}
