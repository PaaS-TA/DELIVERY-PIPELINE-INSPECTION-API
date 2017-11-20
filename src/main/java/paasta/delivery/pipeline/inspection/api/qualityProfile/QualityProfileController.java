package paasta.delivery.pipeline.inspection.api.qualityProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import paasta.delivery.pipeline.inspection.api.project.Project;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGate;

import java.io.IOException;
import java.util.List;
import java.util.Map;


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
     *
     * @param serviceInstancesId the service instances id
     * @return the list
     */
    @GetMapping(value = "/qualityProfileList")
    public List getQualityProfileList(String serviceInstancesId){
        return qualityProfileService.getQualityProfileList(serviceInstancesId);
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
     *
     * @param qualityProfile the quality profile
     * @return the projects
     */
    @GetMapping(value = "/projects")
    public QualityProfile getProjects(@ModelAttribute QualityProfile qualityProfile) {

        return qualityProfileService.getProjects(qualityProfile);
    }


    //TODO --------------------------------
    /**
     * Get specific qualityGate.
     *
     * @param id the id
     * @return the qualityGate
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QualityProfile getQualityProfile(@PathVariable Long id) {
        return qualityProfileService.getQualityProfile(id);
    }


    /**
     * Delete qualityProfile.
     *
     * @return status
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public String deleteQualityProfile(@PathVariable Long id) throws IOException {
//        return qualityProfileService.deleteQualityProfile(id);
        return null;
    }



    ////////////////////////////////////////////////////////////////////


    /**
     * qualityProfile 복제.
     *
     * @param qualityProfile the quality profile
     * @return quality profile
     */
    @RequestMapping(value = "/qualityProfileCopy", method = RequestMethod.POST)
    public QualityProfile qualityProfileCopy(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.qualityProfileCopy(qualityProfile);
    }

    /**
     * qualityProfile 삭제
     *
     * @param qualityProfile the quality profile
     * @return quality profile
     */
    @RequestMapping(value = "/qualityProfileDelete", method = RequestMethod.POST)
    public QualityProfile deleteQualityProfile(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.deleteQualityProfile(qualityProfile);
    }

    /**
     * qualityProfile 수정
     *
     * @param qualityProfile the quality profile
     * @return quality profile
     */
    @RequestMapping(value = "/qualityProfileUpdae", method = RequestMethod.POST)
    public QualityProfile updateQualityProfile(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.updateQualityProfile(qualityProfile);
    }




}
