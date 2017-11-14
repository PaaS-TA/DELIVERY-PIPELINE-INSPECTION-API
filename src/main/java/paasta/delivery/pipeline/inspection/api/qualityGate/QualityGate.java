package paasta.delivery.pipeline.inspection.api.qualityGate;


import java.util.List;

/**
 * Created by Dojun on 2017-06-21.
 */
public class QualityGate {

    // DATABASE COLUMNS :: BEGIN
    private long id; // pid
    private String serviceInstancesId;
    private long qualityGateId; // id -> qualityGateId
    private String qualityGateName; // name -> qualityGateName
    private String gateDefaultYn;
    private String created;
    private String lastModified;
    private String createdString;
    private String lastModifiedString;
    // DATABASE COLUMNS :: END

    private String uuid;
    private List<Long> projectIdList;
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

    //프로젝트 연결상태
    private boolean linked;

    //게이트 리스트
    private List qualityGates; // qualitygates -> qualityGates
    private String defaultKey;

    private List domains;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceInstancesId() {
        return serviceInstancesId;
    }

    public void setServiceInstancesId(String serviceInstancesId) {
        this.serviceInstancesId = serviceInstancesId;
    }

    public long getQualityGateId() {
        return qualityGateId;
    }

    public void setQualityGateId(long qualityGateId) {
        this.qualityGateId = qualityGateId;
    }

    public String getQualityGateName() {
        return qualityGateName;
    }

    public void setQualityGateName(String qualityGateName) {
        this.qualityGateName = qualityGateName;
    }

    public String getGateDefaultYn() {
        return gateDefaultYn;
    }

    public void setGateDefaultYn(String gateDefaultYn) {
        this.gateDefaultYn = gateDefaultYn;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Long> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<Long> projectIdList) {
        this.projectIdList = projectIdList;
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

    public List getConditions() {
        return conditions;
    }

    public void setConditions(List conditions) {
        this.conditions = conditions;
    }

    public List getMetrics() {
        return metrics;
    }

    public void setMetrics(List metrics) {
        this.metrics = metrics;
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

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public List getQualityGates() {
        return qualityGates;
    }

    public void setQualityGates(List qualityGates) {
        this.qualityGates = qualityGates;
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

}
