package paasta.delivery.pipeline.inspection.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * paastaDeliveryPipelineApi
 * paasta.delivery.pipeline.api.common
 *
 * @author REX
 * @version 1.0
 * @since 5 /8/2017
 */
@Service
public class CommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private final RestTemplate restTemplate;

    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;
    /**
     * The Admin user name.
     */
    @Value("${inspection.server.admin.username}")
    public String adminUserName;
    /**
     * The Admin password.
     */
    @Value("${inspection.server.admin.password}")
    public String adminPassword;

    // COMMON API
    @Value("${commonApi.url}")
    private String commonApiUrl;

    /**
     * Instantiates a new Common service.
     *
     * @param restTemplate the rest template
     */
    @Autowired
    public CommonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * Send form t.
     *
     * @param <T>          the type parameter
     * @param baseUrl      the base url
     * @param reqUrl       the req url
     * @param httpMethod   the http method
     * @param bodyObject   the body object
     * @param responseType the response type
     * @return the t
     */
    public <T> T sendForm(String baseUrl, String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {
        String authorization = adminUserName + ":" + adminPassword;
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(AUTHORIZATION_HEADER_KEY, "Basic " + Base64Utils.encodeToString(authorization.getBytes(StandardCharsets.UTF_8)));

        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);

        LOGGER.info("<T> T send :: Request : {} {baseUrl} : {}, Content-Type: {}", httpMethod, baseUrl + reqUrl, reqHeaders.getContentType());

        ResponseEntity<T> resEntity = restTemplate.exchange(baseUrl + reqUrl, httpMethod, reqEntity, responseType);
        LOGGER.info("Response Status Code: {}", resEntity.getStatusCode());
        if (!resEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("Response Error : {} " + resEntity.getBody());
        }

        return resEntity.getBody();
    }

    /**
     * Make query param string.
     *
     * @param reqUrl      the req url
     * @param obj         the obj
     * @param exceptParam the except param
     * @return the string
     */
    public String makeQueryParam(String reqUrl, Object obj, String exceptParam) {
        ObjectMapper om = new ObjectMapper();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(reqUrl);
        Map<String, Object> codingRulesMap = om.convertValue(obj, Map.class);
        String exceptQuery = "";

        for (String key : codingRulesMap.keySet()) {

            if (codingRulesMap.get(key) != null && !key.equals(exceptParam)) {
                builder.queryParam(key, codingRulesMap.get(key));

            } else if (key.equals(exceptParam)) {
                exceptQuery += exceptParam + "="+codingRulesMap.get(key);
            }
        }

        String queryParam = builder.build().encode().toUriString();
        String result = StringUtils.isEmpty(queryParam) ? "?" + exceptQuery : queryParam + "&" + exceptQuery;

        return result;

    }

}