package worldofbooks.listingreportingsystem.service;

import java.util.ArrayList;

public class ListingService {

    private static final String LISTING_ENDPOINT_URL = "https://my.api.mockaroo.com/listing?key=63304c70";

    private HttpRequestService httpRequestService;

    public ListingService(HttpRequestService newHttpRequestService) {
        this.setHttpRequestService(newHttpRequestService);
    }

    public void getAndSaveListingDataFromWebsite(){
        this.getAndConvertListingData();
    }

    private void getAndConvertListingData() {
        this.httpRequestService.sendGetRequest(LISTING_ENDPOINT_URL);
    }

    public HttpRequestService getHttpRequestService() {
        return httpRequestService;
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }
}
