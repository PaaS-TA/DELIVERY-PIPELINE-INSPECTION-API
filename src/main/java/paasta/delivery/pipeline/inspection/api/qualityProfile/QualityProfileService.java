package paasta.delivery.pipeline.inspection.api.qualityProfile;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.*;

/**
 * Created by Dojun on 2017-06-19.
 */
@Service
public class QualityProfileService {

    private final CommonService commonService;

    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;

    @Autowired
    QualityProfileService(CommonService commonService) {this.commonService = commonService;}


    /**
     * 품질 프로파일 목록 조회
     * @return Map
     */
    public List getQualityProfileList(String serviceInstancesId){
        return commonService.sendForm(commonApiUrl , "/qualityProfile/qualityProfileList?serviceInstancesId="+serviceInstancesId,HttpMethod.GET, null, List.class);
    }

    /**
     * qualityProfile 복제
     * @return
     */
    public QualityProfile qualityProfileCopy(QualityProfile qualityProfile){
        QualityProfile result = new QualityProfile();

        result = commonService.sendForm(inspectionServerUrl , "/api/qualityprofiles/copy",HttpMethod.POST, qualityProfile, QualityProfile.class);

        result.setServiceInstancesId(qualityProfile.getServiceInstancesId());
        result.setProfileDefaultYn(qualityProfile.getProfileDefaultYn());

        //sona에서 가져오 키값 셋팅해서 db로 저장
        result = commonService.sendForm(commonApiUrl , "/qualityProfile/qualityProfileCopy",HttpMethod.POST, qualityProfile, QualityProfile.class);
        return  result;
    }

    /**
     * qualityProfile 삭제
     * @return
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
     * @return
     */
    public QualityProfile updateQualityProfile(QualityProfile qualityProfile){
        QualityProfile result = new QualityProfile();
        commonService.sendForm(inspectionServerUrl , "/api/qualityprofiles/rename",HttpMethod.POST, qualityProfile, null);
        result = commonService.sendForm(commonApiUrl , "/qualityProfile/qualityProfileUpdate",HttpMethod.PUT, qualityProfile, QualityProfile.class);
        return result;
    }

    /**
     * qualityProfile 언어 리스트
     * @return
     */
    public QualityProfile qualityProfileLangList(){
        return commonService.sendForm(inspectionServerUrl , "/api/languages/list",HttpMethod.GET, null, QualityProfile.class);
    }

    /**
     * 품질 프로파일 생성
     * @param qualityProfile
     * @return qualityProfile
     */
    QualityProfile createQualityProfile(QualityProfile qualityProfile) {

        QualityProfile resultModel = new QualityProfile();
        JsonNode result = commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/create", HttpMethod.POST, qualityProfile, JsonNode.class);

        qualityProfile.setQualityProfileName(result.get("profile").get("name").asText());
        qualityProfile.setQualityProfileKey(result.get("profile").get("key").asText());
        qualityProfile.setLanguage(result.get("profile").get("language").asText());
        qualityProfile.setLanguageName(result.get("profile").get("languageName").asText());
        resultModel = commonService.sendForm(commonApiUrl, "/qualityProfile/qualityProfilCreate",HttpMethod.POST, qualityProfile, QualityProfile.class);

        return resultModel;
    }

    /**
     *  QualityProfile default setting
     *
     * @param
     * @return
     */
/*    public QualityProfile defaultQualityProfile(QualityProfile qualityProfile){

        commonService.sendForm(inspectionServerUrl , "/api/qualityprofiles/set_default",HttpMethod.POST, qualityProfile, QualityProfile.class);
        commonService.sendForm(commonApiUrl,"/qualityProfile/qualityProfilDefaultSetting",HttpMethod.PUT,qualityProfile,String.class);
        qualityProfile.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return qualityProfile;
    }*/

    /**
     *  QualityProfile codingRules
     *
     * @param qualityProfile
     * @return list
     */
    public List getCodingRulesList(QualityProfile qualityProfile){
        return commonService.sendForm(inspectionServerUrl,"/api/profiles?language="+qualityProfile.getLanguage()+"&name="+qualityProfile.getQualityProfileName(),HttpMethod.GET,null,List.class);
    }


    /**
     *  QualityProfile 한건 검색
     *
     * @param  id
     * @return QualityProfile
     */
    public QualityProfile getQualityProfile(long id){
        return commonService.sendForm(commonApiUrl,"/qualityProfile/getQualityProfile?id="+id,HttpMethod.GET,null,QualityProfile.class);
    }

}
