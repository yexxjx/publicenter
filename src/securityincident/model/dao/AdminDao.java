package securityincident.model.dao;

public class AdminDao {
    private AdminDao(){}
    private static final AdminDao instance = new AdminDao();
    public static AdminDao getInstance(){return instance;}

    private String password = "admin1234";

    // 1. 관리자 로그인
    public boolean adminLogin( String pw ){
        if(pw.equals(password)){return true;}
        return false;
    }
}
