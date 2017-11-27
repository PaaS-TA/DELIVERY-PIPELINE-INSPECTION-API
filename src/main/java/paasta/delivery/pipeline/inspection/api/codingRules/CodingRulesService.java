package paasta.delivery.pipeline.inspection.api.codingRules;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Coding rules Service.
 */
@Service
public class CodingRulesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodingRulesService.class);

    @Autowired
    private CommonService commonService;

    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;


    /**
     * Gets coding rules.
     *
     * @param codingRules the coding rules
     * @return the coding rules
     */
    public CodingRules getCodingRules(CodingRules codingRules) {

        // /api/rules/search?activation=true&f=name&facets=active_severities&ps=1&qprofile={profileKey}
        String reqUrl = commonService.makeQueryParam(Constants.API_RULES_SEARCH, codingRules);

        LOGGER.info("===[INSPECTION-API :: getCodingRules]=== reqUrl : {}", reqUrl);

        CodingRules data = commonService.sendForm(inspectionServerUrl, reqUrl, HttpMethod.GET, null, CodingRules.class);

        data.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return data;

    }

    /**
     * Gets coding rule detail.
     *
     * @param codingRules the coding rules
     * @return the coding rule detail
     */
    public CodingRules getCodingRuleDetail(CodingRules codingRules) {

        // /api/rules/show
        String reqUrl = commonService.makeQueryParam(Constants.API_RULES_SHOW, codingRules);

        CodingRules data = commonService.sendForm(inspectionServerUrl, reqUrl, HttpMethod.GET, null, CodingRules.class);
        data.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return data;

    }

}

