package paasta.delivery.pipeline.inspection.api.codingRules;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by kim on 2017-10-25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodingRulesServiceTest {

    private static final long ID = 1;
    private static final String NAME = "test-name";
    private static final String S = "test-seach-name";
    private static final String SEVERITY = "test-serverrity";
    private static final String SEVERITIES = "test-severities";
    private static final String LANGUAGES = "test-langguages";
    private static final String PS = "1";
    private static final String SERVICE_INSTANCES_ID  = "test-instances-id";
    private static final String QPROFILE = "test-profile";
    private static final String ACTIVATION = "testOn";
    private static final String Q = "E";
    private static final String KEY = "test-key";
    private static final String ACTIVES = "false";
    private static final String PROJECT_KEY = "test-key";
    private static final String RULE_KEY = "test-rule-key";
    private static final String PROFILE_KEY = "test-profile-key";
    private static final Boolean RESET = true;
    private static final String TOTAL = "50";

    private static CodingRules testModel = null;
    private static CodingRules resultModel = null;
//    private static  Map<String, Object> testMap = null;
    private static  Map<String, Object> resultMap = null;

    @Mock
    private CommonService commonService;




    @InjectMocks
    private CodingRulesService codingRulesService;

    /*
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        testModel = new CodingRules();
        testModel.setId(ID);
        testModel.setName(NAME);
        testModel.setS(S);
        testModel.setTotal(TOTAL);
        testModel.setSeverity(SEVERITY);
        testModel.setLanguages(LANGUAGES);
        testModel.setPs(PS);
        testModel.setServiceInstancesId(SERVICE_INSTANCES_ID);
        testModel.setQprofile(QPROFILE);
        testModel.setActivation(ACTIVATION);
        testModel.setQ(Q);
        testModel.setKey(KEY);
        testModel.setActives(ACTIVES);
        testModel.setRule_key(RULE_KEY);
        testModel.setProfile_key(PROJECT_KEY);
        testModel.setReset(RESET);
        testModel.setProfile_key(PROFILE_KEY);
        testModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        testModel.setResultMessage("SUCCESS");



        resultModel = new CodingRules();
        resultModel.setTotal(TOTAL);
        resultModel.setPs(PS);
        resultModel.setSeverity(SEVERITY);
        resultModel.setRules(null);
        resultModel.setResultStatus(Constants.RESULT_STATUS_SUCCESS);


/*
        testMap = new HashMap<>();
        testMap.put("key","test-key");
        testMap.put("name","test-name");
        testMap.put("htmlDesc","test-html");
        testMap.put("severity","test-severity");
        testMap.put("languages","java");
        testMap.put("rules","rules-test");
*/


        resultMap = new HashMap<>();
        resultMap.put("key","test-key");
        resultMap.put("name","test-name");
        resultMap.put("htmlDesc","test-html");
        resultMap.put("severity","test-severity");
        resultMap.put("rule","test-rule");
        resultMap.put("severity","test-severity");
        resultMap.put("status","test-status");
        resultMap.put("qProfile","test-qProfile");








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
     *  CodingRules 리스트
     *
     * @throws Exception the exception
     */
    @Test
    public void getCodingRulesList_Valid_Return() throws Exception{
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/rules/search", HttpMethod.POST , testModel ,CodingRules.class)).thenReturn(resultModel);
        resultModel = codingRulesService.getCodingRulesList(testModel);

    }

    /**
     *  CodingRules 조건 검색
     *
     * @throws Exception the exception
     */
    @Test
    public void getCodingRulesCondition_Valid_Return() throws Exception{
        when(commonService.sendForm(Constants.TARGET_INSPECTION_API,"/api/languages/list", HttpMethod.POST , null ,Object.class)).thenReturn(resultMap);

        resultMap =  codingRulesService.getCodingRulesCondition();
    }

    /**
     *  CodingRules 상세 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getCodingRulesDeteil_Valid_Return() throws Exception{

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/rules/show?key="+testModel.getKey()+"&actives="+testModel.getActives(), HttpMethod.GET , null,Object.class)).thenReturn(resultMap);
        resultMap = codingRulesService.getCodingRulesDeteil(testModel);
    }

    /**
     *  CodingRules 프로파일 추가
     *
     * @throws Exception the exception
     */
    @Test
    public void createCodingRulesProfile_Valid_Return() throws Exception{

        when( commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/activate_rule", HttpMethod.POST, testModel, null)).thenReturn(null);

        resultModel = codingRulesService.createCodingRulesProfile(testModel);

    }

    /**
     *  CodingRules 프로파일 삭제
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteCodingRulesProfile_Valid_Return() throws Exception{

        when( commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/deactivate_rule", HttpMethod.POST, testModel, null)).thenReturn(null);
        resultModel = codingRulesService.deleteCodingRulesProfile(testModel);

    }

    /**
     *  CodingRules 프로파일 변경
     *
     * @throws Exception the exception
     */
    @Test
    public void updateCodingRulesProfile_Valid_Return() throws Exception{

        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/activate_rule", HttpMethod.POST, testModel, null)).thenReturn(null);
        resultModel = codingRulesService.updateCodingRulesProfile(testModel);

    }







}
