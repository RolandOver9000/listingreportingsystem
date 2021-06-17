package worldofbooks.listingreportingsystem.dao.externalapi;

import worldofbooks.listingreportingsystem.util.HttpRequestUtil;

public class LocationAPIDAO {

    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String LOCATION_ENDPOINT_URL = "https://my.api.mockaroo.com/location?key=" + MOCKAROO_API_KEY;
    private HttpRequestUtil httpRequestUtil;

    public LocationAPIDAO(HttpRequestUtil newHttpRequestUtil) {
        this.setHttpRequestUtil(newHttpRequestUtil);
    }

    public String fetchData() {
        return this.httpRequestUtil.sendGetRequest(LOCATION_ENDPOINT_URL);
    }

    public void setHttpRequestUtil(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }
}
