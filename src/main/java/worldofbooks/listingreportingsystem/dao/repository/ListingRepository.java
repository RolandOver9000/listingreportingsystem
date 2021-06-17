package worldofbooks.listingreportingsystem.dao.repository;

import worldofbooks.listingreportingsystem.model.entity.Listing;

public interface ListingRepository {
    void saveListing(Listing newListing);
}
