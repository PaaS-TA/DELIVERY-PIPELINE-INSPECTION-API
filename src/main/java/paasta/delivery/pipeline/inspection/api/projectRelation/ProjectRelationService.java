package paasta.delivery.pipeline.inspection.api.projectRelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dojun on 2017-07-06.
 */
@Service
public class ProjectRelationService {

    private final CommonService commonService;

    @Autowired
    ProjectRelationService(CommonService commonService) {this.commonService = commonService;}

    /**
     * 프로젝트 관계(with qualityGate, qualityProfile) 목록 조회
     * @return
     */
    Map getProjectRelationList() {
        Map<String, Object> resultModel = new LinkedHashMap<>();
        resultModel = (Map)commonService.sendRequestToCommon(resultModel, Constants.RESULT_STATUS_SUCCESS, "/projectRelation", HttpMethod.GET);
        List<ProjectRelation> projectRelationList = (List)resultModel.remove("resultList");
        resultModel.put("projectRelations", projectRelationList);

        return resultModel;
    }


    /**
     * 프로젝트 관계(with qualityGate)  업데이트
     * @param projectId,qualtyGateId
     * @return
     */
    ProjectRelation updateProjectRelationQG(Long projectId, Long qualityGateId) {
        Map<String, String> resultModel = new LinkedHashMap<>();
        ProjectRelation projectRelation = new ProjectRelation();
        resultModel = (Map) commonService.sendRequestToSonar(projectRelation, "/api/qualitygates/select?gateId="+qualityGateId+"&projectId="+projectId, HttpMethod.POST);
        projectRelation = (ProjectRelation) commonService.sendRequestToCommon(projectRelation, resultModel.get("resultStatus"), "/projectRelation/"+projectId + "/qualityGate/"+qualityGateId, HttpMethod.PUT);
        return projectRelation;
    }

    /**
     * 프로젝트 관계(with qualityProfile)  업데이트
     * @param projectId,qualtyProfileId
     * @return
     */
/*
    ProjectRelation updateProjectRelationQP(Long projectId, Long qualityProfileId) {
        Map<String, String> resultModel = new LinkedHashMap<>();
        ProjectRelation projectRelation = new ProjectRelation();
        resultModel = (Map) commonService.sendRequestToSonar(projectRelation, "/api/qualitygates/select?gateId="+qualityProfileId+"&projectId="+projectId, HttpMethod.POST);
        projectRelation = (ProjectRelation) commonService.sendRequestToCommon(projectRelation, resultModel.get("resultStatus"), "/projectRelation/"+projectId + "/qualityProfile/"+qualityProfileId, HttpMethod.PUT);
        return projectRelation;
    }

*/

    /**
     * 프로젝트 관계(with qualityGate, qualityProfile) 삭제 - Project를 삭제시에만, 동작시킴
     * @param id
     * @return
     */
    String deleteProjectRelation(Long id) {
        Map<String, String> resultModel = new LinkedHashMap<>();
        resultModel = (Map)commonService.sendRequestToCommon(resultModel, resultModel.get("resultStatus"),"/projectRelation/"+id, HttpMethod.DELETE);
        return resultModel.get("resultStatus");
    }

}
