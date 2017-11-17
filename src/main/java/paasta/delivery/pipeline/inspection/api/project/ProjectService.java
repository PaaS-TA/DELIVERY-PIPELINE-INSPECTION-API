package paasta.delivery.pipeline.inspection.api.project;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Dojun on 2017-06-15.
 */
@Service
public class ProjectService {

    private final Logger LOGGER = getLogger(getClass());

    private static final String PROJECT_KEY_STRING = "projectKey";
    private static final String PROFILE_KEY_STRING = "profileKey";
    private static final String PROJECT_ID_STRING = "projectId";
    private static final String GATE_ID_STRING = "gateId";

    private final CommonService commonService;

    @Value("${inspection.server.url}")
    public String inspectionServerUrl;

    @Value("${commonApi.url}")
    private String commonApiUrl;

    // TODO
    //project create
//    public Project createProjects(Project project) {
//        Project result = new Project();
//
//        //프로젝트 키 셋팅
//        project.setProjectKey(UUID.randomUUID().toString().replace("-", ""));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddkkmmss", Locale.KOREAN);
//
//        Date currentTime = new Date();
//        String dTime = formatter.format(currentTime);
//
//        project.setProjectName(UUID.randomUUID().toString().replace("-", "") + "_" + dTime);
//
//        project.setProjectName(project.getProjectName());
//        project.setProjectName(project.getProjectName());
//        result = commonService.sendForm(inspectionServerUrl, "/api/projects/create", HttpMethod.POST, project, Project.class);
//        //sona에서 가져온 id 셋팅
//        project.setProjectId(result.getProjectId());
//        result = commonService.sendForm(commonApiUrl, "/project/projectsCreate", HttpMethod.POST, project, Project.class);
//
//        QualityProfile profileParam = new QualityProfile();
//        QualityGate gateParam = new QualityGate();
//
//        long profileId = project.getQualityProfileId();
//        long gateId = project.getQualityGateId();
//
//        profileParam = qualityProfileService.getQualityProfile(profileId);
//        gateParam = qualityGateService.getiQualityGate(gateId);
//
//
//        if (profileParam.getProfileDefaultYn().equals("N")) {
//            project.setProfileKey(profileParam.getQualityProfileKey());
//            project.setLinked(true);
//            qualityProfileProjectLinked(project);
//        }
//
//        if (gateParam.getGateDefaultYn().equals("N")) {
//            project.setLinked(true);
//
//            qualityGateProjectLiked(project);
//        }
//
//        return result;
//    }

    // TODO
    //project update
//    public Project updateProjects(Project project) {
//        Project result = new Project();
//
//        result = commonService.sendForm(commonApiUrl, "/project/projectsUpdate", HttpMethod.PUT, project, Project.class);
//
//        QualityProfile profileParam = new QualityProfile();
//        QualityGate gateParam = new QualityGate();
//        Project projectKey = new Project();
//
//        projectKey = getProjectKey(project);
//        long profileId = project.getQualityProfileId();
//        long gateId = project.getQualityGateId();
//
//        profileParam = qualityProfileService.getQualityProfile(profileId);
//        gateParam = qualityGateService.getiQualityGate(gateId);
//
//        //////추가///////////////////////////
//        if (profileParam.getProfileDefaultYn().equals("N")) {
//            project.setLinked(true);
//            project.setProjectKey(projectKey.getProjectKey());
//            project.setProfileKey(profileParam.getQualityProfileKey());
//            result = qualityProfileProjectLinked(project);
//        }
//
//        if (gateParam.getGateDefaultYn().equals("N")) {
//            project.setLinked(true);
//            project.setProjectId(project.getProjectId());
//            result = qualityGateProjectLiked(project);
//        }
//        ////////////////////////////////////////////
//
//
////        project.setLinked(true);
////        project.setProjectKey(projectKey.getProjectKey());
////        project.setProfileKey(profileParam.getKey());
////        project.setProjectId(Long.toString(project.getId()));
////
////        result = qualityProfileProjectLinked(project);
////        result = qualityGateProjectLiked(project);
//
//
//        return result;
//    }

    @Autowired
    ProjectService(CommonService commonService) {
        this.commonService = commonService;
    }

    //project List
    public List getProjectsList(Project project) {
        return commonService.sendForm(commonApiUrl, "/project/projectsList", HttpMethod.POST, project, List.class);
    }

    //getProject
    public List getProject(Project project) {
        return commonService.sendForm(commonApiUrl, "/project/getProject", HttpMethod.POST, project, List.class);
    }

    //project delete
    public Project deleteProjects(Project project) {

        Project result = new Project();

        //sona 에서 삭제시 key 필요함.
        commonService.sendForm(inspectionServerUrl, "/api/projects/delete", HttpMethod.POST, project, Project.class);

        //DB삭제시 id가 필요함.
        result = commonService.sendForm(commonApiUrl, "/project/projectsDelete", HttpMethod.DELETE, project, Project.class);
        return result;
    }

    //qualityGate project 연결
    public Project qualityGateProjectLiked(Project project) {


        Project result;
        project.setProjectId(project.getProjectId());
        project.setGateId(String.valueOf(project.getQualityGateId()));
        LOGGER.debug("SelectProject_isLinked(True = select,False = deselect) : " + project.isLinked());
        LOGGER.debug("SelectProject_Id : " + project.getId());
        LOGGER.debug("SelectProject_GateId : " + project.getQualityGateId());
        LOGGER.debug("SelectProject_ProjectId : " + project.getProjectId());
        LOGGER.debug("SelectProject_ProjectKey : " + project.getProjectKey());


        Map<String, String> parameter = new HashMap();
        parameter.put("gateId", project.getGateId());
        parameter.put("projectId", "" + project.getProjectId());
        parameter.put("projectKey", project.getProjectKey());
        /*
        * 소나에 적용하고, 파이프라인 DB 업데이트
        */
        if (project.isLinked()) {
            result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/select", HttpMethod.POST, parameter, Project.class);
        } else {
            result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/deselect", HttpMethod.POST, parameter, Project.class);
        }
        result = commonService.sendForm(commonApiUrl, "/project/qualityGateProjectLiked", HttpMethod.PUT, project, Project.class);
        return result;
    }

    //qualityProfile project 연결
    public Project qualityProfileProjectLinked(Project project) {
        Project result = new Project();

        if (project.isLinked()) {
            commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/add_project", HttpMethod.POST, project, Project.class);

        } else {
            commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/remove_project", HttpMethod.POST, project, Project.class);
        }

        result = commonService.sendForm(commonApiUrl, "/project/qualityProfileProjectLiked", HttpMethod.PUT, project, Project.class);

        return result;
    }

    //sonar에서 사용하는 project uuid search
    public List getProjectSonarKey(Project project) {
        return commonService.sendForm(inspectionServerUrl, "/api/resources?resource=" + project.getProjectKey(), HttpMethod.GET, null, List.class);
    }

    //qualityManagement List
    public Project qualityManagementList(Project project) {
        return commonService.sendForm(inspectionServerUrl, "/api/components/app?uuid=" + project.getUuid(), HttpMethod.GET, null, Project.class);
    }

    //coverage List
    public List<Project> qualityCoverageList(Project project) {
        String coverageUrl = "/api/resources?resource=" + project.getResource() + "&metrics=coverage,line_coverage,uncovered_lines" +
                ",lines_to_cover,branch_coverage,uncovered_conditions,conditions_to_cover,new_line_coverage" +
                ",tests,test_execution_time,test_errors,test_failures,skipped_tests,test_success_density,new_coverage,ncloc&includetrends=true";

        return commonService.sendForm(inspectionServerUrl, coverageUrl, HttpMethod.GET, null, List.class);
    }

    //단위테스트 List
    public List testsSourceList(Project project) {
      /*
        List resultList = new ArrayList();

        resultList.add(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&scopes=DIR&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class));
        resultList.add(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&scopes=FIL&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class));
        */

        return commonService.sendForm(inspectionServerUrl, "/api/resources?metrics=" + project.getMetrics() + "&depth=-1&resource=" + project.getProjectKey(), HttpMethod.GET, null, List.class);
    }

    //단위테스트 detail
    public Project testsSourceShow(Project project) {

        Project result = new Project();

        result.setMsr(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics=coverage_line_hits_data,covered_conditions_by_line&resource=" + project.getProjectKey(), HttpMethod.GET, null, List.class));

        result = commonService.sendForm(inspectionServerUrl, "/api/sources/show?key=" + project.getProjectKey(), HttpMethod.GET, null, Project.class);
        project.setSources(result.getSources());

        result = commonService.sendForm(inspectionServerUrl, "/api/sources/scm?key=" + project.getProjectKey(), HttpMethod.GET, null, Project.class);
        project.setScm(result.getScm());

        result = commonService.sendForm(inspectionServerUrl, "/api/issues/search?additionalFields=_all&resolved=false&fileUuids=" + project.getUuid(), HttpMethod.GET, null, Project.class);
        project.setIssues(result.getIssues());


        return project;
    }

    //projectKey data 가져오기
    public Project getProjectKey(Project project) {
        return commonService.sendForm(commonApiUrl, "/project/projectKey", HttpMethod.POST, project, Project.class);
    }


    /**
     * Sets create project.
     *
     * @param project the project
     * @return the create project
     */
    Project setCreateProject(Project project) {
        Project resultModel = new Project();
        resultModel.setResultStatus(Constants.RESULT_STATUS_FAIL);

        long projectId;
        String projectKey;
        String projectName;
        Map<String, String> reqProjectMap = new HashMap<>();


        // SET PROJECT KEY
        projectKey = "KEY-" + UUID.randomUUID().toString() + "-" + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

        // SET PROJECT NAME
        projectName = "SQ-" + UUID.randomUUID().toString() + "-" + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

        project.setProjectKey(projectKey);
        project.setProjectName(projectName);

        reqProjectMap.put("key", projectKey);
        reqProjectMap.put("name", projectName);


        // CREATE PROJECT TO INSPECTION SERVER
        Project apiResult = commonService.sendForm(inspectionServerUrl, "/api/projects/create", HttpMethod.POST, reqProjectMap, Project.class);

        // SET PROJECT ID FROM RESULT
        projectId = apiResult.getId();
        project.setProjectId(projectId);


        // SET QUALITY PROFILE FOR PROJECT TO INSPECTION SERVER
        procSetLinkProject(projectKey, project.getQualityProfileKey(), LinkType.QUALITY_PROFILE, LinkOperationType.LINK);

        // SET QUALITY GATE FOR PROJECT TO INSPECTION SERVER
        procSetLinkProject(String.valueOf(projectId), String.valueOf(project.getQualityGateId()), LinkType.QUALITY_GATE, LinkOperationType.LINK);


        // INSERT PROJECT TO DATABASE
        resultModel = commonService.sendForm(commonApiUrl, "/project/projectsCreate", HttpMethod.POST, project, Project.class);
        resultModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return resultModel;
    }


    /**
     * Sets update project.
     *
     * @param project the project
     * @return the update project
     */
    Project setUpdateProject(Project project) {
        Project resultModel = new Project();
        resultModel.setResultStatus(Constants.RESULT_STATUS_FAIL);

        String projectKey;
        String originalQualityProfileKey;
        long originalQualityGateId;
        String reqInspectionProfileKey = project.getQualityProfileKey();
        long reqInspectionGateId = project.getQualityGateId();


        // GET PROJECT DETAIL FROM DATABASE
        Project projectDetail = commonService.sendForm(commonApiUrl, "/project/" + project.getId(), HttpMethod.GET, null, Project.class);

        projectKey = projectDetail.getProjectKey();
        originalQualityProfileKey = projectDetail.getQualityProfileKey();
        originalQualityGateId = projectDetail.getQualityGateId();


        // CHECK ORIGINAL QUALITY PROFILE
        if (!originalQualityProfileKey.equals(reqInspectionProfileKey)) {
            // SET REMOVING QUALITY PROFILE FOR PROJECT TO INSPECTION SERVER
            procSetLinkProject(projectKey, originalQualityProfileKey, LinkType.QUALITY_PROFILE, LinkOperationType.UNLINK);

            // SET ADDING QUALITY PROFILE FOR PROJECT TO INSPECTION SERVER
            procSetLinkProject(projectKey, reqInspectionProfileKey, LinkType.QUALITY_PROFILE, LinkOperationType.LINK);
        }


        // CHECK ORIGINAL QUALITY GATE
        if (originalQualityGateId != reqInspectionGateId) {
            // SET DESELECTING QUALITY GATE FOR PROJECT TO INSPECTION SERVER
            procSetLinkProject(String.valueOf(projectDetail.getProjectId()), String.valueOf(originalQualityGateId), LinkType.QUALITY_GATE, LinkOperationType.UNLINK);

            // SET SELECTING QUALITY GATE FOR PROJECT TO INSPECTION SERVER
            procSetLinkProject(String.valueOf(projectDetail.getProjectId()), String.valueOf(reqInspectionGateId), LinkType.QUALITY_GATE, LinkOperationType.LINK);
        }


        // UPDATE PROJECT TO DATABASE
        resultModel = commonService.sendForm(commonApiUrl, "/project/projectsUpdate", HttpMethod.PUT, project, Project.class);
        resultModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return resultModel;
    }


    /**
     * Sets delete project.
     *
     * @param project the project
     * @return the delete project
     */
    Project setDeleteProject(Project project) {
        Project resultModel;
        Map<String, String> reqProjectMap = new HashMap<>();
        reqProjectMap.put("key", project.getProjectKey());

        // DELETE PROJECT TO INSPECTION SERVER
        commonService.sendForm(inspectionServerUrl, "/api/projects/delete", HttpMethod.POST, reqProjectMap, Project.class);

        // DELETE PROJECT TO DATABASE
        resultModel = commonService.sendForm(commonApiUrl, "/project/projectsDelete", HttpMethod.DELETE, project, Project.class);

        resultModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return resultModel;
    }


    private void procSetLinkProject(String projectKeyOrProjectId, String qualityProfileKeyOrQualityGateId, Enum linkType, Enum linkOperation) {
        Map<String, String> reqMap = new HashMap<>();
        String reqUrl;

        if (LinkType.QUALITY_PROFILE.equals(linkType)) {
            reqUrl = (LinkOperationType.LINK.equals(linkOperation)) ? "/api/qualityprofiles/add_project" : "/api/qualityprofiles/remove_project";

            reqMap.put(PROJECT_KEY_STRING, projectKeyOrProjectId);
            reqMap.put(PROFILE_KEY_STRING, qualityProfileKeyOrQualityGateId);

        } else {
            reqUrl = (LinkOperationType.LINK.equals(linkOperation)) ? "/api/qualitygates/select" : "/api/qualitygates/deselect";

            reqMap.put(PROJECT_ID_STRING, projectKeyOrProjectId);
            reqMap.put(GATE_ID_STRING, qualityProfileKeyOrQualityGateId);
        }

        commonService.sendForm(inspectionServerUrl, reqUrl, HttpMethod.POST, reqMap, Project.class);
    }


    /**
     * The enum Link type.
     */
    enum LinkType {
        QUALITY_PROFILE,
        QUALITY_GATE;
    }


    /**
     * The enum Link operation type.
     */
    enum LinkOperationType {
        LINK,
        UNLINK;
    }

}
