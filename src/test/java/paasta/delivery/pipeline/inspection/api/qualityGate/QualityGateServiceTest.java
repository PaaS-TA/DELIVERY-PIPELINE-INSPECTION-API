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
import static org.mockito.Mockito.when;

/**
 * Created by kim on 2017-10-26.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QualityGateServiceTest {


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
        List listResult = new ArrayList<>();
        String testServiceInstancesId = "";
        testServiceInstancesId = "09f060c6-ef13-464b-b0c5-d23f863c4960";

        when(qualityGateService.getQualityGateList(testServiceInstancesId)).thenReturn(listResult);
    }

    /**
     *  QualityGate 조건 옵션 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getMetricsList_Valid_Return() throws Exception{
        List listResult = new ArrayList<>();

        when(qualityGateService.getMetricsList()).thenReturn(listResult);
    }

    /**
     *  QualityGate 조건 목록 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getQualityGateCondition_Valid_Return() throws Exception{
        QualityGate resultModel = new QualityGate();
        long id = 1;
        when(qualityGateService.getQualityGateCondition(id)).thenReturn(resultModel);
    }

    /**
     *  QualityGate 조건 저장
     *
     * @throws Exception the exception
     */
    @Test
    public void createQualityGateCond_Valid_Return() throws Exception{
        QualityGate testModel = new QualityGate();
        QualityGate resultModel = new QualityGate();

        testModel.setGateId("1");
        testModel.setMetric("blocker_violations");
        testModel.setError("1");
        testModel.setWarning("1");
        testModel.setOp("EQ");

        when(qualityGateService.createQualityGateCond(testModel)).thenReturn(resultModel);
    }

    /**
     *  QualityGate 조건 수정
     *
     * @throws Exception the exception
     */
    @Test
    public void updateQualityGateCond_Valid_Return() throws Exception{
        QualityGate testModel = new QualityGate();
        Map<String, String> mapModel = new HashMap<>();
        QualityGate resultModel = new QualityGate();

        testModel.setId(1);
        testModel.setGateId("1");
        testModel.setMetric("info_violations");
        testModel.setError("2");
        testModel.setWarning("2");
        testModel.setOp("EQ");

        mapModel.put("id"       , Long.toString(testModel.getId()));
        mapModel.put("gateId"   , testModel.getGateId());
        mapModel.put("metric"   ,testModel.getMetric());
        mapModel.put("error"    ,testModel.getError());
        mapModel.put("warning"  ,testModel.getWarning());
        mapModel.put("op"       ,testModel.getOp());

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API,"/api/qualitygates/update_condition" , HttpMethod.POST,mapModel ,QualityGate.class)).thenReturn(resultModel);

        qualityGateService.updateQualityGateCond(testModel);

    }

    /**
     *  QualityGate 조건 삭제
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteQualityGateCond_Valid_Return() throws Exception{

        QualityGate resultModel = new QualityGate();
        String id = "34";
        when(qualityGateService.deleteQualityGateCond(id)).thenReturn(resultModel);

    }

    /**
     *  QualityGate 복제
     *
     * @throws Exception the exception
     */
    @Test
    public void copyQualityGate_Valid_Return() throws Exception{

        QualityGate testModel = new QualityGate();
        Map<String, String> mapModel = new HashMap<>();
        QualityGate resultModel = new QualityGate();


        testModel.setId(33);
        testModel.setName("copy-gate");
        testModel.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setGateDefaultYn("Y");

        mapModel.put("id",Long.toString(testModel.getId()));
        mapModel.put("name",testModel.getName());


        when(commonService.sendForm(Constants.TARGET_INSPECTION_API,"/api/qualitygates/copy",HttpMethod.POST,mapModel, QualityGate.class)).thenReturn(resultModel);
        assertThat(resultModel).isNotNull();

//        resultModel = commonService.sendForm(Constants.TARGET_INSPECTION_API,"/api/qualitygates/copy",HttpMethod.POST,mapModel, QualityGate.class);


        mapModel.put("serviceInstancesId",testModel.getServiceInstancesId());
        mapModel.put("gateDefaultYn", testModel.getGateDefaultYn());
        resultModel.setServiceInstancesId(mapModel.get("serviceInstancesId"));
        resultModel.setGateDefaultYn(mapModel.get("gateDefaultYn"));

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateCopy", HttpMethod.POST, resultModel, QualityGate.class)).thenReturn(resultModel);
        assertThat(resultModel).isNotNull();
//        qualityGateService.copyQualityGate(resultModel);

//        assertEquals(Constants.RESULT_STATUS_SUCCESS, resultModel.getResultStatus());
    }


    /**
     *  QualityGate 생성
     *
     * @throws Exception the exception
     */
    @Test
    public void createQualityGate_Valid_Return() throws Exception{

        QualityGate testModel = new QualityGate();
        QualityGate resultModel = new QualityGate();

        testModel.setName("create-gate");
        testModel.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setGateDefaultYn("N");

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/create", HttpMethod.POST,testModel, QualityGate.class)).thenReturn(resultModel);
        assertThat(resultModel).isNotNull();

        resultModel.setServiceInstancesId(testModel.getServiceInstancesId());
        resultModel.setGateDefaultYn(testModel.getGateDefaultYn());
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateCreate", HttpMethod.POST, resultModel,QualityGate.class)).thenReturn(resultModel);

    }

    /**
     *  QualityGate 수정
     *
     * @throws Exception the exception
     */
    @Test
    public void updateQualityGate_Valid_Return() throws Exception{

        QualityGate testModel = new QualityGate();
        QualityGate resultModel = new QualityGate();
        Map<String, String> mapModel = new HashMap<>();

        testModel.setId(33);
        testModel.setName("update-gate");
        testModel.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setGateDefaultYn("N");

        mapModel.put("id",Long.toString(testModel.getId()));
        mapModel.put("name",testModel.getName());
        mapModel.put("serviceInstancesId", testModel.getServiceInstancesId());
        mapModel.put("gateDefaultYn",testModel.getGateDefaultYn());

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/rename", HttpMethod.POST, mapModel,QualityGate.class)).thenReturn(resultModel);

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateUpdate", HttpMethod.PUT, mapModel,QualityGate.class)).thenReturn(resultModel);
    }

    /**
     *  QualityGate 삭제
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteQualityGate_Valid_Return() throws Exception{

        QualityGate testModel = new QualityGate();

        Map<String, String> mapModel = new HashMap<>();
        String result = "";

        testModel.setId(33);
        testModel.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");

        mapModel.put("id",Long.toString(testModel.getId()));
        mapModel.put("serviceInstancesId", testModel.getServiceInstancesId());



        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualitygates/destroy", HttpMethod.POST,mapModel, null)).thenReturn(null);
        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityGate/qualityGateDelete", HttpMethod.DELETE, mapModel , null)).thenReturn(null);
        when(commonService.sendForm(Constants.TARGET_COMMON_API,"/project/qualityGateDelete",HttpMethod.PUT,mapModel,String.class)).thenReturn(result);

        QualityGate resultModel = qualityGateService.deleteQualityGate(testModel);
        assertThat(resultModel).isNotNull();
        assertEquals(Constants.RESULT_STATUS_SUCCESS, resultModel.getResultStatus());

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
    }

    /**
     * 품질 게이트 1건 검색
     * @param
     * @return
     */
    @Test
    public void getiQualityGate_Valid_Return() throws Exception{

        QualityGate resultModel = new QualityGate();

        long id = 33;

        when(qualityGateService.getiQualityGate(id)).thenReturn(resultModel);
    }

}
