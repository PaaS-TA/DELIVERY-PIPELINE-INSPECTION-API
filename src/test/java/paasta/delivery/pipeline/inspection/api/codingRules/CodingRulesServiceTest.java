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
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Created by kim on 2017-10-25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodingRulesServiceTest {

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

        CodingRules testModel = new CodingRules();
        CodingRules result = new CodingRules();

        testModel.setS("name");
        testModel.setSeverity("MAJOR");
        testModel.setLanguages("java");
        testModel.setPs("50");
        testModel.setServiceInstancesId("09f060c6-ef13-464b-b0c5-d23f863c4960");
        testModel.setQprofile("java-egov-qualityprofile-79840");
        testModel.setActivation("profileOn");
        testModel.setQ("e");


        when(codingRulesService.getCodingRulesList(testModel)).thenReturn(result);
//        assertThat(result).isNotNull();
    }

    /**
     *  CodingRules 조건 검색
     *
     * @throws Exception the exception
     */
    @Test
    public void getCodingRulesCondition_Valid_Return() throws Exception{

        Map <String, Object> mapResult = new HashMap<>();
        when(codingRulesService.getCodingRulesCondition()).thenReturn(mapResult);
    }

    /**
     *  CodingRules 상세 조회
     *
     * @throws Exception the exception
     */
    @Test
    public void getCodingRulesDeteil_Valid_Return() throws Exception{

        CodingRules testModel = new CodingRules();
        Map <String, Object> mapReturn = new HashMap<>();

        testModel.setKey("squid:S2204");
        testModel.setActives("false");

        when(codingRulesService.getCodingRulesDeteil(testModel)).thenReturn(mapReturn);
    }

    /**
     *  CodingRules 프로파일 추가
     *
     * @throws Exception the exception
     */
    @Test
    public void createCodingRulesProfile_Valid_Return() throws Exception{

        CodingRules testModel = new CodingRules();
        CodingRules resultModel = new CodingRules();

        testModel.setRule_key("squid:S2204");
        testModel.setProfile_key("java-egov-qualityprofile-79840");
        testModel.setSeverity("INFO");
        testModel.setReset(true);
//        when(commonService.sendForm(Constants.TARGET_INSPECTION_API, "/api/qualityprofiles/activate_rule", HttpMethod.POST, testModel, null)).thenReturn(result);
        when( codingRulesService.createCodingRulesProfile(testModel)).thenReturn(resultModel);
//        codingRulesService.createCodingRulesProfile(testModel);

    }

    /**
     *  CodingRules 프로파일 삭제
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteCodingRulesProfile_Valid_Return() throws Exception{
        CodingRules testModel = new CodingRules();
        CodingRules resultModel = new CodingRules();

        testModel.setRule_key("squid:S2204");
        testModel.setProfile_key("java-egov-qualityprofile-79840");

        when(codingRulesService.deleteCodingRulesProfile(testModel)).thenReturn(resultModel);
    }

    /**
     *  CodingRules 프로파일 변경
     *
     * @throws Exception the exception
     */
    @Test
    public void updateCodingRulesProfile_Valid_Return() throws Exception{
        CodingRules testModel = new CodingRules();
        CodingRules resultModel = new CodingRules();

        testModel.setRule_key("squid:S2204");
        testModel.setProfile_key("java-egov-qualityprofile-79840");
        testModel.setSeverity("INFO");
        testModel.setReset(true);

        when(codingRulesService.updateCodingRulesProfile(testModel)).thenReturn(resultModel);

    }







}
