package worldofbooks.listingreportingsystem.dao.repository.database;

import worldofbooks.listingreportingsystem.model.entity.Listing;

public interface ListingDbRepository {
    void saveListing(Listing newListing);
}
