package worldofbooks.listingreportingsystem.service;

public class ListingStatusService {

    private static final String LISTING_ENDPOINT_URL = "https://my.api.mockaroo.com/listingStatus?key=63304c70";
    private HttpRequestService httpRequestService;

    public ListingStatusService(HttpRequestService newHttpRequestService) {
        this.setHttpRequestService(newHttpRequestService);
    }

    public HttpRequestService getHttpRequestService() {
        return httpRequestService;
    }

    public void getAndConvertListingStatusData() {
        this.httpRequestService.sendGetRequest(LISTING_ENDPOINT_URL);
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }
}
