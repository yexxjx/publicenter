package securityincident.model.dto;
// 작업자 이태현
public class AdminDto {
    private String password;

    public AdminDto(){}
    public AdminDto(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "AdminDto{" +
                "password='" + password + '\'' +
                '}';
    }
}
