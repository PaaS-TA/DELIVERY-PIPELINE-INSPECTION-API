package paasta.delivery.pipeline.inspection.api.qualityGate;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;
import paasta.delivery.pipeline.inspection.api.project.Project;
import paasta.delivery.pipeline.inspection.api.project.ProjectService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Dojun on 2017-06-15.
 */
@Service
public class QualityGateService {

    private final Logger LOGGER = getLogger(getClass());

    private final CommonService commonService;

    private final ProjectService projectService;
    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;

    @Autowired
    public QualityGateService(CommonService commonService, ProjectService projectService) {
        this.commonService = commonService;
        this.projectService = projectService;
    }


    /**
     * QualityGate 목록 조회
     *
     * @param
     * @return QualityGate
     */
    public List getQualityGateList(String serviceInstancesId) {
        Map result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/list", HttpMethod.GET, null, Map.class);
        List rest = myQualityServiceInstance((List<Map>) result.get("qualitygates"), serviceInstancesId);
        return rest;
    }


    /**
     * QualityGate 조건 옵션조회
     *
     * @param
     * @return QualityGate
     */
    public List getMetricsList() {

        List result = new ArrayList();
        result.add(commonService.sendForm(inspectionServerUrl, "/api/metrics/search", HttpMethod.GET, null, Object.class));
        result.add(commonService.sendForm(inspectionServerUrl, "/api/metrics/domains", HttpMethod.GET, null, Object.class));


        return result;
    }


    /**
     * 품질 게이트 조건 목록
     *
     * @param id
     * @return
     */
    QualityGate getQualityGateCondition(long id) {
        QualityGate qualityGate = new QualityGate();
        try {
            qualityGate = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/show?id=" + id, HttpMethod.GET, null, QualityGate.class);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
            return setQualityGateConditionName(qualityGate);
        } catch (HttpClientErrorException e) {
            //데이터가 없을 경우 NotFound로 떨어짐
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
                return qualityGate;
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 품질 게이트 조건 저장
     *
     * @param
     * @return
     */
    QualityGate createQualityGateCond(QualityGate qualityGate) {
        return commonService.sendForm(inspectionServerUrl, "/api/qualitygates/create_condition", HttpMethod.POST, qualityGate, QualityGate.class);
    }

    /**
     * 품질 게이트 조건 수정
     *
     * @param
     * @return
     */
    public QualityGate updateQualityGateCond(QualityGate qualityGate) {

        try {
            //id가 long 타입이라서 바꿔줘야함
            Map<String, String> parameter = new HashMap<>();
            parameter.put("id", Long.toString(qualityGate.getQualityGateId()));
            parameter.put("gateId", qualityGate.getGateId());
            parameter.put("metric", qualityGate.getMetric());
            parameter.put("error", qualityGate.getError());
            parameter.put("warning", qualityGate.getWarning());
            parameter.put("op", qualityGate.getOp());
            qualityGate = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/update_condition", HttpMethod.POST, parameter, QualityGate.class);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }
        return qualityGate;
    }


    /**
     * 품질 게이트 조건 삭제
     *
     * @param
     * @return
     */
    QualityGate deleteQualityGateCond(QualityGate qualityGate) {

        try {
            LOGGER.debug("DeleteQualityCondId : " + qualityGate.getId());
            LOGGER.debug("DeleteQualityCondGateId : " + qualityGate.getQualityGateId());
            LOGGER.debug("DeleteQualityCondGateName : " + qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());
            Map<String, String> parameter = new HashMap<>();
            parameter.put("id", Long.toString(qualityGate.getId()));

            commonService.sendForm(inspectionServerUrl, "/api/qualitygates/delete_condition", HttpMethod.POST, parameter, null);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }
        return qualityGate;
    }

    /**
     * 품질 게이트 복제
     *
     * @param
     * @return
     */
    QualityGate copyQualityGate(QualityGate qualityGate) {
        try {
            LOGGER.debug("CopyQualityGateId : " + qualityGate.getQualityGateId());
            LOGGER.debug("CopyQualityGateName : " + qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());
            Map<String, String> parameter = new HashMap<>();
            parameter.put("id", Long.toString(qualityGate.getQualityGateId()));
            parameter.put("name", qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());

            commonService.sendForm(inspectionServerUrl, "/api/qualitygates/copy", HttpMethod.POST, parameter, null);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }

        return qualityGate;
    }


    /**
     * 품질 게이트 생성
     *
     * @param qualityGate
     * @return
     */
    public QualityGate createQualityGate(QualityGate qualityGate) {
        try {
            Map parameter = new HashMap();
            LOGGER.debug("CreateQualityGateId : " + qualityGate.getQualityGateId());
            LOGGER.debug("CreateQualityGateName : " + qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());
            parameter.put("name", qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());
            commonService.sendForm(inspectionServerUrl, "/api/qualitygates/create", HttpMethod.POST, parameter, null);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }

        return qualityGate;
    }


    /**
     * 품질 게이트 업데이트
     *
     * @param qualityGate
     * @return
     */
    QualityGate updateQualityGate(QualityGate qualityGate) {
        try {
            LOGGER.debug("UpdateQualityGateId : " + qualityGate.getQualityGateId());
            LOGGER.debug("UpdateQualityGateName : " + qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());

            Map<String, String> parameter = new HashMap<>();
            parameter.put("id", Long.toString(qualityGate.getQualityGateId()));
            parameter.put("name", qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());
            commonService.sendForm(inspectionServerUrl, "/api/qualitygates/rename", HttpMethod.POST, parameter, null);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }
        return qualityGate;
    }


    /**
     * 품질 게이트 삭제
     *
     * @param
     * @return
     */

    QualityGate deleteQualityGate(QualityGate qualityGate) {

        try {
            LOGGER.debug("DeleteQualityGateId : " + qualityGate.getQualityGateId());
            Map<String, String> parameter = new HashMap<>();
            parameter.put("id", Long.toString(qualityGate.getQualityGateId()));
            commonService.sendForm(inspectionServerUrl, "/api/qualitygates/destroy", HttpMethod.POST, parameter, null);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }
        return qualityGate;
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
     *
     * @param
     * @return
     */
    public QualityGate getQualityGateDomains() {
        QualityGate qualityGate = new QualityGate();
        try {
            LOGGER.debug("GateDomains");
            qualityGate = commonService.sendForm(inspectionServerUrl, "/api/metrics/domains", HttpMethod.GET, null, QualityGate.class);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }

        return qualityGate;
    }

    /**
     * 품질 게이트 id로 검색
     *
     * @param
     * @return
     */
    public QualityGate getiQualityGate(long id) {

        QualityGate qualityGate = new QualityGate();
        try {
            LOGGER.debug("GetiQualityGateID : " + id);
            LOGGER.debug("GetQualityGateName : " + qualityGate.getServiceInstancesId() + "^" + qualityGate.getQualityGateName());
            qualityGate = commonService.sendForm(commonApiUrl, "/qualityGate/getQualityGate?id=" + id, HttpMethod.GET, null, QualityGate.class);
            qualityGate.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            qualityGate.setResultStatus(Constants.RESULT_STATUS_FAIL);
        }

        return qualityGate;
    }

    public QualityGate setQualityGateConditionName(QualityGate data) {
        List returnList = new ArrayList();
        List<Map> metrics = data.getConditions();
        List<Map> conditionsData = getMetricsList();
        List<Map> conditions = (List<Map>) conditionsData.get(0).get("metrics");
        if (metrics != null & conditions != null) {
            for (Map metric : metrics) {
                for (Map condition : conditions) {
                    if (metric.get("metric").toString().equalsIgnoreCase(condition.get("key").toString())) {
                        metric.put("name", condition.get("name").toString());
                    }
                }
                if (metric.get("name") == null) {
                    metric.put("name", metric.get("metric"));
                }
                returnList.add(metric);
            }
            data.setConditions(returnList);
        }
        return data;

    }


    public List myQualityServiceInstance(List<Map> list, String serviceInstancesId) {
        //나중에 정렬기능추가하여서 바꿔야함...구현이 우선이라서 더러운코드로 진행함
        List<Map> returnList = new ArrayList<>();
        List<Map> defaluts = new ArrayList<>();
        List<Map> no_defaluts = new ArrayList<>();
        for (Map map : list) {
            String name = map.get("name").toString();
            String[] names = name.split("\\^");
            if (names.length > 0) {
                if (names[0].equalsIgnoreCase(serviceInstancesId) || names[0].toUpperCase().equalsIgnoreCase("DEFAULT")) {
                    map.put("name", names[1]);
                    if (names[0].toUpperCase().equalsIgnoreCase("DEFAULT")) {
                        map.put("gateDefaultYn", "Y");
                        defaluts.add(map);
                    } else {
                        map.put("gateDefaultYn", "N");
                        no_defaluts.add(map);
                    }

                }
            }
        }
        for (Map map : defaluts) {
            returnList.add(map);
        }
        for (Map map : no_defaluts) {
            returnList.add(map);
        }

        return returnList;
    }


    public List getProjects(Project project) {
        //퀄리티게이트용 정보를 추가하는 작업을 진행해야함
        List data = projectService.getProjects(project);
        return data;

    }

}
