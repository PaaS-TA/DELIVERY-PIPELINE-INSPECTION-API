package paasta.delivery.pipeline.inspection.api.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * paastaDeliveryPipelineApi
 * paasta.delivery.pipeline.inspection.project
 *
 * @author kimdojun
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService) {this.projectService = projectService;}


    @RequestMapping(method = RequestMethod.GET)
    public Map getProjectList() throws IOException {
        // TODO :: MAY NOT BE USE
        return projectService.getProjectList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }


    @RequestMapping(value="/projectList" , method = RequestMethod.POST)
    public List getProjectsList(@RequestBody Project project){
        return projectService.getProjecstList(project);
    }


    /**
     *  project create
     *
     * @param  project
     * @return project
     */
    @RequestMapping(value = "/projectsCreate", method = RequestMethod.POST)
    public Project createProjects(@RequestBody Project project){
        return projectService.createProjects(project);
    }

    /**
     *  project delete
     *
     * @param  project
     * @return project
     */
    @RequestMapping(value = "/projectsDelete", method = RequestMethod.POST)
    public Project deleteProjects(@RequestBody Project project){
        return projectService.deleteProjects(project);
    }

    /**
     *  project update
     *
     * @param  project
     * @return project
     */
    @RequestMapping(value = "/projectsUpdate", method = RequestMethod.POST)
    public Project updateProjects(@RequestBody Project project){
        return projectService.updateProjects(project);
    }

    /**
     *  QualityGate project 연결
     *
     * @param project
     * @return
     */
    @RequestMapping(value="/qualityGateProjectLiked", method = RequestMethod.POST)
    public Project qualityGateProjectLiked(@RequestBody Project project){
        return projectService.qualityGateProjectLiked(project);
    }

    /**
     *  QualityProfile project 연결
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/qualityProfileProjectLinked" , method = RequestMethod.POST)
    public Project qualityProfileProjectLinked(@RequestBody Project project){
        return projectService.qualityProfileProjectLinked(project);
    }

    /////////////////////////////////////

    /**
     *  getProject sonarKey(uuid)
     *
     * @param
     * @return project
     */
    @RequestMapping(value = "/getProjectSonarKey", method = RequestMethod.GET)
    public List getProjectSonarKey(Project project){
        return projectService.getProjectSonarKey(project);
    }

    /**
     *  quality Management
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/qualityManagementList" , method = RequestMethod.GET)
    public Project qualityManagementList(Project project){
        return projectService.qualityManagementList(project);
    }

    /**
     *  quality Coverage
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/qualityCoverageList" , method = RequestMethod.GET)
    public List<Project> qualityCoverageList(@ModelAttribute Project project){
        return projectService.qualityCoverageList(project);
    }

    /**
     *  tests source
     *
     * @param  project
     * @return List
     */
    @RequestMapping(value = "/testsSource", method = RequestMethod.GET)
    public List testsSourceList(@ModelAttribute Project project){
        return projectService.testsSourceList(project);
    }

    /**
     *  tests source
     *
     * @param  project
     * @return List
     */
    @RequestMapping(value = "/testsSourceShow", method = RequestMethod.GET)
    public Project testsSourceShow(@ModelAttribute Project project){
        return projectService.testsSourceShow(project);
    }

}
