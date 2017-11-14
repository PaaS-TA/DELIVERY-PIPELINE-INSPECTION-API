package paasta.delivery.pipeline.inspection.api.project;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGate;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGateService;
import paasta.delivery.pipeline.inspection.api.qualityProfile.QualityProfile;
import paasta.delivery.pipeline.inspection.api.qualityProfile.QualityProfileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Dojun on 2017-06-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectServiceTest {

    private static final long ID = 1;
    private static final String SONAR_NAME = "test-sonarName";
    private static final String NAME = "test-project-name";
    private static final String KEY = "test-key";
    private static final String SONAR_KEY = "test-sonarKey";
    private static final String QUALIFIER = "test-qualifier";
    private static final String QUALITY_PROFILE_ID = "10";
    private static final String QUALITY_GATE_ID = "1";
    private static final String SERVICE_INSTANCES_ID = "test-serviceInstances-id";
    private static final int PIPELINE_ID = 293;
    private static final long JOB_ID = 2L;
    private static final String BRANCH = "test-branch";
    private static final String PROJECT_NAME = "testProject";
    private static final String PROFILE_KEY = "test-profile-key";
    private static final String PROJECT_KEY = "test-key";
    private static final String GATE_DEFAULT_YN = "N";
    private static final String PROFILE_DEFAULT_YN = "N";
    private static final String PROJECT_ID = "1";
    private static final String GATE_ID = "1";

    private static final String RESOURCE = "test-resource";
    private static final String METRICS = "test-metrics";
    private static final String BASE_COMPONENT_KEY = "test-basecomponentkey";

    private static final long PROFILEID = 1;
    private static final long GATEID = 10;

    private static final String QUALITY_PROFILE_NAME = "test-qualityProfileName";
    private static final String QUALITY_GATE_NAME = "test-qualityGateeName";

    private static final List MSR = new ArrayList();
    private static final List COMPONENTS = new ArrayList();
    private static final List SOURCES = new ArrayList();
    private static final List SCM = new ArrayList();
    private static final List ISSUES = new ArrayList();

    private static Project testModel = null;
    private static Project resultModel = null;
    private static QualityProfile profileModel = null;
    private static QualityGate gateModel = null;


    private static List<Map<String, Object>> testResultList = null;
    private static List<Map<String, Object>> testList = null;




    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;


    @Value("${inspection.server.url}")
    private String inspectionServerUrl;

    @InjectMocks
    private ProjectService projectService;




    @Mock
    private RestTemplate restTemplate;

    @Mock
    private QualityProfileService qualityProfileService;

    @Mock
    private QualityGateService qualityGateService;

    @Mock
    private CommonService commonService;


    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {



        Map<String, Object> testResultMap = new HashMap<>();

        testList = new ArrayList();
        Map<String, Object> testMap = new HashMap<>();


        testModel = new Project();

//        testModel.setId(ID);
        testModel.setSonarName(SONAR_NAME);
        testModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
//        testModel.setName(NAME);
//        testModel.setKey(KEY);
        testModel.setSonarKey(SONAR_KEY);
        testModel.setQualifier(QUALIFIER);
//        testModel.setQualityProfileId(QUALITY_PROFILE_ID);
//        testModel.setQualityGateId(QUALITY_GATE_ID);
        testModel.setProfileKey(PROFILE_KEY);
        testModel.setProjectKey(PROJECT_KEY);
        testModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        testModel.setProjectId(PROJECT_ID);
        testModel.setJobId(JOB_ID);
        testModel.setBranch(BRANCH);
        testModel.setGateDefaultYn(GATE_DEFAULT_YN);
        testModel.setProfileDefaultYn(PROFILE_DEFAULT_YN);
        testModel.setGateId(GATE_ID);
        testModel.setProjectId(PROJECT_ID);



        resultModel = new Project();

        testMap.put("scm","test-Scm");
        testMap.put("issues","test-issues");
        testMap.put("components","test-Components");
        testMap.put("baseComponent","test-baseComponent");
        testMap.put("sources","test-sources");
        testList.add(testMap);

//        resultModel.setId(testModel.getId());
        resultModel.setMetrics(METRICS);
        resultModel.setResource(RESOURCE);
        resultModel.setBaseComponent(testList);
        resultModel.setBaseComponentKey(BASE_COMPONENT_KEY);
        resultModel.setComponents(testList);
        resultModel.setSources(testList);
        resultModel.setScm(testList);
        resultModel.setIssues(testList);

        resultModel.setProjectName(PROJECT_NAME);
        resultModel.setSonarName(SONAR_NAME);
        resultModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        resultModel.setSonarKey(SONAR_KEY);
//        resultModel.setQualityProfileId(QUALITY_PROFILE_ID);
//        resultModel.setQualityGateId(QUALITY_GATE_ID);
        resultModel.setJobId(JOB_ID);

        resultModel.setScm(SCM);
        resultModel.setIssues(ISSUES);
        resultModel.setSources(SOURCES);
        resultModel.setComponents(COMPONENTS);
        resultModel.setMsr(MSR);


        gateModel = new QualityGate();
//        gateModel.setId(GATEID);
//        gateModel.setName(QUALITY_GATE_NAME);
        gateModel.setGateDefaultYn(GATE_DEFAULT_YN);
        gateModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
//        gateModel.setDefaultYn(GATE_DEFAULT_YN);


        profileModel = new QualityProfile();
//        profileModel.setId(PROFILEID);
//        profileModel.setName(QUALITY_PROFILE_NAME);
        profileModel.setSonarKey(SONAR_KEY);
        profileModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        profileModel.setProfileDefaultYn(PROFILE_DEFAULT_YN);
        profileModel.setLanguage("test-language");
        profileModel.setLanguageName("test-languageName");



        testResultMap.put("id",ID);
        testResultMap.put("projectName",PROJECT_NAME);
        testResultMap.put("sonarName",SONAR_NAME);
        testResultMap.put("serviceInstancesId",SERVICE_INSTANCES_ID);
        testResultMap.put("sonarKey", SONAR_KEY);
        testResultMap.put("qualityProfileId",QUALITY_PROFILE_ID);
        testResultMap.put("qualityGateId",QUALITY_GATE_ID);
        testResultMap.put("jobId",JOB_ID);

        testResultList = new ArrayList<>();

        testResultList.add(testResultMap);







    }


    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getProjectList_Valid_Return() throws Exception {
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsList", HttpMethod.POST , testModel ,List.class)).thenReturn(testResultList);
        testResultList = projectService.getProjectsList(testModel);
    }

    @Test
    public void getProject_Valid_Return() throws Exception{
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/getProject", HttpMethod.POST , testModel, List.class)).thenReturn(testResultList);
        testResultList = projectService.getProject(testModel);
    }

    @Test
    public void createProjectsCase1_Vaild_Return() throws Exception{
        testModel.setLinked(true);

        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(Matchers.anyString(), any(HttpMethod.class), Matchers.<HttpEntity<?>>any(), Matchers.<Class<Project>>any())).thenReturn(responseEntity);
        when(commonService.sendForm(anyString(),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);
        when(commonService.sendForm(Matchers.matches("http://localhost:8081"),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);

//        when(commonService.sendForm(Matchers.matches("http://localhost:8081"),anyString(),any(HttpMethod.class),any(QualityProfile.class),any())).thenReturn(profileModel);
//        when(commonService.sendForm(Matchers.matches("http://localhost:8081"),anyString(),any(HttpMethod.class),any(QualityGate.class),any())).thenReturn(gateModel);

        long testId = 1;

        when(qualityProfileService.getQualityProfile(testId)).thenReturn(profileModel);
        when(qualityGateService.getiQualityGate(testId)).thenReturn(gateModel);

        profileModel.setProfileDefaultYn("N");
        gateModel.setGateDefaultYn("N");

        when(projectService.qualityGateProjectLiked(testModel)).thenReturn(resultModel);
        when(projectService.qualityProfileProjectLinked(testModel)).thenReturn(resultModel);

//        projectService.createProjects(testModel);

    }

/*
    @Test
    public void createProjectsCase2_Vaild_Return() throws Exception{
        testModel.setLinked(true);
        profileModel.setProfileDefaultYn("Y");
        gateModel.setGateDefaultYn("N");
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(Matchers.anyString(), any(HttpMethod.class), Matchers.<HttpEntity<?>>any(), Matchers.<Class<Project>>any())).thenReturn(responseEntity);
        when(commonService.sendForm(anyString(),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);
        when(commonService.sendForm(Matchers.matches("http://localhost:8081"),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);

        when(qualityProfileService.getQualityProfile(profileModel.getId())).thenReturn(profileModel);
        when(qualityGateService.getiQualityGate(gateModel.getId())).thenReturn(gateModel);

        when(projectService.qualityGateProjectLiked(testModel)).thenReturn(resultModel);
        projectService.createProjects(testModel);

    }

    @Test
    public void createProjectsCase3_Vaild_Return() throws Exception{
        testModel.setLinked(true);
        profileModel.setProfileDefaultYn("N");
        gateModel.setGateDefaultYn("Y");
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(Matchers.anyString(), any(HttpMethod.class), Matchers.<HttpEntity<?>>any(), Matchers.<Class<Project>>any())).thenReturn(responseEntity);
        when(commonService.sendForm(anyString(),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);
        when(commonService.sendForm(Matchers.matches("http://localhost:8081"),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);


        when(qualityGateService.getiQualityGate(gateModel.getId())).thenReturn(gateModel);
        when(projectService.qualityGateProjectLiked(testModel)).thenReturn(resultModel);

        when(projectService.qualityProfileProjectLinked(testModel)).thenReturn(resultModel);
        projectService.createProjects(testModel);

    }

    @Test
    public void createProjectsCase4_Vaild_Return() throws Exception{
        testModel.setLinked(true);
        profileModel.setProfileDefaultYn("Y");
        gateModel.setGateDefaultYn("Y");
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(Matchers.anyString(), any(HttpMethod.class), Matchers.<HttpEntity<?>>any(), Matchers.<Class<Project>>any())).thenReturn(responseEntity);
        when(commonService.sendForm(anyString(),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);
        when(commonService.sendForm(Matchers.matches("http://localhost:8081"),anyString(),any(HttpMethod.class),any(Project.class),any())).thenReturn(resultModel);

        when(qualityGateService.getiQualityGate(gateModel.getId())).thenReturn(gateModel);
        when(projectService.qualityGateProjectLiked(testModel)).thenReturn(resultModel);
        projectService.createProjects(testModel);

    }*/


    @Test
    public void deleteProjects_Valid_Return() throws Exception {


        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/projects/delete" , HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsDelete", HttpMethod.DELETE, testModel, Project.class)).thenReturn(resultModel);

        resultModel = projectService.deleteProjects(testModel);
    }

    @Test
    public void updateProjects_Valid_Return() throws Exception {


        testModel.setLinked(true);

//        long profileId = Long.parseLong(testModel.getQualityProfileId());
//        long gateId = Long.parseLong(testModel.getQualityGateId());

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsUpdate", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);
        when(projectService.getProjectKey(testModel)).thenReturn(resultModel);

//        when(qualityProfileService.getQualityProfile(profileId)).thenReturn(profileModel);
//        when(qualityGateService.getiQualityGate(gateId)).thenReturn(gateModel);

        when(projectService.qualityProfileProjectLinked(testModel)).thenReturn(resultModel);
        when(projectService.qualityGateProjectLiked(testModel)).thenReturn(resultModel);


        projectService.updateProjects(testModel);
    }

    @Test
    public void qualityGateProjectLikedTrue_Valid_Return() throws Exception {
        testModel.setLinked(true);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/select", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityGateProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityGateProjectLiked(testModel);
    }

    @Test
    public void qualityGateProjectLikedFalse_Valid_Return() throws Exception {
        testModel.setLinked(false);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/deselect", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityGateProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityGateProjectLiked(testModel);
    }

    @Test
    public void qualityProfileProjectLinkedTrue_Valid_Return() throws Exception {
        testModel.setLinked(true);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/add_project", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityProfileProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityProfileProjectLinked(testModel);
    }

    @Test
    public void qualityProfileProjectLinkedFalse_Valid_Return() throws Exception {

        testModel.setLinked(false);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/remove_project", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityProfileProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityProfileProjectLinked(testModel);
    }


    @Test
    public void getProjectSonarKey_Valid_Return() throws Exception {

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/resources?resource="+testModel.getProjectKey(), HttpMethod.GET, null, List.class)).thenReturn(testResultList);
        projectService.getProjectSonarKey(testModel);
    }

    @Test
    public void qualityManagementList_Valid_Return() throws Exception {
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/components/app?uuid="+testModel.getUuid(), HttpMethod.GET, null, Project.class)).thenReturn(resultModel);
        projectService.qualityManagementList(testModel);
    }

    @Test
    public void qualityCoverageList_Valid_Return() throws Exception {

        String coverageUrl = "/api/resources?resource="+testModel.getResource()+"&metrics=coverage,line_coverage,uncovered_lines" +
                ",lines_to_cover,branch_coverage,uncovered_conditions,conditions_to_cover,new_line_coverage"+
                ",tests,test_execution_time,test_errors,test_failures,skipped_tests,test_success_density,new_coverage,ncloc&includetrends=true";
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, coverageUrl, HttpMethod.GET, null, List.class)).thenReturn(testResultList);

        projectService.qualityCoverageList(testModel);
    }


    @Test
    public void testsSourceList_Valid_Return() throws Exception {

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/resources?metrics="+testModel.getMetrics()+"&depth=-1&resource="+testModel.getProjectKey(), HttpMethod.GET, null, List.class)).thenReturn(testResultList);
        projectService.testsSourceList(testModel);
    }

    @Test
    public void testsSourceShow_Valid_Return() throws Exception {

        List resultList = new ArrayList();
//        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/resources?metrics=coverage_line_hits_data,covered_conditions_by_line&resource="+testModel.getKey(), HttpMethod.GET, null, List.class)).thenReturn(resultList);
        resultModel.setMsr(resultList);
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), Matchers.<HttpEntity<?>>any(), Matchers.<Class>any())).thenReturn(responseEntity);
        when(commonService.sendForm(anyString(),anyString(),any(HttpMethod.class),any(Class.class),any())).thenReturn(resultModel);


//        projectService.testsSourceShow(testModel);
    }




    @Test
    public void getProjectKey_Valid_Return() throws Exception {

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectKey", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);

        resultModel = projectService.getProjectKey(testModel);
    }

}




