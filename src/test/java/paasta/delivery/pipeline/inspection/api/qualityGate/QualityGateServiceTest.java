package paasta.delivery.pipeline.inspection.api.qualityGate;


import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.*;

/**
 * Created by kim on 2017-10-26.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class QualityGateServiceTest {


    private static final long ID =22221;
    private static final String STRING_ID = "1";
    private static final String UUID = "test-uuid";
    private static final String NAME = "test-gate-name";
    private static final String GATE_ID = "1";
    private static final String METRIC = "test-metrics";
    private static final String ERROR = "test-error";
    private static final String WARNING = "test-warning";
    private static final String OP = "test-op";
    private static final String SERVICE_INSTANCES_ID = "test-serviceInstances-id";
    private static final String GATE_DEFAULT_YN = "N";


    private static List<Long> PROJECT_ID_LIST = null;
    private static List CONDITIONS = null;
    private static List METRICS = null;
    private static List QUALITY_GATES = null;
    private static List DOMAINS = null;
    private static final Boolean LINKED = true;
    private static final String DEFAULT_KEY = "test-default-key";
    private static QualityGate testModel = null;
    private static QualityGate resultModel = null;

    private static List<Map<String, String>> testResultList = null;
    private static Map<String, String> testResultMap = null;

    private static final String CREATED = "";
    private static final String LAST_MODIFIED = "";
    private static final String CREATED_STRING = "";
    private static final String LASTMODIFIED_STRING = "";
    private static final String RESULT_STATUS = "";
    private static final String RESULT_MESSAGE = "SUCCESS";





    @Value("${inspection.server.url}")
    private String inspectionServerUrl;

    @Value("${commonApi.url}")
    private String commonApiUrl;

    @Value("${inspection.server.admin.username}")
    public String adminUserName;
    /**
     * The Admin password.
     */
    @Value("${inspection.server.admin.password}")
    public String adminPassword;


    @Mock
    private MockRestServiceServer mockServer;

    @Mock
    private CommonService commonService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private  QualityGateService qualityGateService;




    /*
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {



        testModel = new QualityGate();
        resultModel = new QualityGate();
        testResultList = new ArrayList<>();
        testResultMap = new HashMap<>();




        testModel.setId(ID);
        testModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        testModel.setGateDefaultYn(GATE_DEFAULT_YN);
        testModel.setName(NAME);
        testModel.setOp(OP);
        testModel.setWarning(WARNING);
        testModel.setError(ERROR);
        testModel.setUuid(UUID);
        testModel.setGateId(GATE_ID);
        testModel.setMetric(METRIC);
        testModel.setDefaultYn(GATE_DEFAULT_YN);
        testModel.setLinked(LINKED);
        testModel.setCreated(CREATED);
        testModel.setResultMessage(RESULT_MESSAGE);
        testModel.setLastModified(LAST_MODIFIED);
        testModel.setLastModifiedString(LASTMODIFIED_STRING);
        testModel.setCreatedString(CREATED_STRING);
        testModel.setDefaultKey(DEFAULT_KEY);

        resultModel.setId(ID);
        resultModel.setName(NAME);
        resultModel.setGateDefaultYn(GATE_DEFAULT_YN);
        resultModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        resultModel.setDefaultYn(GATE_DEFAULT_YN);
        resultModel.setMetrics(METRICS);
        resultModel.setDomains(DOMAINS);
        resultModel.setConditions(CONDITIONS);
        resultModel.setProjectIdList(PROJECT_ID_LIST);
        resultModel.setDefaultKey(DEFAULT_KEY);
        resultModel.setOp(OP);
        resultModel.setWarning(WARNING);
        resultModel.setError(ERROR);
        resultModel.setUuid(UUID);
        resultModel.setGateId(GATE_ID);
        resultModel.setMetric(METRIC);
        resultModel.setDefaultYn(GATE_DEFAULT_YN);
        resultModel.setLinked(LINKED);
        resultModel.setLastModified(LAST_MODIFIED);
        resultModel.setCreated(CREATED);
        resultModel.setResultMessage(RESULT_MESSAGE);
        resultModel.setLastModifiedString(LASTMODIFIED_STRING);
        resultModel.setCreatedString(CREATED_STRING);



        testResultMap.put("id", STRING_ID);
        testResultMap.put("name",testModel.getName());
        testResultMap.put("gateDefaultYn",testModel.getGateDefaultYn());
        testResultMap.put("serviceInstancesId",testModel.getServiceInstancesId());
        testResultMap.put("defaultYn",GATE_DEFAULT_YN);
        testResultMap.put("gateId", GATE_ID);
        testResultMap.put("metric", METRIC);
        testResultMap.put("error", ERROR);
        testResultMap.put("warning", WARNING);
        testResultMap.put("op", OP);


        testResultList.add(testResultMap);

        MockitoAnnotations.initMocks(this);


        mockServer = MockRestServiceServer.createServer(restTemplate);

        ReflectionTestUtils.setField(qualityGateService, "inspectionServerUrl", inspectionServerUrl);
        ReflectionTestUtils.setField(qualityGateService, "commonApiUrl", commonApiUrl);
//        ReflectionTestUtils.setField(qualityGateService, "adminUserName", adminUserName);
//        ReflectionTestUtils.setField(qualityGateService, "adminPassword", adminPassword);
//        ReflectionTestUtils.setField(qualityGateService, "restTemplate", restTemplate);
    }

    /*
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     *  QualityGate 리스트 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getQualityGateList_Valid_Return() throws Exception{

        String testServiceInstancesId = "";
        testServiceInstancesId = "test-serviceInstanceId";
        when(qualityGateService.getQualityGateList(testServiceInstancesId)).thenReturn(testResultList);

        List<Map<String, String>> result = qualityGateService.getQualityGateList(testServiceInstancesId);

    }

    /**
     *  QualityGate 조건 옵션 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getMetricsList_Valid_Return() throws Exception{

        when(qualityGateService.getMetricsList()).thenReturn(testResultList);

        testResultList = qualityGateService.getMetricsList();
    }

    /**
     *  QualityGate 조건 목록 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getQualityGateCondition_Valid_Return() throws Exception{
        long id = 1L;
        when(qualityGateService.getQualityGateCondition(id)).thenReturn(resultModel);

        resultModel = qualityGateService.getQualityGateCondition(id);
    }

    /**
     *  QualityGate 조건 저장
     *
     * @throws Exception the exception
     */
    @Test
    public void createQualityGateCond_Valid_Return() throws Exception{

        when(qualityGateService.createQualityGateCond(testModel)).thenReturn(resultModel);

        QualityGate result = qualityGateService.createQualityGateCond(testModel);
        assertNotNull(result);
    }

    /**
     *  QualityGate 조건 수정
     *
     * @throws Exception the exception
     */
    @Test
    public void updateQualityGateCond_Valid_Return() throws Exception{

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API,"/api/qualitygates/update_condition" , HttpMethod.POST,testResultMap ,QualityGate.class)).thenReturn(resultModel);

        qualityGateService.updateQualityGateCond(testModel);

    }

    /**
     *  QualityGate 조건 삭제
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteQualityGateCond_Valid_Return() throws Exception{


        String id = "34";
        when(qualityGateService.deleteQualityGateCond(id)).thenReturn(resultModel);

        qualityGateService.deleteQualityGateCond(id);

    }

    /**
     *  QualityGate 복제
     *
     * @throws Exception the exception
     */
    @Test
    public void copyQualityGate_Valid_Return() throws Exception{


        when(commonService.sendForm(Constants.TARGET_INSPECTION_API,"/api/qualitygates/copy",HttpMethod.POST,testResultMap, QualityGate.class)).thenReturn(testModel);

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateCopy", HttpMethod.POST, testModel, QualityGate.class)).thenReturn(resultModel);


//        resultModel = qualityGateService.copyQualityGate(testModel);
    }


    /**
     *  QualityGate 생성
     *
     * @throws Exception the exception
     */
    @Test
    public void createQualityGate_Valid_Return() throws Exception{
/*        QualityGate testQg = new QualityGate();
        testQg.setName("test-gate");

        String authorization = adminUserName + ":" + adminPassword;
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, "Basic " + Base64Utils.encodeToString(authorization.getBytes(StandardCharsets.UTF_8)));

        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> reqEntity = new HttpEntity<>(testQg, reqHeaders);

        ResponseEntity resEntity = null;

        when(restTemplate.exchange("http://localhost:9904/api/qualitygates/create",HttpMethod.POST,reqEntity, QualityGate.class)).thenReturn(resEntity);


        System.out.println("데이터 값:::::::"+resEntity);*/


//        when(restTemplate.postForEntity(anyString(), isA(HttpEntity.class), isA(Class.class))).thenReturn(any());

//        ReflectionTestUtils.getField(testModel,"QualityGate", new ObjectMapper());
//        ReflectionTestUtils.setField(resultModel, "restTemplate", restTemplate);
//        resultModel.setServiceInstancesId(testModel.getServiceInstancesId());
//        resultModel.setGateDefaultYn(testModel.getGateDefaultYn());



/*
        QualityGate inputModel = new QualityGate();
        inputModel.setName("gate-test-name");
        inputModel.setGateDefaultYn("N");
        inputModel.setServiceInstancesId("service-test");

        QualityGate outputModel = new QualityGate();
        outputModel.setId(2221);
        outputModel.setName(inputModel.getName());
        outputModel.setGateDefaultYn(inputModel.getGateDefaultYn());
        outputModel.setServiceInstancesId(inputModel.getServiceInstancesId());

        when(commonService.sendForm(inspectionServerUrl, "/api/qualitygates/create", HttpMethod.POST,inputModel, QualityGate.class)).thenReturn(outputModel);
        when(commonService.sendForm(commonApiUrl, "/qualityGate/qualityGateCreate", HttpMethod.POST, outputModel,QualityGate.class)).thenReturn(outputModel);

*/
/*        QualityGate mockedFoo = Mockito.mock(QualityGate.class);
        when(mockedFoo.getId()).thenReturn(resultModel.getId());
        when(mockedFoo.getName()).thenReturn(resultModel.getName());
        when(mockedFoo.getServiceInstancesId()).thenReturn(resultModel.getServiceInstancesId());
        when(mockedFoo.getGateDefaultYn()).thenReturn(resultModel.getGateDefaultYn());*/

 /*       입력 데이터 확인QualityGate{id=182, uuid='null', name='test-mock-uu', projectIdList=null, created='null', lastModified='null', createdString='null', lastModifiedString='null', resultStatus='null', resultMessage='null', conditions=null, metrics=null, gateId='null', metric='null', error='null', warning='null', op='null', serviceInstancesId='09f060c6-ef13-464b-b0c5-d23f863c4960', linked=null, qualitygates=null, defaultKey='null', gateDefaultYn='N', domains=null, defaultYn='null'}
        리턴 데이터 확인 :::::::QualityGate{id=182, uuid='null', name='test-mock-uu', projectIdList=null, created='1509944038241', lastModified='1509944038241', createdString='2017-11-06 13:53:58', lastModifiedString='2017-11-06 13:53:58', resultStatus='null', resultMessage='null', conditions=null, metrics=null, gateId='null', metric='null', error='null', warning='null', op='null', serviceInstancesId='09f060c6-ef13-464b-b0c5-d23f863c4960', linked=null, qualitygates=null, defaultKey='null', gateDefaultYn='N', domains=null, defaultYn='null'}

*/

        QualityGate inputModel = new QualityGate();
        inputModel.setName("gate-test-name");
        inputModel.setGateDefaultYn("N");
        inputModel.setServiceInstancesId("service-test");

        QualityGate outputModel = new QualityGate();
        outputModel.setId(2221);
        outputModel.setName(inputModel.getName());
        outputModel.setGateDefaultYn(inputModel.getGateDefaultYn());
        outputModel.setServiceInstancesId(inputModel.getServiceInstancesId());

        QualityGate inputModel2 = new QualityGate();
        inputModel2.setId(outputModel.getId());
        inputModel2.setName(outputModel.getName());
        inputModel2.setGateDefaultYn(outputModel.getGateDefaultYn());
        inputModel2.setServiceInstancesId(outputModel.getServiceInstancesId());



        QualityGate ouputModel2 = new QualityGate();
        ouputModel2.setId(2221);
        ouputModel2.setName(inputModel.getName());
        ouputModel2.setGateDefaultYn(inputModel.getGateDefaultYn());
        ouputModel2.setServiceInstancesId(inputModel.getServiceInstancesId());

        when(commonService.sendForm(inspectionServerUrl, "/api/qualitygates/create", HttpMethod.POST,inputModel, QualityGate.class)).thenReturn(outputModel);

        when(commonService.sendForm(commonApiUrl, "/qualityGate/qualityGateCreate", HttpMethod.POST, inputModel2,QualityGate.class)).thenReturn(ouputModel2);


        when(qualityGateService.createQualityGate(testModel)).thenReturn(resultModel);

        QualityGate result  = qualityGateService.createQualityGate(testModel);

        assertThat(result).isNotNull();
        assertEquals(resultModel.getId(), result.getId());
        assertEquals(resultModel.getName(),result.getName());
        assertEquals(resultModel.getGateDefaultYn(),result.getGateDefaultYn());
        assertEquals(resultModel.getServiceInstancesId(),result.getServiceInstancesId());
    }



    /**
     *  QualityGate 수정
     *
     * @throws Exception the exception
     */
    @Test
    public void updateQualityGate_Valid_Return() throws Exception{

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/rename", HttpMethod.POST, testResultMap,QualityGate.class)).thenReturn(resultModel);

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateUpdate", HttpMethod.PUT, testResultMap,QualityGate.class)).thenReturn(resultModel);

        qualityGateService.updateQualityGate(testModel);
    }

    /**
     *  QualityGate 삭제
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteQualityGate_Valid_Return() throws Exception{


        String result = "";

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/destroy", HttpMethod.POST,testResultMap, null)).thenReturn(null);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateDelete", HttpMethod.DELETE, testResultMap , null)).thenReturn(null);
        when(commonService.sendForm(Constants.TARGET_COMMON_API,"/project/qualityGateDelete",HttpMethod.PUT,testResultMap,String.class)).thenReturn(result);

        QualityGate resultModel = qualityGateService.deleteQualityGate(testModel);
        assertThat(resultModel).isNotNull();
        assertEquals(Constants.RESULT_STATUS_SUCCESS, resultModel.getResultStatus());

        qualityGateService.deleteQualityGate(testModel);
    }

    /**
     *  QualityGate 조건 domain
     *
     * @throws Exception the exception
     */
    @Test
    public void getQualityGateDomains_Valid_Return() throws Exception{

        QualityGate resultModel = new QualityGate();
        when(qualityGateService.getQualityGateDomains()).thenReturn(resultModel);

        qualityGateService.getQualityGateDomains();
    }

    /**
     * 품질 게이트 1건 검색
     * @param
     * @return
     */
    @Test
    public void getiQualityGate_Valid_Return() throws Exception{

        long id = 33;

        when(qualityGateService.getiQualityGate(id)).thenReturn(resultModel);

        qualityGateService.getiQualityGate(id);
    }

}
