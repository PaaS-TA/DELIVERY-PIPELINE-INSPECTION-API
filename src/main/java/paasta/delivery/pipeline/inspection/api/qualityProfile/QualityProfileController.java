package paasta.delivery.pipeline.inspection.api.qualityProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Quality profile controller.
 */
@RestController
@RequestMapping(value = "/qualityProfile")
public class QualityProfileController {

    @Autowired
    private QualityProfileService qualityProfileService;

    /**
     * Get quality profile list .
     * <p>
     * RequestParam(value = "serviceInstanceId", required = true)
     * RequestParam(value = "language", required = false)
     *
     * @param qualityProfile the quality profile
     * @return the list
     */
    @GetMapping(value = "/qualityProfileList")
    public List getQualityProfileList(@ModelAttribute QualityProfile qualityProfile){
        return qualityProfileService.getQualityProfileList(qualityProfile);
    }

    /**
     * Get quality profile languages list.
     *
     * @return the list
     */
    @GetMapping(value = "/qualityProfileLanguages")
    public List getQualityProfileLanguages(){

        return qualityProfileService.getqualityProfileLanguages();
    }

    /**
     * Create quality profile quality profile.
     * <p>
     * RequestParam(value = "name", required = true)
     * RequestParam(value = "language", required = true)
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    @PostMapping(value = "/qualityProfileCreate")
    public QualityProfile CreateQualityProfile(@RequestBody QualityProfile qualityProfile){

        return qualityProfileService.createQualityProfile(qualityProfile);
    }

    /**
     * Gets projects.
     * <p>
     * RequestParam(value = "key", required = true)
     * RequestParam(value = "selected", required = false)
     *
     * @param qualityProfile the quality profile
     * @return the projects
     */
    @GetMapping(value = "/projects")
    public QualityProfile getProjects(@ModelAttribute QualityProfile qualityProfile) {

        return qualityProfileService.getProjects(qualityProfile);
    }

    /**
     * Copy quality profile quality profile.
     * <p>
     * RequestParam(value = "fromKey", required = true)
     * RequestParam(value = "toName", required = true)
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    @PostMapping(value = "/qualityProfileCopy")
    public QualityProfile copyQualityProfile(@RequestBody QualityProfile qualityProfile) {

        return qualityProfileService.copyQualityProfile(qualityProfile);
    }

    /**
     * Update quality profile quality profile.
     * <p>
     * RequestParam(value = "key", required = true)
     * RequestParam(value = "name", required = true)
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    @PostMapping(value = "/qualityProfileUpdate")
    public QualityProfile updateQualityProfile(@RequestBody QualityProfile qualityProfile) {

        return qualityProfileService.updateQualityProfile(qualityProfile);
    }

    /**
     * Delete quality profile quality profile.
     * <p>
     * RequestParam(value = "profileKey", required = true) OR
     * ( RequestParam(value = "profileName", required = true)
     * + RequestParam(value = "language", required = true) )
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    @PostMapping(value = "/qualityProfileDelete")
    public QualityProfile deleteQualityProfile(@RequestBody QualityProfile qualityProfile) {

        return qualityProfileService.deleteQualityProfile(qualityProfile);
    }

    /**
     * Active rule quality profile.
     * <p>
     * RequestParam(value = "profile_key", required = true)
     * RequestParam(value = "rule_key", required = true)
     * RequestParam(value = "severity", required = true)
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    @PostMapping(value = "/activateRule")
    public QualityProfile activateRule(@RequestBody QualityProfile qualityProfile) {
        return qualityProfileService.activateRule(qualityProfile);
    }

    /**
     * Active rule quality profile.
     * <p>
     * RequestParam(value = "profile_key", required = true)
     * RequestParam(value = "rule_key", required = true)
     *
     * @param qualityProfile the quality profile
     * @return the quality profile
     */
    @PostMapping(value = "/deactivateRule")
    public QualityProfile deactivateRule(@RequestBody QualityProfile qualityProfile) {
        return qualityProfileService.deactivateRule(qualityProfile);
    }
}
