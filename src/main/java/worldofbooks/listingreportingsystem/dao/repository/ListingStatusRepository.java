package worldofbooks.listingreportingsystem.dao.repository;

import worldofbooks.listingreportingsystem.model.entity.ListingStatus;

public interface ListingStatusRepository {
    void saveListingStatus(ListingStatus newListingStatus);
}
