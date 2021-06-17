package worldofbooks.listingreportingsystem.dao.externalapi;

import worldofbooks.listingreportingsystem.util.HttpRequestUtil;

public class MarketplaceAPIDTO {
    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String MARKETPLACE_ENDPOINT_URL = "https://my.api.mockaroo.com/marketplace?key=" + MOCKAROO_API_KEY;
    private HttpRequestUtil httpRequestUtil;

    public MarketplaceAPIDTO(HttpRequestUtil newHttpRequestUtil) {
        this.setHttpRequestUtil(newHttpRequestUtil);
    }

    public String fetchData() {
        return this.httpRequestUtil.sendGetRequest(MARKETPLACE_ENDPOINT_URL);
    }

    public void setHttpRequestUtil(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }
}
