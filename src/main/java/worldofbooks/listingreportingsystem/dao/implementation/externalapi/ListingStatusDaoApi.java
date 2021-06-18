package worldofbooks.listingreportingsystem.dao.implementation.externalapi;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.ListingStatusApiRepository;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;
import worldofbooks.listingreportingsystem.util.HttpRequestUtil;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;

public class ListingStatusDaoApi implements ListingStatusApiRepository {
    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String LISTING_STATUS_ENDPOINT_URL = "https://my.api.mockaroo.com/listingStatus?key=" + MOCKAROO_API_KEY;
    private List<ListingStatus> fetchedListingStatuses;

    @Override
    public List<ListingStatus> getAllListingStatuses() {
        if(fetchedListingStatuses == null || fetchedListingStatuses.isEmpty()) {
            String fetchedData = this.fetchData();
            this.fetchedListingStatuses = this.getListingStatusListFromJson(fetchedData);
        }
        return this.fetchedListingStatuses;
    }

    @Override
    public ListingStatus getListingStatusById(int searchedId) {
        return fetchedListingStatuses.stream()
                .filter(listingStatus -> listingStatus.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }

    private List<ListingStatus> getListingStatusListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<ListingStatus>>() {});
    }

    private String fetchData() {
        return HttpRequestUtil.sendGetRequest(LISTING_STATUS_ENDPOINT_URL);
    }
}
