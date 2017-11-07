package paasta.delivery.pipeline.inspection.api.codingRules;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodingRulesService {
    private final CommonService commonService;
    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;
    @Autowired
    CodingRulesService(CommonService commonService) {this.commonService = commonService;}


    CodingRules getCodingRulesList(CodingRules codingRules) {
/*        CodingRules param = new CodingRules();

        param = commonService.sendForm(inspectionServerUrl, "/api/rules/search", HttpMethod.POST , codingRules ,CodingRules.class);

        param.getTotal();*/

        return commonService.sendForm(inspectionServerUrl, "/api/rules/search", HttpMethod.POST , codingRules ,CodingRules.class);

    }


    Map getCodingRulesCondition(){

        Object resultJson = commonService.sendForm(inspectionServerUrl,"/api/languages/list", HttpMethod.POST , null ,Object.class);
        Map <String, Object> objectHashMap = new HashMap<>();
        objectHashMap = (Map)resultJson;
        return objectHashMap;
    }

    Map getCodingRulesDeteil(CodingRules codingRules){
        Object resultJson = commonService.sendForm(inspectionServerUrl, "/api/rules/show?key="+codingRules.getKey()+"&actives="+codingRules.getActives(), HttpMethod.GET , null,Object.class);
        Map <String, Object> objectHashMap = new HashMap<>();
        objectHashMap = (Map)resultJson;
        return objectHashMap;

    }

    /**
     *  CodingRules 프로파일 추가
     *  rule_key : "squid:S2204",
     *  profile_key : "java-sonar-way-15680",
     *  severity : "INFO",
     *  reset: "true"
     *
     *
     *
     * @param
     * @return
     */
    public CodingRules createCodingRulesProfile(CodingRules codingRules){
        CodingRules result = new CodingRules();
        commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/activate_rule", HttpMethod.POST, codingRules, null);
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return result;

    }

    public CodingRules deleteCodingRulesProfile(CodingRules codingRules){
        CodingRules result = new CodingRules();
        commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/deactivate_rule", HttpMethod.POST, codingRules, null);
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return result;
    }

    public CodingRules updateCodingRulesProfile(CodingRules codingRules){
        CodingRules result = new CodingRules();
        commonService.sendForm(inspectionServerUrl, "/api/qualityprofiles/activate_rule", HttpMethod.POST, codingRules, null);
        result.setResultStatus(Constants.RESULT_STATUS_SUCCESS);
        return result;
    }

}

