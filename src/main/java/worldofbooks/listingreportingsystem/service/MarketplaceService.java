package worldofbooks.listingreportingsystem.service;

public class MarketplaceService {

    private static final String LISTING_ENDPOINT_URL = "https://my.api.mockaroo.com/marketplace?key=63304c70";
    private HttpRequestService httpRequestService;

    public MarketplaceService(HttpRequestService newHttpRequestService) {
        this.setHttpRequestService(newHttpRequestService);
    }

    public HttpRequestService getHttpRequestService() {
        return httpRequestService;
    }

    public void getAndConvertMarketplaceData() {
        this.httpRequestService.sendGetRequest(LISTING_ENDPOINT_URL);
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

}
