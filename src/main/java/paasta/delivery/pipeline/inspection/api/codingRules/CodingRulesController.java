package paasta.delivery.pipeline.inspection.api.codingRules;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/codingrules")
public class CodingRulesController {
    private final CodingRulesService codingRulesService;

    @Autowired
    CodingRulesController(CodingRulesService codingRulesService) {
        this.codingRulesService = codingRulesService;
    }


    /**
     *  CodingRules 리스트
     *
     * @param
     * @return Map
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CodingRules getCodingRules(@RequestBody CodingRules codingRules) {
        return codingRulesService.getCodingRulesList(codingRules);
    }

    /**
     *  CodingRules 검색 조건
     *
     * @param
     * @return List
     */
    @RequestMapping(value="/condition", method = RequestMethod.GET)
    public Map getCodingRulesCondition(){
        return codingRulesService.getCodingRulesCondition();
    }



    @RequestMapping(value = "/codingRulesDeteil" , method = RequestMethod.GET)
    public Map getCodingRulesDeteil(@ModelAttribute CodingRules codingRules){
        return codingRulesService.getCodingRulesDeteil(codingRules);
    }

    /**
     *  CodingRules 프로파일 추가
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/codingRulesProfileAdd", method = RequestMethod.POST)
    public CodingRules createCodingRulesProfile(@RequestBody CodingRules codingRules){

        return codingRulesService.createCodingRulesProfile(codingRules);
    }

    /**
     *  CodingRules 프로파일 제거
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/codingRulesProfileDelete", method = RequestMethod.POST)
    public CodingRules deleteCodingRulesProfile(@RequestBody CodingRules codingRules){
        return codingRulesService.deleteCodingRulesProfile(codingRules);
    }

    /**
     *  CodingRules 프로파일 변경
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/codingRulesProfileUpdate" , method = RequestMethod.POST)
    public CodingRules updateCodingRulesProfile(@RequestBody CodingRules codingRules){
        return codingRulesService.updateCodingRulesProfile(codingRules);
    }

}