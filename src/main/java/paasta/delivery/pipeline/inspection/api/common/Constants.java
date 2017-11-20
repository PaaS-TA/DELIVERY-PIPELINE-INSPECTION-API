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
    public static final String TARGET_INSPECTION_API = "inspectionApi";
    public static final String TARGET_COMMON_API = "commonApi";

    // [API] qualityprofiles
    public static final String API_QUALITYPROFILES_SEARCH = "/api/qualityprofiles/search";
    public static final String API_QUALITYPROFILES_PROJECTS = "/api/qualityprofiles/projects";

    public static final String API_LANGUAGES_LIST = "/api/languages/list";
    public static final String API_QUALITYPROFILES_CREATE = "/api/qualityprofiles/create";
    public static final String API_RULES_SEARCH = "/api/rules/search";

    public static final String KEY_PROFILES = "profiles";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_LANGUAGES = "languages";

    Constants() {}

}
