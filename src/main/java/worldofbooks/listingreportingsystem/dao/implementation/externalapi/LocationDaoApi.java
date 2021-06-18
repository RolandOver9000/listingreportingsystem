package worldofbooks.listingreportingsystem.dao.implementation.externalapi;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.LocationApiRepository;
import worldofbooks.listingreportingsystem.model.entity.Location;
import worldofbooks.listingreportingsystem.util.HttpRequestUtil;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;
import java.util.UUID;

public class LocationDaoApi implements LocationApiRepository {

    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String LOCATION_ENDPOINT_URL = "https://my.api.mockaroo.com/location?key=" + MOCKAROO_API_KEY;
    private List<Location> fetchedLocations;


    @Override
    public List<Location> getAllLocations() {
        if(fetchedLocations == null || fetchedLocations.isEmpty()) {
            String fetchedData = this.fetchData();
            this.fetchedLocations = this.getLocationListFromJson(fetchedData);
        }
        return this.fetchedLocations;
    }

    @Override
    public Location getLocationById(UUID searchedId) {
        return fetchedLocations.stream()
                .filter(location -> location.getId().equals(searchedId))
                .findFirst()
                .orElse(null);
    }

    private List<Location> getLocationListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<Location>>() {});
    }

    public String fetchData() {
        return HttpRequestUtil.sendGetRequest(LOCATION_ENDPOINT_URL);
    }
}
