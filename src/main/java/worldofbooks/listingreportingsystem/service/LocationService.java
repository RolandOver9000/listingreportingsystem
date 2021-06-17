package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.externalapi.LocationAPIDAO;
import worldofbooks.listingreportingsystem.dao.repository.LocationRepository;
import worldofbooks.listingreportingsystem.model.entity.Location;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;
import java.util.UUID;

public class LocationService {

    private LocationRepository locationRepository;
    private List<Location> fetchedLocations;
    private LocationAPIDAO locationAPIDAO;

    public LocationService(LocationRepository newLocationRepository,
                           LocationAPIDAO newLocationAPIDAO) {
        this.setLocationAPIDAO(newLocationAPIDAO);
        this.setLocationRepository(newLocationRepository);
    }

    public void fetchAndSaveData() {
        String fetchedLocationData = this.locationAPIDAO.fetchData();
        this.setFetchedLocations(this.getLocationListFromJson(fetchedLocationData));
        this.saveLocationListElements(this.getFetchedLocations());
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

    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void setFetchedLocations(List<Location> fetchedLocations) {
        this.fetchedLocations = fetchedLocations;
    }

    public List<Location> getFetchedLocations() {
        return fetchedLocations;
    }

    public void setLocationAPIDAO(LocationAPIDAO locationAPIDAO) {
        this.locationAPIDAO = locationAPIDAO;
    }
}
