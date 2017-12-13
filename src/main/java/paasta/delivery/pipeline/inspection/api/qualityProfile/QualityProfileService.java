package paasta.delivery.pipeline.inspection.api.qualityProfile;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


/**
 * The type Quality profile service.
 */
@Service
public class QualityProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QualityProfileService.class);

    @Autowired
    private CommonService commonService;

    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;


    /**
     * Get quality profile list list.
     *
     * @param qualityProfile the quality profile
     * @return the list
     */
    public List getQualityProfileList(QualityProfile qualityProfile){

        // [API] : /api/qualityprofiles/search
        String reqUrl = commonService.makeQueryParam(Constants.API_QUALITY_PROFILES_SEARCH, qualityProfile, null);

        QualityProfile profile = commonService.sendForm(inspectionServerUrl, reqUrl, HttpMethod.GET, null, QualityProfile.class);

        // real
        List qualityProfileList = (List) profile.getProfiles().stream().filter(e ->
        {
            if (((String)((LinkedHashMap)e).get("name")).startsWith(qualityProfile.getServiceInstanceId()+"^")) {
                return true;
            }
            if (((Boolean)((LinkedHashMap)e).get("isDefault")) && ((String)((LinkedHashMap)e).get("name")).toUpperCase().startsWith("DEFAULT^")) {
                return true;
            }
            return false;
        }).collect(toList());


        LOGGER.info("===[INSPECTION-API :: getQualityProfileList]=== qualityProfileList : {}", qualityProfileList.toString());


        return qualityProfileList;
    }

    /**
     * Getquality profile languages list.
     *
     * @return the list
     */
    public List getqualityProfileLanguages(){

        // [API] : /api/languages/list?ps=1&q=java

        String reqUrl = Constants.API_LANGUAGES_LIST;

        LOGGER.info("===[INSPECTION-API :: getqualityProfileLanguages]=== reqUrl : {}", reqUrl);
        LinkedHashMap map = (LinkedHashMap) commonService.sendForm(inspectionServerUrl , reqUrl, HttpMethod.GET, null, Map.class);

        List languages = (List) map.get(Constants.KEY_LANGUAGES);

        LOGGER.info("===[INSPECTION-API :: getqualityProfileLanguages]=== languages : {}", languages.toString());
        return languages;

    }

    /**
     * Create quality profile quality profile.
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    public QualityProfile createQualityProfile(QualityProfile qualityProfile){

        LinkedHashMap resultBody = (LinkedHashMap) commonService.sendForm(inspectionServerUrl , Constants.API_QUALITY_PROFILES_CREATE,HttpMethod.POST, qualityProfile, Map.class);

        ObjectMapper om = new ObjectMapper();
        QualityProfile result = om.convertValue(resultBody.get(Constants.KEY_PROFILE), QualityProfile.class);

        return result;

    }

    /**
     * Gets projects.
     *
     * @param qualityProfile the quality profile
     * @return the projects
     */
    public QualityProfile getProjects(QualityProfile qualityProfile) {

        // /api/qualityprofiles/projects?key=java-egov-qualityprofile-20090&selected=all

        String reqUrl = commonService.makeQueryParam(Constants.API_QUALITY_PROFILES_PROJECTS, qualityProfile, null);

        LOGGER.info("===[INSPECTION-API :: getProjectList]=== reqUrl : {}", reqUrl);

        QualityProfile resultBody = commonService.sendForm(inspectionServerUrl, reqUrl, HttpMethod.GET, null, QualityProfile.class);

        return resultBody;
    }

    /**
     * Copy quality profile quality profile.
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    public QualityProfile copyQualityProfile(QualityProfile qualityProfile) {

        // /api/qualityprofiles/copy
        QualityProfile resultBody = commonService.sendForm(inspectionServerUrl , Constants.API_QUALITY_PROFILES_COPY, HttpMethod.POST, qualityProfile, QualityProfile.class);

        return resultBody;

    }

    /**
     * Update quality profile quality profile.
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    public QualityProfile updateQualityProfile(QualityProfile qualityProfile) {

        // /api/qualityprofiles/rename
        commonService.sendForm(inspectionServerUrl , Constants.API_QUALITY_PROFILES_RENAME, HttpMethod.POST, qualityProfile, String.class);

        QualityProfile result = qualityProfile;
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return result;

    }

    /**
     * Delete quality profile quality profile.
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    public QualityProfile deleteQualityProfile(QualityProfile qualityProfile) {

        // /api/qualityprofiles/delete
        commonService.sendForm(inspectionServerUrl , Constants.API_QUALITY_PROFILES_DELETE, HttpMethod.POST, qualityProfile, String.class);

        QualityProfile result = new QualityProfile();
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return result;
    }

    /**
     * Activate rule quality profile.
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    public QualityProfile activateRule(QualityProfile qualityProfile) {

        // /api/qualityprofiles/activate_rule
        QualityProfile data = new QualityProfile();
        commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/activate_rule", HttpMethod.POST, qualityProfile, String.class);
        data.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return data;
    }

    /**
     * Deactivate rule quality profile.
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    public QualityProfile deactivateRule(QualityProfile qualityProfile) {

        // /api/qualityprofiles/deactivate_rule
        QualityProfile data = new QualityProfile();
        commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/deactivate_rule", HttpMethod.POST, qualityProfile, String.class);
        data.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return data;
    }

}
