package worldofbooks.listingreportingsystem.dao.externalapi;

import worldofbooks.listingreportingsystem.util.HttpRequestUtil;

public class ListingStatusAPIDAO {
    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String LISTING_STATUS_ENDPOINT_URL = "https://my.api.mockaroo.com/listingStatus?key=" + MOCKAROO_API_KEY;
    private HttpRequestUtil httpRequestUtil;

    public ListingStatusAPIDAO(HttpRequestUtil newHttpRequestUtil) {
        this.setHttpRequestUtil(newHttpRequestUtil);
    }


    public String fetchData() {
        return this.httpRequestUtil.sendGetRequest(LISTING_STATUS_ENDPOINT_URL);
    }

    public void setHttpRequestUtil(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }
}
