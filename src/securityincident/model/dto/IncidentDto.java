package securityincident.model.dto;

public class IncidentDto {
    private long incidentId;
    private String incidentYear;
    private String incidentDate;
    private String incidentType;
    private String incidentDescription;
    private String actionTaken;
    private String approvalStatus;
    private String approvalTime;
    private int companyId;

    public IncidentDto(){}
    public IncidentDto(long incidentId, String incidentYear, String incidentDate, String incidentType, String incidentDescription, String actionTaken, String approvalStatus, String approvalTime, int companyId) {
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

    public long getIncidentId() {
        return incidentId;
    }
    public void setIncidentId(long incidentId) {
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
}