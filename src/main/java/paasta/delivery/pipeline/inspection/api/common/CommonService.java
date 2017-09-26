package paasta.delivery.pipeline.inspection.api.common;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import paasta.delivery.pipeline.inspection.api.project.Project;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGate;
import paasta.delivery.pipeline.inspection.api.qualityProfile.QualityProfile;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * paastaDeliveryPipelineApi
 * paasta.delivery.pipeline.api.common
 *
 * @author REX
 * @version 1.0
 * @since 5 /8/2017
 */
@Service
public class CommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private final RestTemplate restTemplate;

    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;
    /**
     * The Admin user name.
     */
    @Value("${inspection.server.admin.username}")
    public String adminUserName;
    /**
     * The Admin password.
     */
    @Value("${inspection.server.admin.password}")
    public String adminPassword;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;

    /**
     * Instantiates a new Common service.
     *
     * @param restTemplate the rest template
     */
    @Autowired
    public CommonService(RestTemplate restTemplate) {this.restTemplate = restTemplate;}


    /**
     * Send form t.
     *
     * @param <T>          the type parameter
     * @param reqUrl       the req url
     * @param httpMethod   the http method
     * @param bodyObject   the body object
     * @param responseType the response type
     * @return the t
     */
    public <T> T sendForm(String baseUrl, String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {
        String authorization = adminUserName + ":" + adminPassword;
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, "Basic " + Base64Utils.encodeToString(authorization.getBytes(StandardCharsets.UTF_8)));

        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);

        LOGGER.info("<T> T send :: Request : {} {baseUrl} : {}, Content-Type: {}", httpMethod, reqUrl, reqHeaders.getContentType());

        ResponseEntity<T> resEntity = restTemplate.exchange(baseUrl + reqUrl, httpMethod, reqEntity, responseType);
        LOGGER.info("Response Status Code: {}", resEntity.getStatusCode());

        return resEntity.getBody();
    }





    public Object sendRequestToSonar(Object object, String reqUrl, HttpMethod httpMethod){

        //SonarQube API에서 error 발생시 exception을 던지는 경우 때문에 try/catch 구문 사용
        try {
            JsonNode jsonResultFromSonar = sendForm(inspectionServerUrl, reqUrl, httpMethod, object, JsonNode.class);

            //sonarqube에서 에러 발생한 케이스
            if(null != jsonResultFromSonar && null != jsonResultFromSonar.get("errors")){

                return null;
            }

            if (null == jsonResultFromSonar){
                //delete api 또는 프로젝트 - 품질 게이트 연결을 사용했을 경우 jsonResultFromSonar가 null
                Map<String, String> resultModel = new LinkedHashMap<>();
                object = makeSonarResult(null, resultModel, Constants.RESULT_STATUS_SUCCESS);
            } else {
                object = makeSonarResult(jsonResultFromSonar, object, Constants.RESULT_STATUS_SUCCESS);
            }

            return object;

        } catch (Exception e) {
            e.printStackTrace();
            return makeSonarResult(null, object, Constants.RESULT_STATUS_FAIL);
        }
    }






    public Object sendRequestToCommon(Object object, String resultStatus, String reqUrl, HttpMethod httpMethod) {

        try{
            if (Constants.RESULT_STATUS_SUCCESS.equals(resultStatus)) {
                if (HttpMethod.GET.equals(httpMethod)){
                    //GET 요청
                    if(object instanceof Map){
                        // 리스트에 대한 요청
                        Map<String, Object> resultModel = (Map)object;
                        resultModel.put("resultList", sendForm(commonApiUrl, reqUrl, httpMethod, null, List.class));
                        resultModel.put("resultStatus", Constants.RESULT_STATUS_SUCCESS);
                        return resultModel;
                    } else {
                        // 단일 요청
                        Object returnObject = sendForm(commonApiUrl, reqUrl, httpMethod, null, object.getClass());
                        return makeCommonResult(returnObject, Constants.RESULT_STATUS_SUCCESS);
                    }

                } else if(HttpMethod.POST.equals(httpMethod) || HttpMethod.PUT.equals(httpMethod)) {
                    Object returnObject = sendForm(commonApiUrl, reqUrl, httpMethod, object, object.getClass());
                    return makeCommonResult(returnObject, Constants.RESULT_STATUS_SUCCESS);

                } else if (HttpMethod.DELETE.equals(httpMethod)) {
                    Map<String, Object> resultModel = (Map)object;
                    resultModel.put("resultStatus", sendForm(commonApiUrl, reqUrl, httpMethod, object, String.class));
                    return resultModel;
                }

            } else {
                //전 단계(sonarqube call)가 실패
                return makeCommonResult(object, Constants.RESULT_STATUS_FAIL);
            }
        } catch (Exception e){
            return makeCommonResult(object, Constants.RESULT_STATUS_FAIL);
        }
        return object;
    }


    //javadoc 플러그인
    public Object makeSonarResult(JsonNode jsonResult, Object object, String resultStatus) {

        if (Project.class.isInstance(object)) {
            Project project =(Project)object;
            if(Constants.RESULT_STATUS_SUCCESS.equals(resultStatus)){
                project.setId(jsonResult.get("id").asLong());
                project.setKey(jsonResult.get("k").asText());
                project.setName(jsonResult.get("nm").asText());
                project.setQualifier(jsonResult.get("qu").asText());
            }
            project.setResultStatus(resultStatus);
            return project;

        } else if (QualityGate.class.isInstance(object)){
            QualityGate qualityGate =(QualityGate) object;
            if(Constants.RESULT_STATUS_SUCCESS.equals(resultStatus)){
                qualityGate.setId(jsonResult.get("id").asLong());
                qualityGate.setName(jsonResult.get("name").asText());
            }
            qualityGate.setResultStatus(resultStatus);
            return qualityGate;

        } else if (QualityProfile.class.isInstance(object)) {
            QualityProfile qualityProfile = (QualityProfile) object;
            if(Constants.RESULT_STATUS_SUCCESS.equals(resultStatus)){
                JsonNode profileFromSonar = jsonResult.get("profile");
                qualityProfile.setName(profileFromSonar.get("name").asText());
                qualityProfile.setKey(profileFromSonar.get("key").asText());
                qualityProfile.setLanguage(profileFromSonar.get("language").asText());
                qualityProfile.setLanguageName(profileFromSonar.get("languageName").asText());
            }
            qualityProfile.setResultStatus(resultStatus);
            return qualityProfile;

        } else if (Map.class.isInstance(object)) {
            Map<String, String> resultModel =(Map)object;
            resultModel.put("resultStatus", resultStatus);
            return resultModel;
        }  else {
            return null;
        }
    }




    public Object makeCommonResult(Object object, String resultStatus) {

        if (Project.class.isInstance(object)) {
            Project project = (Project) object;
            project.setResultStatus(resultStatus);
            return project;

        } else if (QualityGate.class.isInstance(object)) {
            QualityGate qualityGate = (QualityGate)object;
            qualityGate.setResultStatus(resultStatus);

            return qualityGate;

        } else if (QualityProfile.class.isInstance(object)) {
            QualityProfile qualityProfile = (QualityProfile) object;
            qualityProfile.setResultStatus(resultStatus);
            return qualityProfile;

        } else if (Map.class.isInstance(object)){
            Map resultModel = (Map)object;
            resultModel.put("resultStatus",resultStatus);
            return resultModel;
        } else {
            return object;
        }
    }

}