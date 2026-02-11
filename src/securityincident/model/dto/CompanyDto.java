package securityincident.model.dto;

public class CompanyDto {
    int companyId;
    String companyName;
    String headOffice;
    int foundedYear;
    String createdAt;
    int industryId;

    public CompanyDto(int companyId, String companyName, String headOffice, int foundedYear, String createdAt, int industryId) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.headOffice = headOffice;
        this.foundedYear = foundedYear;
        this.createdAt = createdAt;
        this.industryId = industryId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(int foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", headOffice='" + headOffice + '\'' +
                ", foundedYear=" + foundedYear +
                ", createdAt='" + createdAt + '\'' +
                ", industryId=" + industryId +
                '}';
    }
}
