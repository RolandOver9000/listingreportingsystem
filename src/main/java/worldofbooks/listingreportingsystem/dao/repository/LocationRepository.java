package worldofbooks.listingreportingsystem.dao.repository;

import worldofbooks.listingreportingsystem.model.entity.Location;

public interface LocationRepository {
    void saveLocation(Location newLocation);
}
