package securityincident.model.dto;

public class CompanyDto {
    private int companyId;
    private String companyName;
    private String headOffice;
    private int foundedYear;
    private String createdAt;
    private int industryId;
    private String industryIdName;
    private int incidentCount;
    private String lastDate;

    public CompanyDto(){}

    public CompanyDto(int industryId, String createdAt, int foundedYear, String headOffice, String companyName, int companyId) {
        this.industryId = industryId;
        this.createdAt = createdAt;
        this.foundedYear = foundedYear;
        this.headOffice = headOffice;
        this.companyName = companyName;
        this.companyId = companyId;
    }
    public CompanyDto(int companyId, String companyName, String headOffice, int foundedYear, String createdAt, int industryId, String industryIdName) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.headOffice = headOffice;
        this.foundedYear = foundedYear;
        this.createdAt = createdAt;
        this.industryId = industryId;
        this.industryIdName = industryIdName;
    }
    public CompanyDto(int companyId, String companyName, String headOffice, int foundedYear, String createdAt, int industryId, String industryIdName, int incidentCount, String lastDate) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.headOffice = headOffice;
        this.foundedYear = foundedYear;
        this.createdAt = createdAt;
        this.industryId = industryId;
        this.industryIdName = industryIdName;
        this.incidentCount = incidentCount;
        this.lastDate = lastDate;
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

    public String getIndustryIdName() { return industryIdName; }

    public void setIndustryIdName(String industryIdName) { this.industryIdName = industryIdName; }

    public int getIncidentCount() {
        return incidentCount;
    }

    public void setIncidentCount(int incidentCount) {
        this.incidentCount = incidentCount;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }


}
