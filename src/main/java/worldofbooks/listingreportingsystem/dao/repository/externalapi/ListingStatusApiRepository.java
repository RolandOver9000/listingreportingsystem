package worldofbooks.listingreportingsystem.dao.repository.externalapi;

import worldofbooks.listingreportingsystem.model.entity.ListingStatus;

import java.util.List;

public interface ListingStatusApiRepository {
    List<ListingStatus> getAllListingStatuses();
    ListingStatus getListingStatusById(int searchedId);
}
