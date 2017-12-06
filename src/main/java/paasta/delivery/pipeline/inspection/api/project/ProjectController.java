package paasta.delivery.pipeline.inspection.api.project;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

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

    private final Logger LOGGER = getLogger(getClass());

    private final ProjectService projectService;

    /**
     * Instantiates a new Project controller.
     *
     * @param projectService the project service
     */
    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * getProjectList
     *
     * @param project the project
     * @return List projects list
     */
    @RequestMapping(value = "/projectList", method = RequestMethod.GET)
    public List getProjectsList(Project project) {
        return projectService.getProjects(project);
    }

    /**
     * project create
     *
     * @param project the project
     * @return project project
     */
    @RequestMapping(value = "/projectsCreate", method = RequestMethod.POST)
    @ResponseBody
    public Project createProjects(@RequestBody Project project) {
        // TODO
//        return projectService.createProjects(project);
        return projectService.setCreateProject(project);
    }

    /**
     * project delete
     *
     * @param project the project
     * @return project project
     */
    @RequestMapping(value = "/projectsDelete", method = RequestMethod.POST)
    public Project deleteProjects(@RequestBody Project project) {
        // TODO
//        return projectService.deleteProjects(project);
        return projectService.setDeleteProject(project);
    }

    /**
     * project update
     *
     * @param project the project
     * @return project project
     */
    @RequestMapping(value = "/projectsUpdate", method = RequestMethod.POST)
    public Project updateProjects(@RequestBody Project project) {
        // TODO
//        return projectService.updateProjects(project);
        return projectService.setUpdateProject(project);
    }

    /**
     * QualityGate project 연결
     *
     * @param project the project
     * @return project
     */
    @RequestMapping(value = "/qualityGateProjectLiked", method = RequestMethod.POST)
    @ResponseBody
    public Project qualityGateProjectLiked(@RequestBody Project project) {
        return projectService.qualityGateProjectLiked(project);
    }

    /**
     * QualityProfile project 연결
     *
     * @param project the project
     * @return project
     */
    @RequestMapping(value = "/qualityProfileProjectLinked", method = RequestMethod.POST)
    public Project qualityProfileProjectLinked(@RequestBody Project project) {
        return projectService.qualityProfileProjectLinked(project);
    }

    /////////////////////////////////////

    /**
     * getProject sonarKey(uuid)
     *
     * @param project the project
     * @return project project sonar key
     */
    @RequestMapping(value = "/getProjectSonarKey", method = RequestMethod.GET)
    public List getProjectSonarKey(Project project) {
        return projectService.getProjectSonarKey(project);
    }

    /**
     * quality Management
     *
     * @param project the project
     * @return project
     */
    @RequestMapping(value = "/qualityManagementList", method = RequestMethod.GET)
    public Project qualityManagementList(Project project) {
        return projectService.qualityManagementList(project);
    }

    /**
     * quality Coverage
     *
     * @param project the project
     * @return list
     */
    @RequestMapping(value = "/qualityCoverageList", method = RequestMethod.GET)
    public List<Project> qualityCoverageList(@ModelAttribute Project project) {
        return projectService.qualityCoverageList(project);
    }

    /**
     * tests source
     *
     * @param project the project
     * @return List list
     */
    @RequestMapping(value = "/testsSource", method = RequestMethod.GET)
    public List testsSourceList(@ModelAttribute Project project) {
        return projectService.testsSourceList(project);
    }

    /**
     * tests source detail
     *
     * @param project the project
     * @return Project project
     */
    @RequestMapping(value = "/testsSourceShow", method = RequestMethod.GET)
    public Project testsSourceShow(@ModelAttribute Project project) {
        return projectService.testsSourceShow(project);
    }

    /**
     * getProjectKey
     *
     * @param project the project
     * @return Project project key
     */
    @RequestMapping(value = "/projectKey", method = RequestMethod.POST)
    public Project getProjectKey(@RequestBody Project project) {
        return projectService.getProjectKey(project);
    }


    /**
     * Set update project.
     * Project Link / UnLink
     *
     * RequestParam(value = "id", required = true)
     * RequestParam(value = "qualityProfileKey OR NULL", required = true)
     * RequestParam(value = "qualityGateId OR NULL", required = true)
     * RequestParam(value = "linkOperationType", required = true)
     * RequestParam(value = "defaultQualityProfileKey", required = false)
     * RequestParam(value = "defaultQualityGateId", required = false)
     *
     * @param project the project
     * @return the update project
     */
    @PostMapping(value = "/setUpdateProject")
    public Project setUpdateProject(@RequestBody Project project) {

        return projectService.setUpdateProject(project, project.isLinked() ? ProjectService.LinkOperationType.LINK : ProjectService.LinkOperationType.UNLINK);
    }

}
