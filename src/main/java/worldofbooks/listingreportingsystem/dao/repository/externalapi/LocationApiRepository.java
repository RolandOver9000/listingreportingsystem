package worldofbooks.listingreportingsystem.dao.repository.externalapi;

import worldofbooks.listingreportingsystem.model.entity.Location;

import java.util.List;
import java.util.UUID;

public interface LocationApiRepository {
    List<Location> getAllLocations();
    Location getLocationById(UUID searchedId);
}
