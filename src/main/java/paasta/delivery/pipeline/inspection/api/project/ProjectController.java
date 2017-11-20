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

    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * getProjectList
     *
     * @param
     * @return List
     */
    @RequestMapping(value = "/projectList", method = RequestMethod.GET)
    public List getProjectsList(@RequestParam Long projectId, @RequestParam String projectName, @RequestParam String serviceInstancesId, @RequestParam String projectKey) {
        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName(projectName);
        project.setServiceInstancesId(serviceInstancesId);
        project.setProjectKey(projectKey);
        return projectService.getProjects(project);
    }

    /**
     * project create
     *
     * @param project
     * @return project
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
     * @param project
     * @return project
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
     * @param project
     * @return project
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
     * @param project
     * @return
     */
    @RequestMapping(value = "/qualityGateProjectLiked", method = RequestMethod.POST)
    @ResponseBody
    public Project qualityGateProjectLiked(@RequestBody Project project) {
        return projectService.qualityGateProjectLiked(project);
    }

    /**
     * QualityProfile project 연결
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/qualityProfileProjectLinked", method = RequestMethod.POST)
    public Project qualityProfileProjectLinked(@RequestBody Project project) {
        return projectService.qualityProfileProjectLinked(project);
    }

    /////////////////////////////////////

    /**
     * getProject sonarKey(uuid)
     *
     * @param
     * @return project
     */
    @RequestMapping(value = "/getProjectSonarKey", method = RequestMethod.GET)
    public List getProjectSonarKey(Project project) {
        return projectService.getProjectSonarKey(project);
    }

    /**
     * quality Management
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/qualityManagementList", method = RequestMethod.GET)
    public Project qualityManagementList(Project project) {
        return projectService.qualityManagementList(project);
    }

    /**
     * quality Coverage
     *
     * @param project
     * @return
     */
    @RequestMapping(value = "/qualityCoverageList", method = RequestMethod.GET)
    public List<Project> qualityCoverageList(@ModelAttribute Project project) {
        return projectService.qualityCoverageList(project);
    }

    /**
     * tests source
     *
     * @param project
     * @return List
     */
    @RequestMapping(value = "/testsSource", method = RequestMethod.GET)
    public List testsSourceList(@ModelAttribute Project project) {
        return projectService.testsSourceList(project);
    }

    /**
     * tests source detail
     *
     * @param project
     * @return Project
     */
    @RequestMapping(value = "/testsSourceShow", method = RequestMethod.GET)
    public Project testsSourceShow(@ModelAttribute Project project) {
        return projectService.testsSourceShow(project);
    }

    /**
     * getProjectKey
     *
     * @param project
     * @return Project
     */
    @RequestMapping(value = "/projectKey", method = RequestMethod.POST)
    public Project getProjectKey(@RequestBody Project project) {
        return projectService.getProjectKey(project);
    }

}
