package worldofbooks.listingreportingsystem.dao.repository.database;

import worldofbooks.listingreportingsystem.model.entity.ListingStatus;

public interface ListingStatusDbRepository {
    void saveListingStatus(ListingStatus newListingStatus);
}
