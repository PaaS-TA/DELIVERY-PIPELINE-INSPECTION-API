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
     * @param serviceInstancesId the service instances id
     * @return the list
     */
    public List getQualityProfileList(String serviceInstancesId){

        // [API] : /api/qualityprofiles/search
        LOGGER.info("===[INSPECTION-API :: getQualityProfileList]=== reqUrl : {}", Constants.API_QUALITYPROFILES_SEARCH);
        LinkedHashMap profile = (LinkedHashMap) commonService.sendForm(inspectionServerUrl , Constants.API_QUALITYPROFILES_SEARCH,HttpMethod.GET, null, Map.class);

        List profiles = (List) profile.get(Constants.KEY_PROFILES);

        // real
        List qualityProfileList = (List) profiles.stream().filter(e ->
        {
            if (((String)((LinkedHashMap)e).get("name")).startsWith(serviceInstancesId+"^")) {
                return true;
            }
            if (((Boolean)((LinkedHashMap)e).get("isDefault")) || ((String)((LinkedHashMap)e).get("name")).startsWith("DEFAULT"+"^")) {
                return true;
            }
            //temp :: test용
            if (((String)((LinkedHashMap)e).get("name")).startsWith("lena-") || ((String)((LinkedHashMap)e).get("name")).startsWith("rex-") ) {
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

        QualityProfile result = new QualityProfile();

        LinkedHashMap resultBody = (LinkedHashMap) commonService.sendForm(inspectionServerUrl , Constants.API_QUALITYPROFILES_CREATE,HttpMethod.POST, qualityProfile, Map.class);

        ObjectMapper om = new ObjectMapper();
        result = om.convertValue(resultBody.get(Constants.KEY_PROFILE), QualityProfile.class);
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
//        String reqUrl = Constants.API_QUALITYPROFILES_PROJECTS + "?key="+key;

        String reqUrl = commonService.makeQueryParam(inspectionServerUrl+Constants.API_QUALITYPROFILES_PROJECTS, qualityProfile);


        LOGGER.info("===[INSPECTION-API :: getProjectList]=== reqUrl : {}", reqUrl);

        QualityProfile resultBody = commonService.sendForm(reqUrl, HttpMethod.GET, null, QualityProfile.class);

        return resultBody;
    }



    //TODO -----------------------------------------------------------------
    /**
     * qualityProfile 복제
     *
     * @param qualityProfile the quality profile
     * @return quality profile
     */
    public QualityProfile qualityProfileCopy(QualityProfile qualityProfile){
        QualityProfile result = new QualityProfile();

        result = commonService.sendForm(inspectionServerUrl , "/api/qualityprofiles/copy",HttpMethod.POST, qualityProfile, QualityProfile.class);

        result.setServiceInstanceId(qualityProfile.getServiceInstanceId());
//        result.setProfileDefaultYn(qualityProfile.getProfileDefaultYn());

        //sona에서 가져오 키값 셋팅해서 db로 저장
        result = commonService.sendForm(commonApiUrl , "/qualityProfile/qualityProfileCopy",HttpMethod.POST, qualityProfile, QualityProfile.class);
        return  result;
    }

    /**
     * qualityProfile 삭제
     *
     * @param qualityProfile the quality profile
     * @return quality profile
     */
    public QualityProfile deleteQualityProfile(QualityProfile qualityProfile){
        QualityProfile result = new QualityProfile();
        commonService.sendForm(inspectionServerUrl , "/api/qualityprofiles/delete",HttpMethod.POST, qualityProfile, null);
        commonService.sendForm(commonApiUrl , "/qualityProfile/qualityProfileDelete",HttpMethod.DELETE, qualityProfile, String.class);
        commonService.sendForm(commonApiUrl,"/project/qualityProfileDelete",HttpMethod.PUT,qualityProfile,String.class);
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return result;
    }

    /**
     * qualityProfile 수정
     *
     * @param qualityProfile the quality profile
     * @return quality profile
     */
    public QualityProfile updateQualityProfile(QualityProfile qualityProfile){
        QualityProfile result = new QualityProfile();
        commonService.sendForm(inspectionServerUrl , "/api/qualityprofiles/rename",HttpMethod.POST, qualityProfile, null);
        result = commonService.sendForm(commonApiUrl , "/qualityProfile/qualityProfileUpdate",HttpMethod.PUT, qualityProfile, QualityProfile.class);
        return result;
    }





    /**
     * QualityProfile 한건 검색
     *
     * @param id the id
     * @return QualityProfile quality profile
     */
    public QualityProfile getQualityProfile(long id){

        //TODO QualityProfile 한건 검색
        return commonService.sendForm(commonApiUrl,"/qualityProfile/getQualityProfile?id="+id,HttpMethod.GET,null,QualityProfile.class);
    }
}
