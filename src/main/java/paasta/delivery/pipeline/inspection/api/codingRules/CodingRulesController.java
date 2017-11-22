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

    @Autowired
    private CodingRulesService codingRulesService;

    /**
     * Gets coding rules.
     * ## Quality Profile Use
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
     * Get coding rules deteil map.
     *
     * @param codingRules the coding rules
     * @return the map
     */
    @RequestMapping(value = "/codingRulesDeteil" , method = RequestMethod.GET)
    public Map getCodingRulesDeteil(@ModelAttribute CodingRules codingRules){
        return codingRulesService.getCodingRulesDeteil(codingRules);
    }

}