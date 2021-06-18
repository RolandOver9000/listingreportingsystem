package worldofbooks.listingreportingsystem.dao.implementation.externalapi;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.ListingApiRepository;
import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;
import worldofbooks.listingreportingsystem.util.HttpRequestUtil;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;

public class ListingDaoApi implements ListingApiRepository {
    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String LISTING_ENDPOINT_URL = "https://my.api.mockaroo.com/listing?key=" + MOCKAROO_API_KEY;
    private List<ListingIncomingDTO> fetchedListings;

    public List<ListingIncomingDTO> getAllListings() {
        if(fetchedListings == null || fetchedListings.isEmpty()) {
            String fetchedData = this.fetchData();
            this.fetchedListings = this.getListingListFromJson(fetchedData);
        }
        return this.fetchedListings;
    }

    private List<ListingIncomingDTO> getListingListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<ListingIncomingDTO>>() {});
    }

    private String fetchData() {
        return HttpRequestUtil.sendGetRequest(LISTING_ENDPOINT_URL);
    }
}
