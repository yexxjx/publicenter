package securityincident.model.dto;

public class IncidentDto {

    // 멤버변수 - 이한승
    private int incidentId;
    private String incidentYear;
    private String incidentDate;
    private String incidentType;
    private String incidentDescription;
    private String actionTaken;
    private String approvalStatus;
    private String approvalTime;
    private int companyId;
    private String companyName;
    private String industryName;

    // 기본 생성자
    public IncidentDto(){}

    // 전체 생성자
    public IncidentDto(int incidentId, String incidentYear, String incidentDate, String incidentType, String incidentDescription, String actionTaken, String approvalStatus, String approvalTime, int companyId, String companyName) {
        this.incidentId = incidentId;
        this.incidentYear = incidentYear;
        this.incidentDate = incidentDate;
        this.incidentType = incidentType;
        this.incidentDescription = incidentDescription;
        this.actionTaken = actionTaken;
        this.approvalStatus = approvalStatus;
        this.approvalTime = approvalTime;
        this.companyId = companyId;
        this.companyName = companyName;
    }


    // 조회
    public IncidentDto(int incidentId, String incidentYear, String incidentDate, String incidentType, String incidentDescription, String actionTaken, String approvalStatus, String approvalTime, int companyId) {
        this.incidentId = incidentId;
        this.incidentYear = incidentYear;
        this.incidentDate = incidentDate;
        this.incidentType = incidentType;
        this.incidentDescription = incidentDescription;
        this.actionTaken = actionTaken;
        this.approvalStatus = approvalStatus;
        this.approvalTime = approvalTime;
        this.companyId = companyId;
    }



    // 기업명 생성자
    public IncidentDto(int incidentId, String companyName, String incidentType, String incidentDate) {
        this.incidentId = incidentId;
        this.companyName = companyName;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
    }
    public IncidentDto(int incidentId, String companyName, String industryName, String incidentType, String incidentDate, String approvalStatus) {
        this.incidentId = incidentId;
        this.companyName = companyName;
        this.industryName = industryName;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
        this.approvalStatus = approvalStatus;
    }


    public IncidentDto(int incidentId, String companyName, String industryName, String incidentType, String incidentDate, String approvalStatus, String incidentDescription, String actionTaken) {
        this.incidentId = incidentId;
        this.companyName = companyName;
        this.industryName = industryName;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
        this.approvalStatus = approvalStatus;
        this.incidentDescription = incidentDescription;
        this.actionTaken = actionTaken;
    }

    public IncidentDto(int incidentId, String incidentType, String incidentDate) {
        this.incidentId = incidentId;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
    }

    // getter setter - 이한승
    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public String getIncidentYear() {
        return incidentYear;
    }

    public void setIncidentYear(String incidentYear) {
        this.incidentYear = incidentYear;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @Override
    public String toString() {
        return "IncidentDto{" +
                "incidentId=" + incidentId +
                ", incidentYear='" + incidentYear + '\'' +
                ", incidentDate='" + incidentDate + '\'' +
                ", incidentType='" + incidentType + '\'' +
                ", incidentDescription='" + incidentDescription + '\'' +
                ", actionTaken='" + actionTaken + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", approvalTime='" + approvalTime + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", industryName='" + industryName + '\'' +
                '}';
    }
}