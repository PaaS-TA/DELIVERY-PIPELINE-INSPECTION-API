package paasta.delivery.pipeline.inspection.api.qualityIssues;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;
import paasta.delivery.pipeline.inspection.api.project.Project;
import paasta.delivery.pipeline.inspection.api.project.ProjectService;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kim on 2017-10-27.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QualityIssuesServiceTest {

    private static final long ID = 1L;
    private static final String KEY= "test-key";
    private static final String UUID = "test-uuid";
    private static final String NAME = "test-issues-name";
    private static final String SERVICE_INSTANCES_ID = "test-serviceInstances-id";
    private static final String FILE_KEY = "test-file-key";
    private static final String TOTAL = "50";
    private static final String PS = "1";
    private static final String P = "1";
    private static final String RESOLUTIONS = "test-fixed";
    private static final String SEVERITIES = "test-major";
    private static final String STATUSES = "test-open";
    private static final String COMPONENTKEYS = "test-component-keys";
    private static final String RESOLVED = "test-resolved";
    private static final String ISSUE = "test-issue";

    private static QualityIssues testModel = null;
    private static QualityIssues resultModel = null;
    private static Project projectModel = null;

    @Mock
    private CommonService commonService;

/*    @Mock
    private ProjectService projectService;*/

    @InjectMocks
    private QualityIssuesService qualityIssuesService;

    /*
 * Sets up.
 *
 * @throws Exception the exception
 */
    @Before
    public void setUp() throws Exception {

        testModel = new QualityIssues();
        resultModel = new QualityIssues();
        projectModel = new Project();
//        Map<String, Object> testMap = new HashMap<>();

        testModel.setId(ID);
        testModel.setKey(KEY);
        testModel.setUuid(UUID);
        testModel.setName(NAME);
        testModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        testModel.setFileKey(FILE_KEY);
        testModel.setTotal(TOTAL);
        testModel.setPs(PS);
        testModel.setP(P);
        testModel.setResolved(RESOLVED);
        testModel.setSeverity(SEVERITIES);
        testModel.setResolutions(RESOLUTIONS);
        testModel.setStatuses(STATUSES);
        testModel.setComponentKeys(COMPONENTKEYS);
        testModel.setIssue(ISSUE);

        projectModel.setServiceInstancesId(SERVICE_INSTANCES_ID);




        resultModel.setId(ID);
        resultModel.setKey(KEY);
        resultModel.setUuid(UUID);
        resultModel.setName(NAME);
        resultModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        resultModel.setFileKey(FILE_KEY);
        resultModel.setTotal(TOTAL);
        resultModel.setPs(PS);
        resultModel.setP(P);
        resultModel.setResolved(RESOLVED);
        resultModel.setSeverity(SEVERITIES);
        resultModel.setResolutions(RESOLUTIONS);
        resultModel.setStatuses(STATUSES);
        resultModel.setComponentKeys(COMPONENTKEYS);
        resultModel.setIssue(ISSUE);


    }

    /*
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void qualityIssuesListCase1_Valid_Return() throws Exception{

        Project projectParam = new Project();
        QualityIssues testModel = new QualityIssues();
        QualityIssues resultModel = new QualityIssues();
        List<Map<String, String>> projectList = new ArrayList<>();


        projectParam.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setComponentKeys("aa99e034f4f947e28a45ba5de35085fd,2491c49756c54a42bfb0c868eb33ecf9");
        testModel.setResolutions("UNRESOLVED");
        testModel.setPs("100");
        testModel.setSeverities("MAJOR");
        testModel.setStatuses("OPEN");


        String  url = "/api/issues/search?additionalFields=_all&ps="+testModel.getPs()+"&severities="+testModel.getSeverities()+"&statuses="+testModel.getStatuses()+
                "&resolved=false&componentKeys="+testModel.getComponentKeys();

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsList", HttpMethod.POST,projectParam, List.class)).thenReturn(projectList);


        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, url, HttpMethod.GET,null, QualityIssues.class)).thenReturn(resultModel);

        qualityIssuesService.qualityIssuesList(testModel);
    }

    @Test
    public void qualityIssuesListCase2_Valid_Return() throws Exception{
        Project projectParam = new Project();
        QualityIssues testModel = new QualityIssues();
        QualityIssues resultModel = new QualityIssues();
        List<Map<String, String>> projectList = new ArrayList<>();


        projectParam.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setComponentKeys("aa99e034f4f947e28a45ba5de35085fd,2491c49756c54a42bfb0c868eb33ecf9");
        testModel.setResolutions("FIXED");
        testModel.setPs("100");
        testModel.setSeverities("MAJOR");
        testModel.setStatuses("OPEN");

        String url = "/api/issues/search?additionalFields=_all&ps="+testModel.getPs()+"&severities="+testModel.getSeverities()+"&statuses="+testModel.getStatuses()+
                "&componentKeys="+testModel.getComponentKeys()+"&resolutions="+testModel.getResolutions();

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsList", HttpMethod.POST,projectParam, List.class)).thenReturn(projectList);
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, url, HttpMethod.GET,null, QualityIssues.class)).thenReturn(resultModel);

        qualityIssuesService.qualityIssuesList(testModel);
    }

    @Test
    public void qualityIssuesListCase3_Valid_Return() throws Exception{
        Project projectParam = new Project();
        QualityIssues testModel = new QualityIssues();
        List<Map<String, String>> projectList = new ArrayList<>();

        projectParam.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setComponentKeys("aa99e034f4f947e28a45ba5de35085fd,2491c49756c54a42bfb0c868eb33ecf9");
        testModel.setResolutions("FIXED");
        testModel.setPs("100");
        testModel.setSeverities("MAJOR");
        testModel.setStatuses("OPEN");

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/project/projectsList", HttpMethod.POST,projectParam, List.class)).thenReturn(projectList);

        testModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        qualityIssuesService.qualityIssuesList(testModel);
    }

    @Test
    public void qualityIssuesListCase4_Valid_Return() throws Exception{

        QualityIssues testModel = new QualityIssues();
        QualityIssues resultModel = new QualityIssues();

        testModel.setComponentKeys("aa99e034f4f947e28a45ba5de35085fd,2491c49756c54a42bfb0c868eb33ecf9");
        testModel.setResolutions("UNRESOLVED");
        testModel.setPs("100");
        testModel.setSeverities("MAJOR");
        testModel.setStatuses("OPEN");

        String url = "/api/issues/search?additionalFields=_all&ps="+testModel.getPs()+"&severities="+testModel.getSeverities()+"&statuses="+testModel.getStatuses()+
                "&resolved=false&componentKeys="+testModel.getComponentKeys();

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, url, HttpMethod.GET,null, QualityIssues.class)).thenReturn(resultModel);

        qualityIssuesService.qualityIssuesList(testModel);
    }

    @Test
    public void qualityIssuesListCase5_Valid_Return() throws Exception{

        QualityIssues testModel = new QualityIssues();
        QualityIssues resultModel = new QualityIssues();

        testModel.setComponentKeys("aa99e034f4f947e28a45ba5de35085fd,2491c49756c54a42bfb0c868eb33ecf9");
        testModel.setResolutions("FIXED");
        testModel.setPs("100");
        testModel.setSeverities("MAJOR");
        testModel.setStatuses("OPEN");

        String url = "/api/issues/search?additionalFields=_all&ps="+testModel.getPs()+"&severities="+testModel.getSeverities()+"&statuses="+testModel.getStatuses()+
                "&componentKeys="+testModel.getComponentKeys()+"&resolutions="+testModel.getResolutions();

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, url, HttpMethod.GET,null, QualityIssues.class)).thenReturn(resultModel);

        qualityIssuesService.qualityIssuesList(testModel);
    }

    @Test
    public void getIssuesConditionList_Valid_Return() throws Exception{
        Project projectParam = new Project();
        List<QualityIssues> list = new ArrayList();


        QualityIssues testModel = new QualityIssues();
        QualityIssues resultModel = new QualityIssues();



        testModel.setComponentKeys("aa99e034f4f947e28a45ba5de35085fd,2491c49756c54a42bfb0c868eb33ecf9");
        projectParam.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        resultModel.setTotal("50");

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/issues/search?componentKeys="+testModel.getComponentKeys(), HttpMethod.GET,null, QualityIssues.class)).thenReturn(resultModel);


        int num = 5;

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/issues/search?additionalFields=_all&ps=500&pageIndex="+num+"&componentKeys="+testModel.getComponentKeys(), HttpMethod.GET,null, QualityIssues.class)).thenReturn(resultModel);

//        qualityIssuesService.getIssuesConditionList(testModel);
    }

    @Test
    public void getQualityIssuesDetail_Valid_Return() throws Exception{
        QualityIssues testModel = new QualityIssues();
        QualityIssues resultModel = new QualityIssues();
        List list = new ArrayList();


        testModel.setFileKey("AV9S2Eokknh2OSbD032S");
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/resources?metrics=lines,violations,coverage_line_hits_data,coverage&resource="+testModel.getFileKey(), HttpMethod.GET, null, List.class)).thenReturn(list);
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/sources/show?key="+testModel.getFileKey(), HttpMethod.GET, null, QualityIssues.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/sources/scm?key="+testModel.getFileKey(), HttpMethod.GET, null, QualityIssues.class)).thenReturn(resultModel);
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/issues/search?additionalFields=_all&resolved=false&componentKeys="+testModel.getFileKey(), HttpMethod.GET, null, QualityIssues.class)).thenReturn(resultModel);

        list.add(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/sources/show?key="+testModel.getFileKey(), HttpMethod.GET, null, QualityIssues.class));
        list.add(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/sources/scm?key="+testModel.getFileKey(), HttpMethod.GET, null, QualityIssues.class));
        list.add(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/issues/search?additionalFields=_all&resolved=false&componentKeys="+testModel.getFileKey(), HttpMethod.GET, null, QualityIssues.class));



//        qualityIssuesService.getQualityIssuesDetail(testModel);

    }

    @Test
    public void setSeverity_Valid_Return() throws Exception{
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/issues/set_severity", HttpMethod.POST, testModel, Object.class)).thenReturn(resultModel);
        qualityIssuesService.setSeverity(testModel);
    }







}
