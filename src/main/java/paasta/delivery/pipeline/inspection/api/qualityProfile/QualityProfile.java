package paasta.delivery.pipeline.inspection.api.qualityProfile;


import java.util.List;

/**
 * Created by Dojun on 2017-06-28.
 */
public class QualityProfile {

    // DATABASE COLUMNS :: BEGIN
    private long id; // pid
    private String serviceInstancesId;
    private long qualityProfileId; // id -> qualityProfileId
    private String qualityProfileName; // name -> qualityProfileName
    private String qualityProfileKey; // key -> qualityProfileKey
    private String language;
    private String languageName;
    private String profileDefaultYn;
    private int activeRuleCount;
    private int activeDeprecatedRuleCount;
    private String created;
    private String lastModified;
    private String createdString;
    private String lastModifiedString;
    // DATABASE COLUMNS :: END

    private String resultStatus;
    private String resultMessage;

    private List<Long> projectIdList;

    //프로파일 복제
    private String fromKey;
    private String toName;
    private String sonarKey;

    //프로파일 삭제
    private String profileKey;

    //언어 리스트
    private List languages;

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

    public long getQualityProfileId() {
        return qualityProfileId;
    }

    public void setQualityProfileId(long qualityProfileId) {
        this.qualityProfileId = qualityProfileId;
    }

    public String getQualityProfileName() {
        return qualityProfileName;
    }

    public void setQualityProfileName(String qualityProfileName) {
        this.qualityProfileName = qualityProfileName;
    }

    public String getQualityProfileKey() {
        return qualityProfileKey;
    }

    public void setQualityProfileKey(String qualityProfileKey) {
        this.qualityProfileKey = qualityProfileKey;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getProfileDefaultYn() {
        return profileDefaultYn;
    }

    public void setProfileDefaultYn(String profileDefaultYn) {
        this.profileDefaultYn = profileDefaultYn;
    }

    public int getActiveRuleCount() {
        return activeRuleCount;
    }

    public void setActiveRuleCount(int activeRuleCount) {
        this.activeRuleCount = activeRuleCount;
    }

    public int getActiveDeprecatedRuleCount() {
        return activeDeprecatedRuleCount;
    }

    public void setActiveDeprecatedRuleCount(int activeDeprecatedRuleCount) {
        this.activeDeprecatedRuleCount = activeDeprecatedRuleCount;
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

    public List<Long> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<Long> projectIdList) {
        this.projectIdList = projectIdList;
    }

    public String getFromKey() {
        return fromKey;
    }

    public void setFromKey(String fromKey) {
        this.fromKey = fromKey;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getSonarKey() {
        return sonarKey;
    }

    public void setSonarKey(String sonarKey) {
        this.sonarKey = sonarKey;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public List getLanguages() {
        return languages;
    }

    public void setLanguages(List languages) {
        this.languages = languages;
    }

}
