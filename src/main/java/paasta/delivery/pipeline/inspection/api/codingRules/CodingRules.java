package paasta.delivery.pipeline.inspection.api.codingRules;


import java.util.List;

public class CodingRules {

    private  Long id;
    private String name;
    private String key;
    private String orgName;
    private String languages;
    private String resultStatus;
    private String resultMessage;
    private String orgGuid;
    private String userName;
    private String userGuid;
    private List<Object> rulesList;
    private String severities;

    private String qprofile;
    private String activation;
    private String serviceInstancesId;
    private String q;
    private String ps;
    private String p;
    //룰 리스트 sort
    private String s;

    private String total;
    private List rules;

    //룰 상세 프로파일 연결 확인
    private String actives;

    //룰과 프로파일 연결
    private String rule_key;
    private String profile_key;
    private String severity;

    //룰에대한 프로파일 이슈 변경
    private Boolean reset;

    public String getName() {
        return name;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getOrgGuid() {
        return orgGuid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgGuid(String orgGuid) {
        this.orgGuid = orgGuid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getKey() {
        return key;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public List<Object> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<Object> rulesList) {
        this.rulesList = rulesList;
    }

    public String getSeverities() {
        return severities;
    }

    public void setSeverities(String severities) {
        this.severities = severities;
    }

    public String getQprofile() {
        return qprofile;
    }

    public void setQprofile(String qprofile) {
        this.qprofile = qprofile;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getServiceInstancesId() {
        return serviceInstancesId;
    }

    public void setServiceInstancesId(String serviceInstancesId) {
        this.serviceInstancesId = serviceInstancesId;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public List getRules() {
        return rules;
    }

    public void setRules(List rules) {
        this.rules = rules;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getActives() {
        return actives;
    }

    public void setActives(String actives) {
        this.actives = actives;
    }

    public Boolean getReset() {
        return reset;
    }

    public void setReset(Boolean reset) {
        this.reset = reset;
    }
    public String getRule_key() {
        return rule_key;
    }

    public void setRule_key(String rule_key) {
        this.rule_key = rule_key;
    }

    public String getProfile_key() {
        return profile_key;
    }

    public void setProfile_key(String profile_key) {
        this.profile_key = profile_key;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "CodingRules{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", orgName='" + orgName + '\'' +
                ", languages='" + languages + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", orgGuid='" + orgGuid + '\'' +
                ", userName='" + userName + '\'' +
                ", userGuid='" + userGuid + '\'' +
                ", rulesList=" + rulesList +
                ", severities='" + severities + '\'' +
                ", qprofile='" + qprofile + '\'' +
                ", activation='" + activation + '\'' +
                ", serviceInstancesId='" + serviceInstancesId + '\'' +
                ", q='" + q + '\'' +
                ", ps='" + ps + '\'' +
                ", p='" + p + '\'' +
                ", s='" + s + '\'' +
                ", total='" + total + '\'' +
                ", rules=" + rules +
                ", actives='" + actives + '\'' +
                ", rule_key='" + rule_key + '\'' +
                ", profile_key='" + profile_key + '\'' +
                ", severity='" + severity + '\'' +
                ", reset=" + reset +
                '}';
    }


}
