package paasta.delivery.pipeline.inspection.api.codingRules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Coding rules controller.
 */
@RestController
@RequestMapping(value="/codingrules")
public class CodingRulesController {
    private final CodingRulesService codingRulesService;

    /**
     * Instantiates a new Coding rules controller.
     *
     * @param codingRulesService the coding rules service
     */
    @Autowired
    CodingRulesController(CodingRulesService codingRulesService) {
        this.codingRulesService = codingRulesService;
    }


    /**
     * Gets coding rules.
     * RequestParam(value = "qprofile", required = true)
     * RequestParam(value = "languages", required = false)
     * RequestParam(value = "facets", required = true)
     *
     * @param codingRules the coding rules
     * @return the coding rules
     */
    @GetMapping
    public CodingRules getCodingRules(@ModelAttribute CodingRules codingRules) {
        return codingRulesService.getCodingRules(codingRules);
    }


    //TODO ---------------------------------------------

    /**
     * CodingRules 검색 조건
     *
     * @param
     * @return List map
     */
    @RequestMapping(value="/condition", method = RequestMethod.GET)
    public Map getCodingRulesCondition(){
        return codingRulesService.getCodingRulesCondition();
    }


    /**
     * Get coding rules deteil map.
     *
     * @param codingRules the coding rules
     * @return the map
     */
    @RequestMapping(value = "/codingRulesDeteil" , method = RequestMethod.GET)
    public Map getCodingRulesDeteil(@ModelAttribute CodingRules codingRules){
        return codingRulesService.getCodingRulesDeteil(codingRules);
    }

    /**
     * CodingRules 프로파일 추가
     *
     * @param codingRules the coding rules
     * @return coding rules
     */
    @RequestMapping(value = "/codingRulesProfileAdd", method = RequestMethod.POST)
    public CodingRules createCodingRulesProfile(@RequestBody CodingRules codingRules){

        return codingRulesService.createCodingRulesProfile(codingRules);
    }

    /**
     * CodingRules 프로파일 제거
     *
     * @param codingRules the coding rules
     * @return coding rules
     */
    @RequestMapping(value = "/codingRulesProfileDelete", method = RequestMethod.POST)
    public CodingRules deleteCodingRulesProfile(@RequestBody CodingRules codingRules){
        return codingRulesService.deleteCodingRulesProfile(codingRules);
    }

    /**
     * CodingRules 프로파일 변경
     *
     * @param codingRules the coding rules
     * @return coding rules
     */
    @RequestMapping(value = "/codingRulesProfileUpdate" , method = RequestMethod.POST)
    public CodingRules updateCodingRulesProfile(@RequestBody CodingRules codingRules){
        return codingRulesService.updateCodingRulesProfile(codingRules);
    }

}