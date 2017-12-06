package paasta.delivery.pipeline.inspection.api.codingRules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Gets coding rule detail.
     * <p>
     * RequestParam(value = "key", required = true)
     * RequestParam(value = "actives", required = true)
     *
     * @param codingRules the coding rules
     * @return the coding rule detail
     */
    @GetMapping(value = "/codingRuleDetail")
    public CodingRules getCodingRuleDetail(@ModelAttribute CodingRules codingRules) {
        return codingRulesService.getCodingRuleDetail(codingRules);
    }

}