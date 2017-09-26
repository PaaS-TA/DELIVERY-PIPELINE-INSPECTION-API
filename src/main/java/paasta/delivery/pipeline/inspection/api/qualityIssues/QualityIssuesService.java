package paasta.delivery.pipeline.inspection.api.qualityIssues;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import paasta.delivery.pipeline.inspection.api.common.CommonService;

import java.beans.Transient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kim on 2017-08-10.
 */
@Service
public class QualityIssuesService {

    private final CommonService commonService;

    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;


    QualityIssuesService(CommonService commonService){
        this.commonService = commonService;
    }

    public QualityIssues qualityIssuesList(QualityIssues qualityIssues){

        String url = "/api/issues/search?s=CREATION_DATE&ps="+qualityIssues.getPs()+"&severities="+qualityIssues.getSeverities()+"&statuses="+qualityIssues.getStatuses()+
                "&componentKeys="+qualityIssues.getComponentKeys()+"&resolutions="+qualityIssues.getResolutions()+qualityIssues.getResolved();

        return  commonService.sendForm(inspectionServerUrl, url, HttpMethod.GET,null, QualityIssues.class);
    }

    public List<QualityIssues> getProjectList(){
        QualityIssues param = new QualityIssues();
        int num = 0;
        param = commonService.sendForm(inspectionServerUrl, "/api/issues/search", HttpMethod.GET,null, QualityIssues.class);

        if(!param.getTotal().equals("0") && param.getTotal() != null){
            num = (Integer.parseInt(param.getTotal()) / 500) +1;
        }

        List<QualityIssues> list = new ArrayList();

        for(int i=1;i<=num;i++){
            list.add(commonService.sendForm(inspectionServerUrl, "/api/issues/search?s=CREATION_DATE&additionalFields=_all&ps=500&pageIndex="+i, HttpMethod.GET,null, QualityIssues.class));
        }

        return list;
    }


    public List getQualityIssuesDetail(QualityIssues qualityIssues){
        QualityIssues param = new QualityIssues();
        List list = new ArrayList();

        list = commonService.sendForm(inspectionServerUrl, "/api/resources?metrics=lines,violations,coverage_line_hits_data,coverage&resource="+qualityIssues.getFileKey(), HttpMethod.GET, null, List.class);

        list.add(commonService.sendForm(inspectionServerUrl, "/api/sources/show?key="+qualityIssues.getFileKey(), HttpMethod.GET,null, QualityIssues.class));

        list.add(commonService.sendForm(inspectionServerUrl, "/api/sources/scm?key="+qualityIssues.getFileKey(), HttpMethod.GET,null, QualityIssues.class));

        list.add(commonService.sendForm(inspectionServerUrl, "/api/issues/search?additionalFields=_all&resolved=false&componentKeys="+qualityIssues.getFileKey(), HttpMethod.GET,null, QualityIssues.class));

//        commonService.sendForm(commonApiUrl, "/issues/", HttpMethod.GET,null, QualityIssues.class
        return list;
    }
}
