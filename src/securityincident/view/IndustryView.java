package securityincident.view;

import securityincident.controller.IndustryController;
import securityincident.model.dto.IndustryDto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IndustryView {
    private IndustryView(){}
    private static final IndustryView instance=new IndustryView();
    public static IndustryView getInstance(){return instance;}

    private IndustryController ic=IndustryController.getInstance();

    public Scanner scan=new Scanner(System.in);
    public void index() {
        for (; ; ) {
            try {
                System.out.println("──┤ 기업 정보 관리 ├──────────────────────────────────────");
                System.out.println("1.산업군 조회|2.산업군 등록ㅣ3.산업군 수정ㅣ4.산업군 삭제");
                System.out.println("선택 > ");
                int ch = scan.nextInt();
                if (ch == 1) {industryFindAll();}
                else if (ch == 2) {industryAdd();}
                else if (ch == 3) {industryUpdate();}
                else if (ch == 4) {industryDelete();}
                else {
                    System.out.println("[경고] 없는 기능 번호입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[경고] 잘못된 입력 방식입니다. [재입력]");
                scan = new Scanner(System.in);
            } catch (Exception e) {
                System.out.println("[시스템오류] 관리자에게 문의");
            }
        }
    }

    // 산업군 전체 조회
    public void industryFindAll(){
        if(ic == null) ic = IndustryController.getInstance();
        ArrayList<IndustryDto> industryDtos = ic.industryFindAll();
        for(;;){
            try {
                System.out.println("──┤ 전체 기업 목록 ├───────────────────────────────────────────────\n");
                System.out.println("산업군ID |   산업군");
                System.out.println("------------------------------------");
                for(IndustryDto list : industryDtos){
                    System.out.printf("  %d  |  %s  \n",
                            list.getIndustryId(), list.getIndustryName());
                }

                System.out.println("======================== 다음 동작 선택 ========================");
                System.out.println("1. 기업 상세 정보 조회");
                System.out.println("2. 이전 메뉴로 돌아가기");
                System.out.print("선택> ");
                int ch = scan.nextInt();
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
        }
    }

    // 산업군 등록
    public void industryAdd(){
        scan.nextLine();
        System.out.println("산업군ID: "); int industryId=scan.nextInt();
        System.out.println("산업군: "); String industryName=scan.next();
        boolean result=ic.industryAdd(industryId, industryName);
        if(result){
            System.out.println("[안내] 산업군 등록 완료");}
        else{
            System.out.println("[경고] 산업군 등록 실패");}
    }

    // 산업군 수정
    public void industryUpdate(){
        System.out.println("수정할 산업군 번호"); int ino=scan.nextInt();
        scan.nextLine();
        System.out.println("수정할 산업군ID"); int industryId=scan.nextInt();
        System.out.println("수정할 산업군"); String industryName=scan.nextLine();
        boolean result=ic.industryUpdate(ino, industryId, industryName);
        if(result){
            System.out.println("[안내] 사업군 수정 성공");}
        else{
            System.out.println("[경고] 산업군 수정 실패 또는 없는 산업군 번호 입니다.");}
    }

    // 산업군 삭제
    public void industryDelete(){
        System.out.println("삭제할 산업군 번호");
        int ino=scan.nextInt();
        boolean result=ic.industryDelete(ino);
        if(result) {
            System.out.println("[안내] 산업군 삭제 성공");
        }else{
                System.out.println("[경고] 산업군 삭제 실패 또는 없는 산업군 번호 입니다.");}
        }


}
