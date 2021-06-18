package worldofbooks.listingreportingsystem.dao.repository.database;

import worldofbooks.listingreportingsystem.model.entity.Location;

public interface LocationDbRepository {
    void saveLocation(Location newLocation);
}
