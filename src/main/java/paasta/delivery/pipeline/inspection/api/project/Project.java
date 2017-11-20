package paasta.delivery.pipeline.inspection.api.project;

import java.util.List;

/**
 * Created by Dojun on 2017-06-19.
 */
public class Project {

    // DATABASE COLUMNS :: BEGIN
    private long id; // pid
    private String serviceInstancesId;
    private long pipelineId;
    private long jobId;
    private long projectId; // id -> projectId
    private String projectName;
    private String projectKey;  //자동생성 uuid
    private String qualityProfileKey;
    private long qualityGateId;
    private String created;
    private String lastModified;
    private String createdString;
    private String lastModifiedString;
    // DATABASE COLUMNS :: END

    private String qualifier;
    private String resultStatus;
    private String resultMessage;
    private String branch;

    //프로젝트와 게이트, 프로파일 연결 상태
    private boolean linked;

    //sona에서 사용하는 id(gate)
    private String gateId;

    //sonar에서 사용하는 id(profile)
    private String profileKey;

    //품질관리 대시보드
    private Object measures;
    private String uuid;

    //품질관리 커버리지
    private String resource;
    private List msr;
    private String metrics;

    //tests소스
    private Object baseComponent;
    private String baseComponentKey;
    private List components;
    private List sources;
    private List scm;
    private List issues;

    //프로젝트 추가, 수정시 게이트와 프로파일 default 상태
    private String gateDefaultYn;
    private String profileDefaultYn;
    private String projectViewName;

    private String defaultQualityProfileKey;
    private long defaultQualityGateId;

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

    public long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(long pipelineId) {
        this.pipelineId = pipelineId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getQualityProfileKey() {
        return qualityProfileKey;
    }

    public void setQualityProfileKey(String qualityProfileKey) {
        this.qualityProfileKey = qualityProfileKey;
    }

    public long getQualityGateId() {
        return qualityGateId;
    }

    public void setQualityGateId(long qualityGateId) {
        this.qualityGateId = qualityGateId;
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

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public Object getMeasures() {
        return measures;
    }

    public void setMeasures(Object measures) {
        this.measures = measures;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List getMsr() {
        return msr;
    }

    public void setMsr(List msr) {
        this.msr = msr;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public Object getBaseComponent() {
        return baseComponent;
    }

    public void setBaseComponent(Object baseComponent) {
        this.baseComponent = baseComponent;
    }

    public String getBaseComponentKey() {
        return baseComponentKey;
    }

    public void setBaseComponentKey(String baseComponentKey) {
        this.baseComponentKey = baseComponentKey;
    }

    public List getComponents() {
        return components;
    }

    public void setComponents(List components) {
        this.components = components;
    }

    public List getSources() {
        return sources;
    }

    public void setSources(List sources) {
        this.sources = sources;
    }

    public List getScm() {
        return scm;
    }

    public void setScm(List scm) {
        this.scm = scm;
    }

    public List getIssues() {
        return issues;
    }

    public void setIssues(List issues) {
        this.issues = issues;
    }

    public String getGateDefaultYn() {
        return gateDefaultYn;
    }

    public void setGateDefaultYn(String gateDefaultYn) {
        this.gateDefaultYn = gateDefaultYn;
    }

    public String getProfileDefaultYn() {
        return profileDefaultYn;
    }

    public void setProfileDefaultYn(String profileDefaultYn) {
        this.profileDefaultYn = profileDefaultYn;
    }

    public String getProjectViewName() {
        return projectViewName;
    }

    public void setProjectViewName(String projectViewName) {
        this.projectViewName = projectViewName;
    }

    public String getDefaultQualityProfileKey() {
        return defaultQualityProfileKey;
    }

    public void setDefaultQualityProfileKey(String defaultQualityProfileKey) {
        this.defaultQualityProfileKey = defaultQualityProfileKey;
    }

    public long getDefaultQualityGateId() {
        return defaultQualityGateId;
    }

    public void setDefaultQualityGateId(long defaultQualityGateId) {
        this.defaultQualityGateId = defaultQualityGateId;
    }

}
