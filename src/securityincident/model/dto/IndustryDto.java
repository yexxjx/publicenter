package securityincident.model.dto;

public class IndustryDto {
    int industryId;
    String industryName;

    public IndustryDto(){}

    public IndustryDto(int industryId, String industryName) {
        this.industryId = industryId;
        this.industryName = industryName;
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
                "industryId=" + industryId +
                ", industryName='" + industryName + '\'' +
                '}';
    }
}
