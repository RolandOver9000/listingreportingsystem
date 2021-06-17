package worldofbooks.listingreportingsystem.dao.externalapi;

import worldofbooks.listingreportingsystem.util.HttpRequestUtil;

public class ListingAPIDAO {
    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String LISTING_ENDPOINT_URL = "https://my.api.mockaroo.com/listing?key=" + MOCKAROO_API_KEY;
    private HttpRequestUtil httpRequestUtil;

    public ListingAPIDAO(HttpRequestUtil newHttpRequestUtil) {
        this.setHttpRequestUtil(newHttpRequestUtil);
    }

    public String fetchData() {
        return this.httpRequestUtil.sendGetRequest(LISTING_ENDPOINT_URL);
    }

    public void setHttpRequestUtil(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }
}
