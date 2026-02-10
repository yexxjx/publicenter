package securityincident.model.dto;

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
