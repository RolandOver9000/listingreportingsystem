package worldofbooks.listingreportingsystem.service;

import worldofbooks.listingreportingsystem.dao.repository.database.ListingStatusDbRepository;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.ListingStatusApiRepository;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;

import java.util.List;

public class ListingStatusService {

    private ListingStatusDbRepository listingStatusDBRepository;
    private ListingStatusApiRepository listingStatusAPIRepository;

    public ListingStatusService(ListingStatusDbRepository newListingStatusDbRepository,
                                ListingStatusApiRepository newListingStatusAPIDAO) {

        this.listingStatusAPIRepository = newListingStatusAPIDAO;
        this.listingStatusDBRepository = newListingStatusDbRepository;
    }

    public void fetchAndSaveData() {
        this.saveListingStatusListToDB(this.listingStatusAPIRepository.getAllListingStatuses());
    }

    public void saveListingStatusListToDB(List<ListingStatus> listingStatuses){
        for(ListingStatus listingStatus : listingStatuses){
            listingStatusDBRepository.saveListingStatus(listingStatus);
        }
    }

    public ListingStatus getListingStatusByIdFromAPI(int id) {
        return this.listingStatusAPIRepository.getListingStatusById(id);
    }

    public void setListingStatusDBRepository(ListingStatusDbRepository listingStatusDBRepository) {
        this.listingStatusDBRepository = listingStatusDBRepository;
    }

    public void setListingStatusAPIRepository(ListingStatusApiRepository listingStatusAPIRepository) {
        this.listingStatusAPIRepository = listingStatusAPIRepository;
    }
}
