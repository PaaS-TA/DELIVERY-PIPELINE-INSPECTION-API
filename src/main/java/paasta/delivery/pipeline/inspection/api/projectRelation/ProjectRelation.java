package paasta.delivery.pipeline.inspection.api.projectRelation;

/**
 * Created by Dojun on 2017-07-06.
 */
public class ProjectRelation {

    private Long id; // projectId 와 동일.
    private Long qualityProfileId;
    private Long qualityGateId;
    private String created;
    private String lastModified;
    private String createdString;
    private String lastModifiedString;
    private String resultStatus;
    private String resultMessage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQualityProfileId() {
        return qualityProfileId;
    }

    public void setQualityProfileId(Long qualityProfileId) {
        this.qualityProfileId = qualityProfileId;
    }

    public Long getQualityGateId() {
        return qualityGateId;
    }

    public void setQualityGateId(Long qualityGateId) {
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

    @Override
    public String toString() {
        return "ProjectRelation{" +
                "id=" + id +
                ", qualityProfileId=" + qualityProfileId +
                ", qualityGateId=" + qualityGateId +
                ", created='" + created + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", createdString='" + createdString + '\'' +
                ", lastModifiedString='" + lastModifiedString + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
