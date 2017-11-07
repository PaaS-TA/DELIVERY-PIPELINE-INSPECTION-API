package paasta.delivery.pipeline.inspection.api.qualityProfile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kim on 2017-10-26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QualityProfileServiceTest {


    private static final long ID = 1;
    private static final String KEY = "test-profile-key";
    private static final String SONAR_KEY = "test-profile-key";
    private static final String NAME = "test-profile-name";
    private static final String LANGUAGE = "test-profile-language";
    private static final String LANGUAGE_NAME = "test-profile-language-name";
    private static final int ACTIVE_RULE_COUNT = 1;
    private static final int ACTIVE_DEPRECATED_RULE_COUNT = 1;
    private static final String SERVICE_INSTANCES_ID  = "test-instances-id";
    private static final String PROFILE_DEFAULT_YN = "N";
    private static final String TO_NAME = "test-to-name";
    private static final String FROM_KEY = "test-from-key";
    private static final String PROFILE_KEY = "test-profile-key";



    private static QualityProfile testModel = null;
    private static QualityProfile resultModel = null;

/*
    private List<Long> projectIdList;



    //언어 리스트
    private List languages;
*/



    @Mock
    private CommonService commonService;


    @Mock
    private RestTemplate restTemplate;


    @InjectMocks
    private QualityProfileService qualityProfileService;


    /*
 * Sets up.
 *
 * @throws Exception the exception
 */
    @Before
    public void setUp() throws Exception {
        testModel = new QualityProfile();
        resultModel = new QualityProfile();

        testModel.setId(ID);
        testModel.setSonarKey(SONAR_KEY);
        testModel.setLanguageName(LANGUAGE_NAME);
        testModel.setLanguage(LANGUAGE);
        testModel.setName(NAME);
        testModel.setProfileDefaultYn(PROFILE_DEFAULT_YN);
        testModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        testModel.setKey(KEY);
        testModel.setToName(TO_NAME);
        testModel.setFromKey(FROM_KEY);
        testModel.setProfileKey(PROFILE_KEY);
        testModel.setActiveRuleCount(ACTIVE_RULE_COUNT);
        testModel.setActiveDeprecatedRuleCount(ACTIVE_DEPRECATED_RULE_COUNT);

        resultModel.setId(ID);
        resultModel.setSonarKey(SONAR_KEY);
        resultModel.setLanguageName(LANGUAGE_NAME);
        resultModel.setLanguage(LANGUAGE);
        resultModel.setName(NAME);
        resultModel.setProfileDefaultYn(PROFILE_DEFAULT_YN);

        resultModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        resultModel.setKey(KEY);
        resultModel.setProfileKey(PROFILE_KEY);
        resultModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        resultModel.setActiveDeprecatedRuleCount(ACTIVE_DEPRECATED_RULE_COUNT);
        resultModel.setActiveRuleCount(ACTIVE_RULE_COUNT);

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
     *  QualityProfile 목록 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getQualityProfileList_Valid_Return() throws Exception{

        String serviceInstancesId = "09f060c6-ef13-464b-b0c5-d23f863c4960";
        List resultList = new ArrayList();

        when(commonService.sendForm(Constants.TARGET_COMMON_API, "/qualityProfile/qualityProfileList?serviceInstancesId="+serviceInstancesId, HttpMethod.GET, null, List.class)).thenReturn(resultList);

        qualityProfileService.getQualityProfileList(serviceInstancesId);

    }



    /**
     *  QualityProfile 복제
     *
     * @throws Exception the exception
     */
    @Test
    public void qualityProfileCopy_Valid_Return() throws Exception{
        ResponseEntity responseEntity = new ResponseEntity<Map>(HttpStatus.OK);
        when(restTemplate.exchange(Matchers.anyString(), any(HttpMethod.class), Matchers.<HttpEntity<?>>any(), Matchers.<Class<QualityGate>>any())).thenReturn(responseEntity);
        when(commonService.sendForm(anyString(),anyString(),any(HttpMethod.class),any(QualityGate.class),any())).thenReturn(testModel);
        when(commonService.sendForm(Matchers.matches("http://localhost:8081"),anyString(),any(HttpMethod.class),any(Object.class),any())).thenReturn(resultModel);


//
//        when(commonService.sendForm(Constants.TARGET_INSPECTION_API , "/api/qualityprofiles/copy",HttpMethod.POST, testModel, QualityProfile.class)).thenReturn(resultModel);
//        when(commonService.sendForm(Constants.TARGET_COMMON_API , "/qualityProfile/qualityProfileCopy",HttpMethod.POST, resultModel, QualityProfile.class)).thenReturn(resultModel);
//        when(qualityProfileService.qualityProfileCopy(testModel)).thenReturn(resultModel);

        QualityProfile result  = qualityProfileService.qualityProfileCopy(testModel);
        assertThat(result).isNotNull();
        assertEquals(resultModel.getId(), result.getId());

    }




    /**
     *  QualityProfile 생성
     *
     * @throws Exception the exception
     */
    @Test
    public void createQualityProfile_Valid_Return() throws Exception{


        QualityProfile testModel = new QualityProfile();
        QualityProfile resultModel = new QualityProfile();
        ObjectMapper mapper = new ObjectMapper();
//        JsonNode jsonModel = mapper.readTree()

        testModel.setLanguage("java");
        testModel.setName("profile-create");
        testModel.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setProfileDefaultYn("N");


//        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/create", HttpMethod.POST, testModel, JsonNode.class)).thenReturn(resultModel);

    }

    /**
     *  QualityProfile 삭제
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteQualityProfile_Valid_Return() throws Exception{

        QualityProfile testModel = new QualityProfile();


        testModel.setId(104);
        testModel.setProfileKey("java-copy-egov-profile-61540");
        testModel.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setProfileDefaultYn("N");

        String s​tate = "SUSESS";

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API , "/api/qualityprofiles/delete",HttpMethod.POST, testModel, null)).thenReturn(null);
        when(commonService.sendForm(Constants.TARGET_COMMON_API , "/qualityProfile/qualityProfileDelete",HttpMethod.DELETE, testModel, String.class)).thenReturn(s​tate);
        when(commonService.sendForm(Constants.TARGET_COMMON_API,"/project/qualityProfileDelete",HttpMethod.PUT,testModel,String.class)).thenReturn(s​tate);

        QualityProfile resultModel = qualityProfileService.deleteQualityProfile(testModel);

        assertThat(resultModel).isNotNull();
        assertEquals(Constants.RESULT_STATUS_SUCCESS, resultModel.getResultStatus());

        qualityProfileService.deleteQualityProfile(testModel);

    }


    /**
     *  QualityProfile 수정
     *
     * @throws Exception the exception
     */
    @Test
    public void updateQualityProfile_Valid_Return() throws Exception{

        QualityProfile testModel = new QualityProfile();
        QualityProfile resultModel = new QualityProfile();

        testModel.setId(104);
        testModel.setName("update-profile");
        testModel.setKey("java-copy-egov-profile-61540");
        testModel.setProfileDefaultYn("N");

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API , "/api/qualityprofiles/rename",HttpMethod.POST, testModel, null)).thenReturn(null);
        when(commonService.sendForm(Constants.TARGET_COMMON_API , "/qualityProfile/qualityProfileUpdate",HttpMethod.PUT, testModel, QualityProfile.class)).thenReturn(resultModel);

//        QualityProfile result = qualityProfileService.updateQualityProfile(testModel);

//        assertThat(result).isNotNull();
//        assertEquals(Constants.RESULT_STATUS_SUCCESS, result.getResultStatus());
        qualityProfileService.updateQualityProfile(testModel);
    }

    /**
     *  QualityProfile language 목록
     *
     * @throws Exception the exception
     */
    @Test
    public void qualityProfileLangList_Valid_Return() throws Exception{

        QualityProfile resultModel = new QualityProfile();
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API , "/api/languages/list",HttpMethod.GET, null, QualityProfile.class)).thenReturn(resultModel);

        qualityProfileService.qualityProfileLangList();
    }


    /**
     *  QualityProfile codingRules 개수
     *
     * @throws Exception the exception
     */
    @Test
    public void getCodingRulesList_Valid_Return() throws Exception{

        QualityProfile testModel = new QualityProfile();
        List resultList = new ArrayList();

        testModel.setLanguage("java");
        testModel.setName("profile-create");

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API,"/api/profiles?language="+testModel.getLanguage()+"&name="+testModel.getName(),HttpMethod.GET,null,List.class)).thenReturn(resultList);

        qualityProfileService.getCodingRulesList(testModel);
    }

    /**
     *  QualityProfile id로 검색
     *
     * @throws Exception the exception
     */
    @Test
    public void getQualityProfile_Valid_Return() throws Exception{
        long id = 104;
        QualityProfile testModel = new QualityProfile();
        QualityProfile resultModel = new QualityProfile();

        when(commonService.sendForm(Constants.TARGET_COMMON_API,"/qualityProfile/getQualityProfile?id="+id,HttpMethod.GET,null,QualityProfile.class)).thenReturn(resultModel);

        qualityProfileService.getQualityProfile(id);
    }
}
