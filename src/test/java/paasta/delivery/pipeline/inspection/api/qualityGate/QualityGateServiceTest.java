package paasta.delivery.pipeline.inspection.api.qualityGate;

import javafx.beans.binding.When;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by kim on 2017-10-26.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QualityGateServiceTest {

    private static final long ID = 1L;
    private static final String UUID = "test-uuid";
    private static final String NAME = "test-gate-name";
    private static final String GATE_ID = "1";
    private static final String METRIC = "test-metrics";
    private static final String ERROR = "test-error";
    private static final String WARNING = "test-warning";
    private static final String OP = "test-op";
    private static final String SERVICE_INSTANCES_ID = "test-serviceInstances-id";
    private static final String GATE_DEFAUTL_YN = "N";

    private static QualityGate testModel = null;
    private static QualityGate resultModel = null;
    private static List<Map<String, Object>> testResultList = null;
    private static Map<String, Object> testResultMap = null;



    @Mock
    private CommonService commonService;

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
        testModel.setGateDefaultYn(GATE_DEFAUTL_YN);
        testModel.setName(NAME);
        testModel.setOp(OP);
        testModel.setWarning(WARNING);
        testModel.setError(ERROR);
        testModel.setUuid(UUID);
        testModel.setGateId(GATE_ID);
        testModel.setMetric(METRIC);

        resultModel.setId(ID);
        resultModel.setName(NAME);
        resultModel.setGateDefaultYn(GATE_DEFAUTL_YN);
        resultModel.setServiceInstancesId(SERVICE_INSTANCES_ID);



        testResultMap.put("id",ID);
        testResultMap.put("name",NAME);
        testResultMap.put("gateDefaultYn",GATE_DEFAUTL_YN);
        testResultMap.put("serviceInstancesId",SERVICE_INSTANCES_ID);


        testResultMap.put("gateId", GATE_ID);
        testResultMap.put("metric", METRIC);
        testResultMap.put("error", ERROR);
        testResultMap.put("warning", WARNING);
        testResultMap.put("op", OP);

        testResultList.add(testResultMap);


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

        testResultList = qualityGateService.getQualityGateList(testServiceInstancesId);
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

        resultModel = qualityGateService.createQualityGateCond(testModel);
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


        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/create", HttpMethod.POST,testModel, QualityGate.class)).thenReturn(resultModel);



        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateCreate", HttpMethod.POST, testModel,QualityGate.class)).thenReturn(resultModel);

//        qualityGateService.createQualityGate(testModel);
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
