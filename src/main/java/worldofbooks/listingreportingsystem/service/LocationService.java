package worldofbooks.listingreportingsystem.service;

import worldofbooks.listingreportingsystem.dao.repository.database.LocationDbRepository;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.LocationApiRepository;
import worldofbooks.listingreportingsystem.model.entity.Location;

import java.util.List;
import java.util.UUID;

public class LocationService {

    private LocationDbRepository locationDbRepository;
    private LocationApiRepository locationApiRepository;

    public LocationService(LocationDbRepository newLocationDbRepository,
                           LocationApiRepository newLocationApiRepository) {
        this.locationDbRepository = newLocationDbRepository;
        this.locationApiRepository = newLocationApiRepository;
    }

    public void fetchAndSaveData() {
        this.saveLocationListToDB(this.locationApiRepository.getAllLocations());
    }

    public void saveLocationListToDB(List<Location> locations){
        for(Location location : locations){
            locationDbRepository.saveLocation(location);
        }
    }

    public Location getLocationByIdFromAPI(UUID id) {
        return this.locationApiRepository.getLocationById(id);
    }

    public void setLocationDbRepository(LocationDbRepository locationDbRepository) {
        this.locationDbRepository = locationDbRepository;
    }

    public void setLocationApiRepository(LocationApiRepository locationApiRepository) {
        this.locationApiRepository = locationApiRepository;
    }
}
