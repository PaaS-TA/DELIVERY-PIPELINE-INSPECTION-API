package paasta.delivery.pipeline.inspection.api.qualityGate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.*;

/**
 * Created by Dojun on 2017-06-15.
 */
@Service
public class QualityGateService {

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
    QualityGateService(CommonService commonService) {this.commonService = commonService;}


    /**
     *  QualityGate 목록 조회
     *
     * @param
     * @return QualityGate
     */
    public List getQualityGateList(String serviceInstancesId){
        Map<String, Object> params = new HashMap<>();
        List result = new ArrayList<>();
        QualityGate qualityGate = new QualityGate();
        //default라는 자바예약어변수를 쓸수 없기 때문에 변수명을 바꿔씀.
//        params = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/list",HttpMethod.GET,null,Map.class);
        result = commonService.sendForm(commonApiUrl,"/qualityGate/qualityGateList?serviceInstancesId="+serviceInstancesId,HttpMethod.GET, null, List.class);
        return result;
    }



    /**
     *  QualityGate 조건 옵션조회
     *
     * @param
     * @return QualityGate
     */
    public List getMetricsList(){

        List result = new ArrayList();
        result.add(commonService.sendForm(inspectionServerUrl, "/api/metrics/search", HttpMethod.GET,null ,Object.class));
        result.add(commonService.sendForm(inspectionServerUrl, "/api/metrics/domains", HttpMethod.GET,null ,Object.class));


        return result;
    }



    /**
     * 품질 게이트 조건 목록
     * @param id
     * @return
     */
    QualityGate getQualityGateCondition(long id){
        return commonService.sendForm(inspectionServerUrl, "/api/qualitygates/show?id="+id, HttpMethod.GET,null ,QualityGate.class );

    }

    /**
     * 품질 게이트 조건 저장
     * @param
     * @return
     */
    QualityGate createQualityGateCond(QualityGate qualityGate){
        return commonService.sendForm(inspectionServerUrl,"/api/qualitygates/create_condition" ,HttpMethod.POST,qualityGate ,QualityGate.class);
    }

    /**
     * 품질 게이트 조건 수정
     * @param
     * @return
     */
    public QualityGate updateQualityGateCond(QualityGate qualityGate){
        //id가 long 타입이라서 바꿔줘야함
        Map<String, String> resultModel = new HashMap<>();
        resultModel.put("id", Long.toString(qualityGate.getId()));
        resultModel.put("gateId", qualityGate.getGateId());
        resultModel.put("metric", qualityGate.getMetric());
        resultModel.put("error", qualityGate.getError());
        resultModel.put("warning", qualityGate.getWarning());
        resultModel.put("op", qualityGate.getOp());

        return commonService.sendForm(inspectionServerUrl,"/api/qualitygates/update_condition" ,HttpMethod.POST,resultModel ,QualityGate.class);
    }





    /**
     * 품질 게이트 조건 삭제
     * @param
     * @return
     */
    QualityGate deleteQualityGateCond(String id){
        QualityGate result = new QualityGate();
        commonService.sendForm(inspectionServerUrl, "/api/qualitygates/delete_condition", HttpMethod.POST, id, QualityGate.class);
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return  result;
    }

    /**
     * 품질 게이트 복제
     * @param
     * @return
     */
    QualityGate copyQualityGate(QualityGate qualityGate){
//        QualityGate qualityGate = new QualityGate();

        Map<String, String> resultModel = new HashMap<>();
        resultModel.put("id", Long.toString(qualityGate.getId()));
        resultModel.put("name", qualityGate.getName());
        resultModel.put("serviceInstancesId", qualityGate.getServiceInstancesId());
        resultModel.put("gateDefaultYn",qualityGate.getGateDefaultYn());

        qualityGate = commonService.sendForm(inspectionServerUrl,"/api/qualitygates/copy", HttpMethod.POST,  resultModel, QualityGate.class);
        qualityGate.setServiceInstancesId(resultModel.get("serviceInstancesId"));
        qualityGate.setGateDefaultYn(resultModel.get("gateDefaultYn"));
        qualityGate = commonService.sendForm(commonApiUrl, "/qualityGate/qualityGateCopy", HttpMethod.POST, qualityGate, QualityGate.class);

        return qualityGate;
    }



    /**
     * 품질 게이트 생성
     * @param qualityGate
     * @return
     */
    QualityGate createQualityGate(QualityGate qualityGate) {

/*
        qualityGate = (QualityGate) commonService.sendRequestToSonar(qualityGate, "/api/qualitygates/create", HttpMethod.POST);
        qualityGate = (QualityGate) commonService.sendRequestToCommon(qualityGate, qualityGate.getResultStatus(), "/qualityGate/qualityGateCreate", HttpMethod.PUT);
*/

        QualityGate result = new QualityGate();

        result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/create", HttpMethod.POST,qualityGate, QualityGate.class);

        result.setServiceInstancesId(qualityGate.getServiceInstancesId());
        result.setGateDefaultYn(qualityGate.getGateDefaultYn());
        result = commonService.sendForm(commonApiUrl, "/qualityGate/qualityGateCreate", HttpMethod.POST, result,QualityGate.class);


        return result;
    }




    /**
     * 품질 게이트 업데이트
     * @param qualityGate
     * @return
     */
    QualityGate updateQualityGate(QualityGate qualityGate) {
        Map<String, String> resultModel = new HashMap<>();
        resultModel.put("id", Long.toString(qualityGate.getId()));
        resultModel.put("name", qualityGate.getName());
        resultModel.put("ServiceInstancesId",qualityGate.getServiceInstancesId());
        resultModel.put("gateDefaultYn", qualityGate.getGateDefaultYn());

        qualityGate = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/rename", HttpMethod.POST, resultModel,QualityGate.class);
        qualityGate = commonService.sendForm(commonApiUrl, "/qualityGate/qualityGateUpdate", HttpMethod.PUT, resultModel,QualityGate.class);
        return qualityGate;
    }


    /**
     * 품질 게이트 삭제
     * @param
     * @return
     */
//    String deleteQualityGate(Long id) {
//        Map<String, String> resultModel = new LinkedHashMap<>();
//        resultModel = (Map)commonService.sendRequestToSonar(resultModel, "/api/qualitygates/destroy?id="+id, HttpMethod.POST);
//        resultModel = (Map)commonService.sendRequestToCommon(resultModel, resultModel.get("resultStatus"),"/qualityGate/"+id, HttpMethod.DELETE);
//        return resultModel.get("resultStatus");
//    }

    QualityGate deleteQualityGate(QualityGate qualityGate) {
        Map<String, String> resultModel = new HashMap<>();
        resultModel.put("id", Long.toString(qualityGate.getId()));
        resultModel.put("serviceInstancesId",qualityGate.getServiceInstancesId());
        QualityGate result = new QualityGate();

        commonService.sendForm(inspectionServerUrl, "/api/qualitygates/destroy", HttpMethod.POST,resultModel, null);
        commonService.sendForm(commonApiUrl, "/qualityGate/qualityGateDelete", HttpMethod.DELETE, resultModel , null);
        commonService.sendForm(commonApiUrl,"/project/qualityGateDelete",HttpMethod.PUT,resultModel,String.class);
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return result;
    }

    /**
     * 품질 게이트 기본설정
     * @param
     * @return
     */
    /*
    public QualityGate qualityGateDefaultSetting(QualityGate qualityGate){
        Map<String, String> resultModel = new HashMap<>();
        resultModel.put("id", Long.toString(qualityGate.getId()));
        commonService.sendForm(inspectionServerUrl, "/api/qualitygates/set_as_default" , HttpMethod.POST,resultModel, null);
        commonService.sendForm(commonApiUrl,"/qualityGate/qualityGateDefaultSetting",HttpMethod.PUT,qualityGate, String.class);
        qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return qualityGate;
    }
    */


    /**
     * 품질 게이트 조건 domain
     * @param
     * @return
     */
    public QualityGate getQualityGateDomains(){
        return commonService.sendForm(inspectionServerUrl, "/api/metrics/domains",HttpMethod.GET, null,QualityGate.class);
    }

    /**
     * 품질 게이트 id로 검색
     * @param
     * @return
     */
    public QualityGate getiQualityGate(long id){
        return commonService.sendForm(commonApiUrl,"/qualityGate/getQualityGate?id="+id, HttpMethod.GET ,null, QualityGate.class);
    }
}
