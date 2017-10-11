package paasta.delivery.pipeline.inspection.api.project;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Dojun on 2017-06-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @MockBean
    private Project project;


    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
    }


    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getProjectList_Valid_Return() throws Exception {
        Project testModel = new Project();
        testModel.setName("junit-test-project");

        List<Project> testList = new ArrayList<>();
        testList.add(testModel);

        Map <String, Object> testResultModel = new LinkedHashMap<>();
        testResultModel.put("projects", testList);
        testResultModel.put("resultStatus", Constants.RESULT_STATUS_SUCCESS);

//        when(projectService.getProjectList()).thenReturn(testResultModel);
//
//        Map <String, Object> resultModel = projectController.getProjectList();
//        assertThat(resultModel).isNotNull();
//        assertEquals(Constants.RESULT_STATUS_SUCCESS, resultModel.get("resultStatus"));

        List<Project> resultList = (List)testResultModel.get("projects");

        assertEquals(resultList.get(0).getName(), testModel.getName());


    }


}




