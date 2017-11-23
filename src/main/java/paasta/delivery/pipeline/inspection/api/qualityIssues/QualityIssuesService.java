package paasta.delivery.pipeline.inspection.api.qualityIssues;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.project.Project;
import paasta.delivery.pipeline.inspection.api.project.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by kim on 2017-08-10.
 */
@Service
public class QualityIssuesService {

    private CommonService commonService;

    private ProjectService projectService;

    private final Logger LOGGER = getLogger(getClass());
    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;


    QualityIssuesService(CommonService commonService, ProjectService projectService) {
        this.commonService = commonService;
        this.projectService = projectService;
    }

    public QualityIssues qualityIssuesList(QualityIssues qualityIssues) {
        String url = "";
        //ps - 페이지 사이즈 , serverities - 이슈 수준 , stateuses - 상태 , resolved = 미해결 ,
        Project projectParam = new Project();
        projectParam.setServiceInstancesId(qualityIssues.getServiceInstancesId());
        if (qualityIssues.getComponentKeys().equals("")) {
            List<Map> projectList = projectService.getProjects(projectParam);
            String componentKey = "";
            for (Map project : projectList) {
                if (componentKey.length() == 0) {
                    componentKey += project.get("projectKey").toString();
                } else {
                    componentKey += "," + project.get("projectKey").toString();
                }

            }
            qualityIssues.setComponentKeys(componentKey);
        }

        if (qualityIssues.getComponentKeys() == null || qualityIssues.getComponentKeys().equalsIgnoreCase("") || qualityIssues.getComponentKeys().length() == 0) {
            return new QualityIssues();
        }


        if (qualityIssues.getResolutions().equals("UNRESOLVED")) {
            url = "/api/issues/search?additionalFields=_all&ps=" + qualityIssues.getPs() + "&severities=" + qualityIssues.getSeverities() + "&statuses=" + qualityIssues.getStatuses() +
                    "&resolved=false&componentKeys=" + qualityIssues.getComponentKeys();
        } else {
            url = "/api/issues/search?additionalFields=_all&ps=" + qualityIssues.getPs() + "&severities=" + qualityIssues.getSeverities() + "&statuses=" + qualityIssues.getStatuses() +
                    "&componentKeys=" + qualityIssues.getComponentKeys() + "&resolutions=" + qualityIssues.getResolutions();
        }
        QualityIssues data = commonService.sendForm(inspectionServerUrl, url, HttpMethod.GET, null, QualityIssues.class);
        return setProjectViewName(projectParam, data);
    }


    /*
    * 사용안함 --- 추후 정리 예정
     */
    public List<QualityIssues> getIssuesConditionList(QualityIssues qualityIssues) {
        String projectId = "";

        List<QualityIssues> list = new ArrayList();


        int num = 0;


        Project projectParam = new Project();
        projectParam.setServiceInstancesId(qualityIssues.getServiceInstancesId());


        //project check시
        if (qualityIssues.getComponentKeys() != null && !qualityIssues.getComponentKeys().equals("")) {
            projectId = qualityIssues.getComponentKeys();
        } else {
            List<Map> projects = projectService.getProjects(projectParam);
            //DB에서 프로젝트 키값 바인딩
            if (projects.size() > 0) {
                for (Map project : projects) {
                    if (projectId.length() == 0) {
                        projectId += project.get("projectId");
                    } else {
                        projectId += "," + project.get("projectId");
                    }

                }
            }

        }

        QualityIssues param = commonService.sendForm(inspectionServerUrl, "/api/issues/search?componentKeys=" + projectId, HttpMethod.GET, null, QualityIssues.class);


        if (!param.getTotal().equals("0") && param.getTotal() != null) {
            num = (Integer.parseInt(param.getTotal()) / 500) + 1;
        }


        for (int i = 1; i <= num; i++) {
            list.add(commonService.sendForm(inspectionServerUrl, "/api/issues/search?additionalFields=_all&ps=500&pageIndex=" + i + "&componentKeys=" + projectId, HttpMethod.GET, null, QualityIssues.class));
        }

        return list;
    }


    public List getQualityIssuesDetail(QualityIssues qualityIssues) {
        List list = new ArrayList();
        list = commonService.sendForm(inspectionServerUrl, "/api/resources?metrics=lines,violations,coverage_line_hits_data,coverage&resource=" + qualityIssues.getFileKey(), HttpMethod.GET, null, List.class);
        list.add(commonService.sendForm(inspectionServerUrl, "/api/sources/show?key=" + qualityIssues.getFileKey(), HttpMethod.GET, null, QualityIssues.class));
        list.add(commonService.sendForm(inspectionServerUrl, "/api/sources/scm?key=" + qualityIssues.getFileKey(), HttpMethod.GET, null, QualityIssues.class));
        QualityIssues  data  = commonService.sendForm(inspectionServerUrl, "/api/issues/search?additionalFields=_all&resolved=false&componentKeys=" + qualityIssues.getFileKey(), HttpMethod.GET, null, QualityIssues.class);
        Project param = new Project();
        param.setServiceInstancesId(qualityIssues.getServiceInstancesId());
        list.add(setProjectViewName(param,data));
        return list;
    }


    public Object setSeverity(QualityIssues qualityIssues) {
        return commonService.sendForm(inspectionServerUrl, "/api/issues/set_severity", HttpMethod.POST, qualityIssues, Object.class);
    }


    private QualityIssues setProjectViewName(Project param, QualityIssues data) {
        List<Map> projects = projectService.getProjects(param);
        List<Map> components = data.getComponents();
        List<Map> pcomponents = new ArrayList<>();
        for (Map component : components) {
            for (Map project: projects) {
                if(component.get("projectId") != null) {
                    if (component.get("projectId").toString().equalsIgnoreCase(project.get("projectId").toString())) {
                        component.put("projectViewName", project.get("projectViewName").toString());
                    }
                }
            }
            pcomponents.add(component);
        }
        data.setComponents(pcomponents);
        return data;
    }

}
