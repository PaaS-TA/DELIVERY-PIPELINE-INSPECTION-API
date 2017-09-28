package paasta.delivery.pipeline.inspection.api.qualityProfile;

import java.util.Date;
import java.util.List;

/**
 * Created by Dojun on 2017-06-28.
 */
public class QualityProfile {


    private Long id;
    private String key;
    private String name;
    private String language;
    private String languageName;
    private List<Long> projectIdList;
    private int activeRuleCount;
    private int activeDeprecatedRuleCount;
    private Date created;
    private Date lastModified;
    private String createdString;
    private String lastModifiedString;
    private String resultStatus;
    private String resultMessage;
    private String serviceInstancesId;
    private String defaultYn;
    //////////////////////
    //프로파일 복제
    private String fromKey;
    private String toName;
    private String sonarKey;

    //프로파일 삭제
    private String profileKey;

    //언어 리스트
    private List languages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Long> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<Long> projectIdList) {
        this.projectIdList = projectIdList;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
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

    public String getServiceInstancesId() {
        return serviceInstancesId;
    }

    public void setServiceInstancesId(String serviceInstancesId) {
        this.serviceInstancesId = serviceInstancesId;
    }

    public String getDefaultYn() {
        return defaultYn;
    }

    public void setDefaultYn(String defaultYn) {
        this.defaultYn = defaultYn;
    }

    @Override
    public String toString() {
        return "QualityProfile{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", languageName='" + languageName + '\'' +
                ", projectIdList=" + projectIdList +
                ", activeRuleCount=" + activeRuleCount +
                ", activeDeprecatedRuleCount=" + activeDeprecatedRuleCount +
                ", created=" + created +
                ", lastModified=" + lastModified +
                ", createdString='" + createdString + '\'' +
                ", lastModifiedString='" + lastModifiedString + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", serviceInstancesId='" + serviceInstancesId + '\'' +
                ", defaultYn='" + defaultYn + '\'' +
                ", fromKey='" + fromKey + '\'' +
                ", toName='" + toName + '\'' +
                ", sonarKey='" + sonarKey + '\'' +
                ", profileKey='" + profileKey + '\'' +
                ", languages=" + languages +
                '}';
    }
}
