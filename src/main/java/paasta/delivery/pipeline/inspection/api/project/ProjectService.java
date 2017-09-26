package paasta.delivery.pipeline.inspection.api.project;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Dojun on 2017-06-15.
 */
@Service
public class ProjectService {

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
    ProjectService(CommonService commonService) {this.commonService = commonService;}

    /**
     * 프로젝트 목록 조회
     * @return
     * @throws IOException
     */
    Map getProjectList() {
        Map<String, Object> resultModel = new HashMap<>();

        resultModel = (Map)commonService.sendRequestToCommon(resultModel, Constants.RESULT_STATUS_SUCCESS,"/project", HttpMethod.GET);

        List<Project> projectList = (List<Project>)resultModel.remove("resultList");
        resultModel.put("projects",projectList);

        return resultModel;
    }

    Project getProject(Long id) {
        Project project = new Project();
        project = (Project) commonService.sendRequestToCommon(project, Constants.RESULT_STATUS_SUCCESS, "/project/"+id, HttpMethod.GET);
        return project;
    }

    /**
     * 프로젝트 생성
     * @param
     * @return
     */
/*    public Project createProject(Project project) {
        project.setKey(UUID.randomUUID().toString().replace("-", ""));
        project = (Project) commonService.sendRequestToSonar(project, "/api/projects/create", HttpMethod.POST);
        project = (Project) commonService.sendRequestToCommon(project, project.getResultStatus(), "/project", HttpMethod.POST);
        return project;
    }*/

/*    Map deleteProject(Long id){
        Project project = getProject(id);
        Map<String, String> resultModel = new LinkedHashMap<>();
        resultModel = (Map)commonService.sendRequestToSonar(resultModel, "/api/projects/delete?key="+project.getKey(), HttpMethod.POST);
        resultModel = (Map)commonService.sendRequestToCommon(resultModel, resultModel.get("resultStatus"), "/project/"+id, HttpMethod.DELETE);
        resultModel = (Map)commonService.sendRequestToCommon(resultModel, resultModel.get("resultStatus"),"/projectRelation/"+id, HttpMethod.DELETE);
        return resultModel;
    }*/


/*
    JsonNode returnTest(Project project) throws IOException {
        JsonNode jsonResult = commonService.sendForm("api/projects/create", HttpMethod.POST, project, JsonNode.class);
        return jsonResult;
    }
*/


//    List getProjectList(String serviceInstancesId){
//        return (List) commonService.sendForm(commonApiUrl, "/project/projectsList?serviceInstancesId="+serviceInstancesId, HttpMethod.GET , null ,Object.class);
//    }



    ////////////////////////////////////////////////////


    List getProjecstList(){
        return (List) commonService.sendForm(commonApiUrl, "/project/projectsList", HttpMethod.GET , null ,Object.class);
    }

    public Project createProjects(Project project){
        Project result = new Project();
        //프로젝트 키 셋팅
        project.setKey(UUID.randomUUID().toString().replace("-", ""));
        project.setSonarKey(project.getKey());
        result = commonService.sendForm(inspectionServerUrl, "/api/projects/create" , HttpMethod.POST, project, Project.class);
        //sona에서 가져온 id 셋팅
        project.setId(result.getId());

        result = commonService.sendForm(commonApiUrl, "/project/projectsCreate", HttpMethod.POST, project, Project.class);

        return result;
    }

    public Project deleteProjects(Project project){
        Project result = new Project();
        //sona 에서 삭제시 key 필요함.
        commonService.sendForm(inspectionServerUrl, "/api/projects/delete" , HttpMethod.POST, project, Project.class);
        //DB삭제시 id가 필요함.
        result = commonService.sendForm(commonApiUrl, "/project/projectsDelete", HttpMethod.POST, project, Project.class);
        return result;
    }

    public Project updateProjects(Project project){
        return commonService.sendForm(commonApiUrl, "/project/projectsUpdate", HttpMethod.POST, project, Project.class);
    }

    public Project qualityGateProjectLiked(Project project){


        Project result = new Project();
        project.setProjectId(project.getId().toString());
        project.setGateId(project.getQualityGateId());

        if(project.getLinked().equals(true)){
            result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/select", HttpMethod.POST, project, Project.class);
        }else if(project.getLinked().equals(false)){
            result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/deselect", HttpMethod.POST, project, Project.class);
        }

        result = commonService.sendForm(commonApiUrl, "/project/qualityGateProjectLiked", HttpMethod.POST, project, Project.class);


        return result;
    }


    public Project qualityProfileProjectLinked(Project project){
        if(project.getLinked().equals(true)){
            commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/add_project", HttpMethod.POST, project, Project.class);

        }else if(project.getLinked().equals(false)){
            commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/remove_project", HttpMethod.POST, project, Project.class);
        }

        project = commonService.sendForm(commonApiUrl, "/project/qualityProfileProjectLiked", HttpMethod.POST, project, Project.class);


        return project;
    }

    ///////////////////////////////////

    public List getProjectSonarKey(Project project){
        return commonService.sendForm(inspectionServerUrl, "/api/resources?resource="+project.getProjectKey(), HttpMethod.GET, null, List.class);
    }

    public Project qualityManagementList(Project project){
        return commonService.sendForm(inspectionServerUrl, "/api/components/app?uuid="+project.getUuid(), HttpMethod.GET, null, Project.class);
    }

    public List<Project>  qualityCoverageList(Project project){
        String coverageUrl = "/api/resources?resource="+project.getResource()+"&metrics=coverage,line_coverage,uncovered_lines" +
                ",lines_to_cover,branch_coverage,uncovered_conditions,conditions_to_cover,new_line_coverage"+
                ",tests,test_execution_time,test_errors,test_failures,skipped_tests,test_success_density,new_coverage,ncloc&includetrends=true";

        return commonService.sendForm(inspectionServerUrl, coverageUrl, HttpMethod.GET, null, List.class);
    }

    public List testsSourceList(Project project){
      /*
        List resultList = new ArrayList();

        resultList.add(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&scopes=DIR&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class));
        resultList.add(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&scopes=FIL&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class));
        */

        return commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class);
    }

    public Project testsSourceShow(Project project){

        Project result = new Project();
        project.setMsr(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics=coverage_line_hits_data,covered_conditions_by_line&resource="+project.getKey(), HttpMethod.GET, null, List.class));
        result = commonService.sendForm(inspectionServerUrl, "/api/sources/show?key="+project.getKey(), HttpMethod.GET, null, Project.class);
        project.setSources(result.getSources());
        result = commonService.sendForm(inspectionServerUrl, "/api/sources/scm?key="+project.getKey(), HttpMethod.GET, null, Project.class);
        project.setScm(result.getScm());
        result = commonService.sendForm(inspectionServerUrl, "/api/issues/search?additionalFields=_all&resolved=false&fileUuids="+project.getUuid(), HttpMethod.GET, null, Project.class);
        project.setIssues(result.getIssues());


        return project;
    }

}

