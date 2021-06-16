package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.repository.LocationRepository;
import worldofbooks.listingreportingsystem.model.entity.Location;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;
import java.util.UUID;

public class LocationService {

    private static final String LOCATION_ENDPOINT_URL = "https://my.api.mockaroo.com/location?key=63304c70";
    private HttpRequestService httpRequestService;
    private LocationRepository locationRepository;
    private List<Location> fetchedLocations;

    public LocationService(HttpRequestService newHttpRequestService,
                           LocationRepository newLocationRepository) {

        this.setHttpRequestService(newHttpRequestService);
        this.setLocationRepository(newLocationRepository);
    }

    public void fetchAndSaveData() {
        String fetchedLocationData = this.fetchLocationData();
        this.setFetchedLocations(this.getLocationListFromJson(fetchedLocationData));
        this.saveLocationListElements(this.getFetchedLocations());
    }

    public String fetchLocationData() {
        return this.httpRequestService.sendGetRequest(LOCATION_ENDPOINT_URL);
    }

    public List<Location> getLocationListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<Location>>() {});
    }

    public void saveLocationListElements(List<Location> locations){
        for(Location location : locations){
            locationRepository.saveLocation(location);
        }
    }

    public Location getLocationByIdFromFetchedData(UUID searchedId) {
        return fetchedLocations.stream()
                .filter(location -> location.getId().equals(searchedId))
                .findFirst()
                .orElse(null);
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void setFetchedLocations(List<Location> fetchedLocations) {
        this.fetchedLocations = fetchedLocations;
    }

    public List<Location> getFetchedLocations() {
        return fetchedLocations;
    }
}
