package paasta.delivery.pipeline.inspection.api.project;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGate;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGateService;
import paasta.delivery.pipeline.inspection.api.qualityProfile.QualityProfile;
import paasta.delivery.pipeline.inspection.api.qualityProfile.QualityProfileService;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Dojun on 2017-06-15.
 */
@Service
public class ProjectService {

    private final CommonService commonService;
    private final QualityGateService qualityGateService;
    private final QualityProfileService qualityProfileService;


    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;

    @Autowired
    ProjectService(CommonService commonService, QualityGateService qualityGateService, QualityProfileService qualityProfileService) {
        this.commonService = commonService;
        this.qualityGateService = qualityGateService;
        this.qualityProfileService = qualityProfileService;
    }

    //project List
    public List getProjectsList(Project project){
        return commonService.sendForm(commonApiUrl, "/project/projectsList", HttpMethod.POST , project ,List.class);
    }

    //getProject
    public List getProject(Project project){
        return commonService.sendForm(commonApiUrl, "/project/getProject", HttpMethod.POST , project, List.class);
    }

    //project create
    public Project createProjects(Project project){
        Project result = new Project();

        //프로젝트 키 셋팅
        project.setKey(UUID.randomUUID().toString().replace("-", ""));
        project.setSonarKey(project.getKey());
        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddkkmmss" ,Locale.KOREAN);

        Date currentTime = new Date();
        String dTime = formatter.format ( currentTime );

        project.setSonarName(UUID.randomUUID().toString().replace("-", "")+"_"+dTime);

        //수정후 삭제 예정
        project.setProjectName(project.getName());

        result = commonService.sendForm(inspectionServerUrl, "/api/projects/create" , HttpMethod.POST, project, Project.class);
        //sona에서 가져온 id 셋팅
        project.setId(result.getId());

        result = commonService.sendForm(commonApiUrl, "/project/projectsCreate", HttpMethod.POST, project, Project.class);

        QualityProfile profileParam = new QualityProfile();
        QualityGate gateParam = new QualityGate();

        long profileId = Long.parseLong(project.getQualityProfileId());
        long gateId = Long.parseLong(project.getQualityGateId());

        profileParam = qualityProfileService.getQualityProfile(profileId);
        gateParam = qualityGateService.getiQualityGate(gateId);



        if(profileParam != null && profileParam.getProfileDefaultYn().equals("N")){
            project.setProfileKey(profileParam.getKey());
            project.setProjectKey(project.getKey());
            project.setLinked(true);
            qualityProfileProjectLinked(project);
        }

        if(gateParam != null && gateParam.getGateDefaultYn().equals("N")){
            project.setLinked(true);

            qualityGateProjectLiked(project);
        }

        return result;
    }

    //project delete
    public Project deleteProjects(Project project){

        Project result = new Project();

        //sona 에서 삭제시 key 필요함.
        commonService.sendForm(inspectionServerUrl, "/api/projects/delete" , HttpMethod.POST, project, Project.class);

        //DB삭제시 id가 필요함.
        result = commonService.sendForm(commonApiUrl, "/project/projectsDelete", HttpMethod.DELETE, project, Project.class);
        return result;
    }

    //project update
    public Project updateProjects(Project project){
        Project result = new Project();

        //테스트용
/*        project.setProfileKey("java-quality-copy-55679");
        project.setProjectKey("84445a412f5a419fbe14615c8aa5077d");
        project.setGateDefaultYn("N");
        project.setProfileDefaultYn("N");*/



        result =  commonService.sendForm(commonApiUrl, "/project/projectsUpdate", HttpMethod.PUT, project, Project.class);

        QualityProfile profileParam = new QualityProfile();
        QualityGate gateParam = new QualityGate();
        Project projectKey = new Project();

        projectKey = getProjectKey(project);
        long profileId = Long.parseLong(project.getQualityProfileId());
        long gateId = Long.parseLong(project.getQualityGateId());

        profileParam = qualityProfileService.getQualityProfile(profileId);
        gateParam = qualityGateService.getiQualityGate(gateId);


        project.setLinked(true);
        project.setProjectKey(projectKey.getSonarKey());
        project.setProfileKey(profileParam.getKey());
        project.setProjectId(Long.toString(project.getId()));

        result = qualityProfileProjectLinked(project);
        result = qualityGateProjectLiked(project);


        return result;
    }

    //qualityGate project 연결
    public Project qualityGateProjectLiked(Project project){


        Project result = new Project();
        project.setProjectId(Long.toString(project.getId()));
        project.setGateId(project.getQualityGateId());

        if(project.getLinked().equals(true)){
            result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/select", HttpMethod.POST, project, Project.class);
        }else if(project.getLinked().equals(false)){
            result = commonService.sendForm(inspectionServerUrl, "/api/qualitygates/deselect", HttpMethod.POST, project, Project.class);
        }

        result = commonService.sendForm(commonApiUrl, "/project/qualityGateProjectLiked", HttpMethod.PUT, project, Project.class);

        return result;
    }


    //qualityProfile project 연결
    public Project qualityProfileProjectLinked(Project project){
        if(project.getLinked().equals(true)){
            commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/add_project", HttpMethod.POST, project, Project.class);

        }else if(project.getLinked().equals(false)){
            commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/remove_project", HttpMethod.POST, project, Project.class);
        }

        project = commonService.sendForm(commonApiUrl, "/project/qualityProfileProjectLiked", HttpMethod.PUT, project, Project.class);

        return project;
    }


    //sonar에서 사용하는 project uuid search
    public List getProjectSonarKey(Project project){
        return commonService.sendForm(inspectionServerUrl, "/api/resources?resource="+project.getProjectKey(), HttpMethod.GET, null, List.class);
    }

    //qualityManagement List
    public Project qualityManagementList(Project project){
        return commonService.sendForm(inspectionServerUrl, "/api/components/app?uuid="+project.getUuid(), HttpMethod.GET, null, Project.class);
    }

    //coverage List
    public List<Project>  qualityCoverageList(Project project){
        String coverageUrl = "/api/resources?resource="+project.getResource()+"&metrics=coverage,line_coverage,uncovered_lines" +
                ",lines_to_cover,branch_coverage,uncovered_conditions,conditions_to_cover,new_line_coverage"+
                ",tests,test_execution_time,test_errors,test_failures,skipped_tests,test_success_density,new_coverage,ncloc&includetrends=true";

        return commonService.sendForm(inspectionServerUrl, coverageUrl, HttpMethod.GET, null, List.class);
    }

    //단위테스트 List
    public List testsSourceList(Project project){
      /*
        List resultList = new ArrayList();

        resultList.add(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&scopes=DIR&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class));
        resultList.add(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&scopes=FIL&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class));
        */

        return commonService.sendForm(inspectionServerUrl, "/api/resources?metrics="+project.getMetrics()+"&depth=-1&resource="+project.getProjectKey(), HttpMethod.GET, null, List.class);
    }

    //단위테스트 detail
    public Project testsSourceShow(Project project){

        Project result = new Project();

        result.setMsr(commonService.sendForm(inspectionServerUrl, "/api/resources?metrics=coverage_line_hits_data,covered_conditions_by_line&resource="+project.getKey(), HttpMethod.GET, null, List.class));

        result = commonService.sendForm(inspectionServerUrl, "/api/sources/show?key="+project.getKey(), HttpMethod.GET, null, Project.class);
        project.setSources(result.getSources());

        result = commonService.sendForm(inspectionServerUrl, "/api/sources/scm?key="+project.getKey(), HttpMethod.GET, null, Project.class);
        project.setScm(result.getScm());

        result = commonService.sendForm(inspectionServerUrl, "/api/issues/search?additionalFields=_all&resolved=false&fileUuids="+project.getUuid(), HttpMethod.GET, null, Project.class);
        project.setIssues(result.getIssues());



        return project;
    }


    //projectKey data 가져오기
    public Project getProjectKey(Project project){
        return commonService.sendForm(commonApiUrl, "/project/projectKey", HttpMethod.POST, project, Project.class);
    }

}

