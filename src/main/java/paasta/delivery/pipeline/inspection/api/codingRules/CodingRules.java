package paasta.delivery.pipeline.inspection.api.codingRules;

/**
 * The type Coding rules.
 */
public class CodingRules {

    // --- common :: s
    private String key;
    // --- common :: e

    // --- search :: QualityProfile Detail Rule Info :: s
    private String qprofile;
    private int ps;
    private int p = 1;
    private String f;
    private Object facets;
    private boolean activation = true;
    private int total;
    // --- search :: QualityProfile Detail Rule Info :: e

    // --- create :: s
    private String custom_key;
    private String markdown_description;
    private String name;
    // --- create :: e

    // --- repositories :: s
    private String language;
    // --- repositories :: e

    // --- etc :: s
    private String actives;
    private String resultStatus;
    private String resultMessage;
    // --- etc :: e

    /**
     * Gets total.
     *
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(int total) {
        this.total = total;
    }


    /**
     * Sets key.
     *
     * @param key the key
     */
// --[getter/setter]-----------------------------------
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets qprofile.
     *
     * @return the qprofile
     */
    public String getQprofile() {
        return qprofile;
    }

    /**
     * Sets qprofile.
     *
     * @param qprofile the qprofile
     */
    public void setQprofile(String qprofile) {
        this.qprofile = qprofile;
    }

    /**
     * Sets ps.
     *
     * @param ps the ps
     */
    public void setPs(int ps) {
        this.ps = ps;
    }

    /**
     * Gets facets.
     *
     * @return the facets
     */
    public Object getFacets() {
        return facets;
    }

    /**
     * Sets facets.
     *
     * @param facets the facets
     */
    public void setFacets(Object facets) {
        this.facets = facets;
    }

    /**
     * Is activation boolean.
     *
     * @return the boolean
     */
    public boolean isActivation() {
        return activation;
    }

    /**
     * Sets activation.
     *
     * @param activation the activation
     */
    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    /**
     * Gets ps.
     *
     * @return the ps
     */
    public int getPs() {
        return ps;
    }

    /**
     * Gets f.
     *
     * @return the f
     */
    public String getF() {
        return f;
    }

    /**
     * Sets f.
     *
     * @param f the f
     */
    public void setF(String f) {
        this.f = f;
    }

    /**
     * Gets p.
     *
     * @return the p
     */
    public int getP() {
        return p;
    }

    /**
     * Gets result status.
     *
     * @return the result status
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * Sets result status.
     *
     * @param resultStatus the result status
     */
    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    /**
     * Gets result message.
     *
     * @return the result message
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Sets result message.
     *
     * @param resultMessage the result message
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    /**
     * Gets custom key.
     *
     * @return the custom key
     */
    public String getCustom_key() {
        return custom_key;
    }

    /**
     * Sets custom key.
     *
     * @param custom_key the custom key
     */
    public void setCustom_key(String custom_key) {
        this.custom_key = custom_key;
    }

    /**
     * Gets markdown description.
     *
     * @return the markdown description
     */
    public String getMarkdown_description() {
        return markdown_description;
    }

    /**
     * Sets markdown description.
     *
     * @param markdown_description the markdown description
     */
    public void setMarkdown_description(String markdown_description) {
        this.markdown_description = markdown_description;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Sets p.
     *
     * @param p the p
     */
    public void setP(int p) {
        this.p = p;
    }

    /**
     * Gets actives.
     *
     * @return the actives
     */
    public String getActives() {
        return actives;
    }

    /**
     * Sets actives.
     *
     * @param actives the actives
     */
    public void setActives(String actives) {
        this.actives = actives;
    }

    @Override
    public String toString() {
        return "CodingRules{" +
                "key='" + key + '\'' +
                ", qprofile='" + qprofile + '\'' +
                ", ps=" + ps +
                ", p=" + p +
                ", f='" + f + '\'' +
                ", facets=" + facets +
                ", activation=" + activation +
                ", total=" + total +
                ", custom_key='" + custom_key + '\'' +
                ", markdown_description='" + markdown_description + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", actives='" + actives + '\'' +
                ", resultStatus='" + resultStatus + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }


}
