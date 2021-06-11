package worldofbooks.listingreportingsystem.service;

public class LocationService {

    private static final String LISTING_ENDPOINT_URL = "https://my.api.mockaroo.com/location?key=63304c70";
    private HttpRequestService httpRequestService;

    public LocationService(HttpRequestService newHttpRequestService) {
        this.setHttpRequestService(newHttpRequestService);
    }

    public void getAndConvertLocationData() {
        this.httpRequestService.sendGetRequest(LISTING_ENDPOINT_URL);
    }

    public HttpRequestService getHttpRequestService() {
        return httpRequestService;
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }
}
