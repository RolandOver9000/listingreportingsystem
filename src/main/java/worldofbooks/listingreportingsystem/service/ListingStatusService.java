package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.repository.ListingStatusRepository;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;

public class ListingStatusService {

    private static final String LISTING_STATUS_ENDPOINT_URL = "https://my.api.mockaroo.com/listingStatus?key=63304c70";
    private HttpRequestService httpRequestService;
    private ListingStatusRepository listingStatusRepository;
    private List<ListingStatus> fetchedListingStatuses;

    public ListingStatusService(HttpRequestService newHttpRequestService,
                                ListingStatusRepository newListingStatusRepository) {

        this.setHttpRequestService(newHttpRequestService);
        this.setListingStatusRepository(newListingStatusRepository);
    }

    public void fetchAndSaveData() {
        String fetchListingStatusData = this.fetchListingStatusData();
        this.setFetchedListingStatuses(this.getListingStatusListFromJson(fetchListingStatusData));
        this.saveListingStatusListElements(this.getFetchedListingStatuses());
    }

    public String fetchListingStatusData() {
        return this.httpRequestService.sendGetRequest(LISTING_STATUS_ENDPOINT_URL);
    }

    public List<ListingStatus> getListingStatusListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<ListingStatus>>() {});
    }

    public void saveListingStatusListElements(List<ListingStatus> listingStatuses){
        for(ListingStatus listingStatus : listingStatuses){
            listingStatusRepository.saveListingStatus(listingStatus);
        }
    }

    public ListingStatus getListingStatusByIdFromFetchedData(int searchedId) {
        return fetchedListingStatuses.stream()
                .filter(listingStatus -> listingStatus.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public void setListingStatusRepository(ListingStatusRepository listingStatusRepository) {
        this.listingStatusRepository = listingStatusRepository;
    }

    public void setFetchedListingStatuses(List<ListingStatus> fetchedListingStatuses) {
        this.fetchedListingStatuses = fetchedListingStatuses;
    }

    public List<ListingStatus> getFetchedListingStatuses() {
        return fetchedListingStatuses;
    }
}
