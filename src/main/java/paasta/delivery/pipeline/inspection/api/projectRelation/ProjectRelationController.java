package paasta.delivery.pipeline.inspection.api.projectRelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dojun on 2017-07-06.
 */
@RestController
@RequestMapping(value = "/projectrelations")
public class ProjectRelationController {

    private final ProjectRelationService projectRelationService;

    @Autowired
    ProjectRelationController(ProjectRelationService projectRelationService) {this.projectRelationService = projectRelationService;}


    /**
     * Update projectRelation.
     *
     * @return updated qualityGate
     */
    @RequestMapping(value = "/{projectId}/qualitygate/{qualityGateId}", method = RequestMethod.PUT)
    public ProjectRelation updateProjectRelationQG(@PathVariable Long projectId, @PathVariable Long qualityGateId) {
        return projectRelationService.updateProjectRelationQG(projectId, qualityGateId);
    }
}
