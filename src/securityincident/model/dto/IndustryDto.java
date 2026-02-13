package securityincident.model.dto;

public class IndustryDto {
    int ino;
    int industryId;
    String industryName;

    public IndustryDto(){}

    public IndustryDto(int ino, int industryId, String industryName) {
        this.ino = ino;
        this.industryId = industryId;
        this.industryName = industryName;
    }

    public int getIno() {
        return ino;
    }

    public void setIno(int ino) {
        this.ino = ino;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @Override
    public String toString() {
        return "IndustryDto{" +
                "ino=" + ino +
                ", industryId=" + industryId +
                ", industryName='" + industryName + '\'' +
                '}';
    }
}
