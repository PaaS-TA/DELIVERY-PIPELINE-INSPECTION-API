package paasta.delivery.pipeline.inspection.api.project;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGate;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGateService;
import paasta.delivery.pipeline.inspection.api.qualityProfile.QualityProfile;
import paasta.delivery.pipeline.inspection.api.qualityProfile.QualityProfileService;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Dojun on 2017-06-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectServiceTest {

    private static final long ID = 110;
    private static final String SERVICE_INSTANCES_ID = "09f060c6-ef13-464b-b0c5-d23f863c4960";
    private static final int PIPELINE_ID = 293;
    private static final String KEY = "116ed8ded2e6927caa0dbb975b8ea87a";
    private static final String SONAR_KEY = "116ed8ded2e6927caa0dbb975b8ea87a";
    private static final String SONAR_NAME = "116ed8ded2e6927caa0dbb975b8ea90e_20171026071922";
    private static final String PROJECT_NAME = "testProject";
    private static final String QUALITY_PROFILE_ID = "10";
    private static final String QUALITY_GATE_ID = "1";
    private static final String NAME = "testProject";

    private static final String PROFILE_KEY = "java-quality-copy-55679";
    private static final String PROJECT_KEY = "84445a412f5a419fbe14615c8aa5077d";
    private static final String GATE_DEFAULT_YN = "N";
    private static final String PROFILE_DEFAULT_YN = "N";
    private static final String PROJECT_ID = "110";
    private static final String GATE_ID = "10";




    @InjectMocks
    private ProjectService projectService;
    private QualityProfileService qualityProfileService;
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

        List resultModel = new ArrayList();
        Project testModel = new Project();
        testModel.setServiceInstancesId(SERVICE_INSTANCES_ID);

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsList", HttpMethod.POST , testModel ,List.class)).thenReturn(resultModel);

        projectService.getProjecstList(testModel);
    }

    @Test
    public void getProject_Valid_Return() throws Exception{

        Project testModel = new Project();
        List resultModel = new ArrayList();

        testModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        testModel.setPipelineId(PIPELINE_ID);

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/getProject", HttpMethod.POST , testModel, List.class)).thenReturn(resultModel);

        projectService.getProject(testModel);
    }

    @Test
    public void createProjects_Vaild_Return() throws Exception{

        Project testModel = new Project();
        Project resultModel = new Project();

        QualityProfile profileTest = new QualityProfile();
        QualityGate gateTest = new QualityGate();

        testModel.setKey(KEY);
        testModel.setSonarKey(SONAR_KEY);
        testModel.setSonarName(SONAR_NAME);
        testModel.setProjectName(PROJECT_NAME);
        testModel.setQualityProfileId(QUALITY_PROFILE_ID);
        testModel.setQualityGateId(QUALITY_GATE_ID);


        testModel.setName(NAME);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/projects/create" , HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);

        testModel.setId(resultModel.getId());

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsCreate", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);

/*        long profileId = Long.parseLong(testModel.getQualityProfileId());
        long gateId = Long.parseLong(testModel.getQualityGateId());

        when(qualityProfileService.getQualityProfile(profileId)).thenReturn(profileTest);
        when(qualityGateService.getiQualityGate(gateId)).thenReturn(gateTest);*/





/*        profileTest.setProfileDefaultYn("N");
        gateTest.setGateDefaultYn("N");

        testModel.setProfileKey(testModel.getKey());
        testModel.

        project.setProfileKey(profileTest.getKey());
        project.setProjectKey(profileTest.getKey());
        project.setLinked(true);
        qualityProfileProjectLinked(project);*/


    }


    @Test
    public void deleteProjects_Valid_Return() throws Exception {

        Project testModel = new Project();
        Project resultModel = new Project();

        testModel.setId(110);
        testModel.setKey(KEY);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/projects/delete" , HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsDelete", HttpMethod.DELETE, testModel, Project.class)).thenReturn(resultModel);

        projectService.deleteProjects(testModel);
    }

    @Test
    public void updateProjects_Valid_Return() throws Exception {

        Project testModel = new Project();
        Project resultModel = new Project();

        testModel.setId(ID);
        testModel.setProfileKey(PROFILE_KEY);
        testModel.setProjectKey(PROJECT_KEY);
        testModel.setGateDefaultYn(GATE_DEFAULT_YN);
        testModel.setProfileDefaultYn(PROFILE_DEFAULT_YN);

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsUpdate", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

//        projectService.updateProjects(testModel);
    }

    @Test
    public void qualityGateProjectLikedTrue_Valid_Return() throws Exception {
        Project testModel = new Project();
        Project resultModel = new Project();

        testModel.setId(ID);
        testModel.setProjectId(PROJECT_ID);
        testModel.setGateId(GATE_ID);
        testModel.setQualityGateId(QUALITY_GATE_ID);
        testModel.setLinked(true);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/select", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityGateProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityGateProjectLiked(testModel);
    }

    @Test
    public void qualityGateProjectLikedFalse_Valid_Return() throws Exception {
        Project testModel = new Project();
        Project resultModel = new Project();

        testModel.setId(ID);
        testModel.setProjectId(PROJECT_ID);
        testModel.setGateId(GATE_ID);
        testModel.setQualityGateId(QUALITY_GATE_ID);
        testModel.setLinked(false);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/deselect", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityGateProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityGateProjectLiked(testModel);
    }

    @Test
    public void qualityProfileProjectLinkedTrue_Valid_Return() throws Exception {
        Project testModel = new Project();
        Project resultModel = new Project();

        testModel.setQualityProfileId(QUALITY_GATE_ID);
        testModel.setProjectKey(PROJECT_KEY);
        testModel.setProfileKey(PROFILE_KEY);
        testModel.setSonarKey(SONAR_KEY);
        testModel.setId(ID);
        testModel.setLinked(true);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/add_project", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityProfileProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityProfileProjectLinked(testModel);
    }

    @Test
    public void qualityProfileProjectLinkedFalse_Valid_Return() throws Exception {
        Project testModel = new Project();
        Project resultModel = new Project();

        testModel.setQualityProfileId(QUALITY_GATE_ID);
        testModel.setProjectKey(PROJECT_KEY);
        testModel.setProfileKey(PROFILE_KEY);
        testModel.setSonarKey(SONAR_KEY);
        testModel.setId(ID);
        testModel.setLinked(false);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/remove_project", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/qualityProfileProjectLiked", HttpMethod.PUT, testModel, Project.class)).thenReturn(resultModel);

        projectService.qualityProfileProjectLinked(testModel);
    }


    @Test
    public void getProjectSonarKey_Valid_Return() throws Exception {
        Project testModel = new Project();
        List resultModel = new ArrayList();

        testModel.setProjectKey(PROJECT_KEY);
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/resources?resource="+testModel.getProjectKey(), HttpMethod.GET, null, List.class)).thenReturn(resultModel);

        projectService.getProjectSonarKey(testModel);
    }

    @Test
    public void qualityManagementList_Valid_Return() throws Exception {
        Project testModel = new Project();
        Project resultModel = new Project();

        testModel.setUuid("AV9S2Eokknh2OSbD9w8E");

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/components/app?uuid="+testModel.getUuid(), HttpMethod.GET, null, Project.class)).thenReturn(resultModel);
        projectService.qualityManagementList(testModel);
    }

    @Test
    public void qualityCoverageList_Valid_Return() throws Exception {
        Project testModel = new Project();
        List resultModel = new ArrayList();

        testModel.setResource("09f060c6-ef13-464b-b0c5-d23f863c4960");

        String coverageUrl = "/api/resources?resource="+testModel.getResource()+"&metrics=coverage,line_coverage,uncovered_lines" +
                ",lines_to_cover,branch_coverage,uncovered_conditions,conditions_to_cover,new_line_coverage"+
                ",tests,test_execution_time,test_errors,test_failures,skipped_tests,test_success_density,new_coverage,ncloc&includetrends=true";
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, coverageUrl, HttpMethod.GET, null, List.class)).thenReturn(resultModel);

        projectService.qualityCoverageList(testModel);
    }


    @Test
    public void testsSourceList_Valid_Return() throws Exception {
        Project testModel = new Project();
        List resultModel = new ArrayList();

        testModel.setMetrics("branch_coverage");
        testModel.setProjectKey("09f060c6-ef13-464b-b0c5-d23f863c4960");
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/resources?metrics="+testModel.getMetrics()+"&depth=-1&resource="+testModel.getProjectKey(), HttpMethod.GET, null, List.class)).thenReturn(resultModel);

        projectService.testsSourceList(testModel);
    }

    @Test
    public void testsSourceShow_Valid_Return() throws Exception {
        Project testModel = new Project();
        Project resultModel = new Project();
        List listModel = new ArrayList();

        testModel.setKey(KEY);
        testModel.setUuid("AV9S2Eokknh2OSbD9w8E");

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/resources?metrics=coverage_line_hits_data,covered_conditions_by_line&resource="+testModel.getKey(), HttpMethod.GET, null, List.class)).thenReturn(listModel);
        testModel.setMsr(listModel);

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/sources/show?key="+testModel.getKey(), HttpMethod.GET, null, Project.class)).thenReturn(resultModel);
        testModel.setSources(resultModel.getSources());

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/sources/scm?key="+testModel.getKey(), HttpMethod.GET, null, Project.class)).thenReturn(resultModel);
        testModel.setScm(resultModel.getScm());

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/issues/search?additionalFields=_all&resolved=false&fileUuids="+testModel.getUuid(), HttpMethod.GET, null, Project.class)).thenReturn(resultModel);
        testModel.setIssues(resultModel.getIssues());

//        projectService.testsSourceShow(testModel);
    }




    @Test
    public void getProjectKey_Valid_Return() throws Exception {
        Project testModel = new Project();
        Project resultModel = new Project();
        testModel.setId(ID);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectKey", HttpMethod.POST, testModel, Project.class)).thenReturn(resultModel);

        projectService.getProjectKey(testModel);
    }

}




