package paasta.delivery.pipeline.inspection.api.qualityProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import paasta.delivery.pipeline.inspection.api.qualityGate.QualityGate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * paastaDeliveryPipelineApi
 * paasta.delivery.pipeline.inspection.api.qualityProfile
 *
 * @author kimdojun
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/qualityProfile")
public class QualityProfileController {

    private final QualityProfileService qualityProfileService;

    @Autowired
    QualityProfileController (QualityProfileService qualityProfileService) {this.qualityProfileService = qualityProfileService;}




    /**
     * Get specific qualityGate.
     *
     * @return the qualityGate
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QualityProfile getQualityProfile(@PathVariable Long id) {
        return qualityProfileService.getQualityProfile(id);
    }

    /**
     * Create qualityProfile
     *
     * @return created qualityProfile
     */
/*    @RequestMapping(method = RequestMethod.POST)
    public QualityProfile createQualityProfile(@RequestBody QualityProfile qualityProfile) {
        return qualityProfileService.createQualityProfile(qualityProfile);
    }*/

    /**
     * Delete qualityProfile.
     *
     * @return status
     */
//    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
//    public String deleteQualityProfile(@PathVariable Long id) throws IOException {
//        return qualityProfileService.deleteQualityProfile(id);
//    }



    ////////////////////////////////////////////////////////////////////

    /**
     * Get qualityProfile.
     *
     * @return the qualityProfile
     */
//    @RequestMapping(method = RequestMethod.GET)
//    public Map getQualityProfileList() {
//        // TODO :: MAY NOT BE USE
//        return qualityProfileService.getQualityProfileList();
//    }

    //시연후 로그인 정보 추가
    @RequestMapping(value = "/qualityProfileList" , method = RequestMethod.GET)
    public List getQualityProfileList(String serviceInstancesId){
        return qualityProfileService.getQualityProfileList(serviceInstancesId);
    }

    /**
     * qualityProfile 복제.
     *
     * @return
     */
    @RequestMapping(value = "/qualityProfileCopy", method = RequestMethod.POST)
    public QualityProfile qualityProfileCopy(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.qualityProfileCopy(qualityProfile);
    }

    /**
     * qualityProfile 삭제
     *
     * @return
     */
    @RequestMapping(value = "/qualityProfileDelete", method = RequestMethod.POST)
    public QualityProfile deleteQualityProfile(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.deleteQualityProfile(qualityProfile);
    }

    /**
     * qualityProfile 수정
     *
     * @return
     */
    @RequestMapping(value = "/qualityProfileUpdae", method = RequestMethod.POST)
    public QualityProfile updateQualityProfile(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.updateQualityProfile(qualityProfile);
    }

    /**
     * qualityProfile 언어리스트
     *
     * @return
     */
    @RequestMapping(value = "/qualityProfileLangList", method = RequestMethod.GET)
    public QualityProfile qualityProfileLangList(){
        return qualityProfileService.qualityProfileLangList();
    }

    /**
     * qualityProfile 생성
     *
     * @return
     */
    @RequestMapping(value = "/qualityProfileCreate", method = RequestMethod.POST)
    public QualityProfile createQualityProfile(@RequestBody QualityProfile qualityProfile) {
        return qualityProfileService.createQualityProfile(qualityProfile);
    }

    /**
     *  QualityProfile default setting
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/qualityProfileDefault",method = RequestMethod.POST)
    public QualityProfile defaultQualityProfile(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.defaultQualityProfile(qualityProfile);
    }

    /**
     *  QualityProfile codingRules
     *
     * @param qualityProfile
     * @return list
     */
    @RequestMapping(value = "/codingRulesList",method = RequestMethod.POST)
    public List getCodingRulesList(@RequestBody QualityProfile qualityProfile){
        return qualityProfileService.getCodingRulesList(qualityProfile);
    }


}
