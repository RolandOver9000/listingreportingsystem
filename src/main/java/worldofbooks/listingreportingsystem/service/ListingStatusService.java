package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.externalapi.ListingStatusAPIDAO;
import worldofbooks.listingreportingsystem.dao.repository.ListingStatusRepository;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;

public class ListingStatusService {

    private ListingStatusRepository listingStatusRepository;
    private List<ListingStatus> fetchedListingStatuses;
    private ListingStatusAPIDAO listingStatusAPIDAO;

    public ListingStatusService(ListingStatusRepository newListingStatusRepository,
                                ListingStatusAPIDAO newListingStatusAPIDAO) {

        this.setListingStatusAPIDAO(newListingStatusAPIDAO);
        this.setListingStatusRepository(newListingStatusRepository);
    }

    public void fetchAndSaveData() {
        String fetchListingStatusData = this.listingStatusAPIDAO.fetchData();
        this.setFetchedListingStatuses(this.getListingStatusListFromJson(fetchListingStatusData));
        this.saveListingStatusListElements(this.getFetchedListingStatuses());
    }

    public List<ListingStatus> getListingStatusListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<ListingStatus>>() {});
    }

    public void saveListingStatusListElements(List<ListingStatus> listingStatuses){
        for(ListingStatus listingStatus : listingStatuses){
            listingStatusRepository.saveListingStatus(listingStatus);
        }
    }

    public ListingStatus getListingStatusByIdFromFetchedData(int searchedId) {
        return fetchedListingStatuses.stream()
                .filter(listingStatus -> listingStatus.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }

    public void setListingStatusRepository(ListingStatusRepository listingStatusRepository) {
        this.listingStatusRepository = listingStatusRepository;
    }

    public void setFetchedListingStatuses(List<ListingStatus> fetchedListingStatuses) {
        this.fetchedListingStatuses = fetchedListingStatuses;
    }

    public List<ListingStatus> getFetchedListingStatuses() {
        return fetchedListingStatuses;
    }

    public void setListingStatusAPIDAO(ListingStatusAPIDAO listingStatusAPIDAO) {
        this.listingStatusAPIDAO = listingStatusAPIDAO;
    }
}
