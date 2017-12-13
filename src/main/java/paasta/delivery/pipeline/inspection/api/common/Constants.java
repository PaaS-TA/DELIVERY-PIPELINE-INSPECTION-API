package paasta.delivery.pipeline.inspection.api.common;

/**
 * paastaDeliveryPipelineApi
 * paasta.delivery.pipeline.ui.common
 *
 * @author REX
 * @version 1.0
 * @since 5 /10/2017
 */
public class Constants {

    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    public static final String RESULT_STATUS_FAIL = "FAIL";

    // [API] qualityprofiles
    public static final String API_QUALITY_PROFILES_SEARCH = "/api/qualityprofiles/search";
    public static final String API_QUALITY_PROFILES_PROJECTS = "/api/qualityprofiles/projects";
    public static final String API_QUALITY_PROFILES_CREATE = "/api/qualityprofiles/create";
    public static final String API_QUALITY_PROFILES_COPY = "/api/qualityprofiles/copy";
    public static final String API_QUALITY_PROFILES_RENAME = "/api/qualityprofiles/rename";
    public static final String API_QUALITY_PROFILES_DELETE = "/api/qualityprofiles/delete";

    public static final String API_LANGUAGES_LIST = "/api/languages/list";

    public static final String API_RULES_SEARCH = "/api/rules/search";
    public static final String API_RULES_SHOW = "/api/rules/show";

    public static final String KEY_PROFILE = "profile";
    public static final String KEY_LANGUAGES = "languages";

    public static final String API_PROJECTS_CREATE = "/api/projects/create";
    public static final String API_QUALITY_PROFILES_ADD_PROJECT = "/api/qualityprofiles/add_project";
    public static final String API_QUALITY_PROFILES_REMOVE_PROJECT = "/api/qualityprofiles/remove_project";
    public static final String API_QUALITY_GATES_SELECT = "/api/qualitygates/select";
    public static final String API_QUALITY_GATES_DESELECT = "/api/qualitygates/deselect";

    private static final String ADMIN_STRING = "admin";
    public static final String INSPECTION_SERVER_ADMIN_USER_NAME = ADMIN_STRING;
    public static final String INSPECTION_SERVER_ADMIN_SECRET = ADMIN_STRING;

    Constants() {}

}
