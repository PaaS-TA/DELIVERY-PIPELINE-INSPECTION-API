package paasta.delivery.pipeline.inspection.api.qualityGate;


import java.util.List;

/**
 * Created by Dojun on 2017-06-21.
 */
public class QualityGate {

    private long id;
    //    private String id;
    private String uuid;
    private String name;
    private String orgName;
    private String orgGuid;
    private String userName;
    private String userGuid;
    private List<Long> projectIdList;
    private String created;
    private String lastModified;
    private String createdString;
    private String lastModifiedString;
    private String resultStatus;
    private String resultMessage;

    private List conditions;
    //조건 명
    private List metrics;

    //게이트 조건 저장 목록
    private String gateId;
    private String metric;
    private String error;
    private String warning;
    private String op;

    private String serviceInstancesId;

    //프로젝트 연결상태
    private Boolean linked;

    //게이트 리스트
    private List qualitygates;
    private String defaultKey;


    private String gateDefaultYn;
    private List domains;


    //삭제 예정
    private String defaultYn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgGuid() {
        return orgGuid;
    }

    public void setOrgGuid(String orgGuid) {
        this.orgGuid = orgGuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public List<Long> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<Long> projectIdList) {
        this.projectIdList = projectIdList;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getCreatedString() {
        return createdString;
    }

    public void setCreatedString(String createdString) {
        this.createdString = createdString;
    }

    public String getLastModifiedString() {
        return lastModifiedString;
    }

    public void setLastModifiedString(String lastModifiedString) {
        this.lastModifiedString = lastModifiedString;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public List getMetrics() {
        return metrics;
    }

    public void setMetrics(List metrics) {
        this.metrics = metrics;
    }

    public List getConditions() {
        return conditions;
    }

    public void setConditions(List conditions) {
        this.conditions = conditions;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getServiceInstancesId() {
        return serviceInstancesId;
    }

    public void setServiceInstancesId(String serviceInstancesId) {
        this.serviceInstancesId = serviceInstancesId;
    }

    public Boolean getLinked() {
        return linked;
    }

    public void setLinked(Boolean linked) {
        this.linked = linked;
    }

    public List getQualitygates() {
        return qualitygates;
    }

    public void setQualitygates(List qualitygates) {
        this.qualitygates = qualitygates;
    }

    public String getDefaultKey() {
        return defaultKey;
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    public List getDomains() {
        return domains;
    }

    public void setDomains(List domains) {
        this.domains = domains;
    }

    public String getGateDefaultYn() {
        return gateDefaultYn;
    }

    public void setGateDefaultYn(String gateDefaultYn) {
        this.gateDefaultYn = gateDefaultYn;
    }

    public String getDefaultYn() {
        return defaultYn;
    }

    public void setDefaultYn(String defaultYn) {
        this.defaultYn = defaultYn;
    }

    @Override
    public String toString() {
        return "QualityGate{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgGuid='" + orgGuid + '\'' +
                ", userName='" + userName + '\'' +
                ", userGuid='" + userGuid + '\'' +
                ", projectIdList=" + projectIdList +
                ", created='" + created + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", createdString='" + createdString + '\'' +
                ", lastModifiedString='" + lastModifiedString + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", conditions=" + conditions +
                ", metrics=" + metrics +
                ", gateId='" + gateId + '\'' +
                ", metric='" + metric + '\'' +
                ", error='" + error + '\'' +
                ", warning='" + warning + '\'' +
                ", op='" + op + '\'' +
                ", serviceInstancesId='" + serviceInstancesId + '\'' +
                ", linked=" + linked +
                ", qualitygates=" + qualitygates +
                ", defaultKey='" + defaultKey + '\'' +
                ", gateDefaultYn='" + gateDefaultYn + '\'' +
                ", domains=" + domains +
                ", defaultYn='" + defaultYn + '\'' +
                '}';
    }
}
