package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.externalapi.ListingAPIDAO;
import worldofbooks.listingreportingsystem.dao.repository.ListingRepository;
import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;
import worldofbooks.listingreportingsystem.model.entity.Listing;
import worldofbooks.listingreportingsystem.service.validation.ListingValidationService;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListingService {

    private ListingRepository listingRepository;
    private List<ListingIncomingDTO> fetchedListings;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private LocationService locationService;
    private ListingStatusService listingStatusService;
    private MarketplaceService marketplaceService;
    private ListingValidationService listingValidationService;
    private ListingAPIDAO listingAPIDAO;

    public ListingService(ListingRepository newListingRepository,
                          LocationService newLocationService,
                          ListingStatusService newListingStatusService,
                          MarketplaceService newMarketplaceService,
                          ListingValidationService newListingValidationService,
                          ListingAPIDAO newListingAPIDAO) {

        this.setListingRepository(newListingRepository);
        this.setLocationService(newLocationService);
        this.setListingStatusService(newListingStatusService);
        this.setMarketplaceService(newMarketplaceService);
        this.setListingValidationService(newListingValidationService);
        this.setListingAPIDAO(newListingAPIDAO);
    }

    public void fetchAndSaveValidData() {
        String fetchedListingData = this.listingAPIDAO.fetchData();
        this.setFetchedListings(this.getListingListFromJson(fetchedListingData));
        this.setFetchedListings(this.listingValidationService.getValidatedIncomingListings(this.fetchedListings));
        this.saveListingListElementsFromFetchedData();
    }

    public List<ListingIncomingDTO> getListingListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<ListingIncomingDTO>>() {});
    }

    public void saveListingListElementsFromFetchedData() {
        for(ListingIncomingDTO listingIncomingDTO : fetchedListings) {
            Listing newListing = new Listing();
            newListing.setId(listingIncomingDTO.getId());
            newListing.setTitle(listingIncomingDTO.getTitle());
            newListing.setDescription(listingIncomingDTO.getDescription());
            newListing.setListingPrice(listingIncomingDTO.getListing_price());
            newListing.setCurrency(listingIncomingDTO.getCurrency());
            newListing.setQuantity(Integer.parseInt(listingIncomingDTO.getQuantity()));
            newListing.setOwnerEmailAddress(listingIncomingDTO.getOwner_email_address());
            newListing.setUploadTime(listingIncomingDTO.getUpload_time() != null ?
                    StringConverterUtil.tryFormatDateFromString(listingIncomingDTO.getUpload_time(), formatter) : null);
            newListing.setLocation(this.locationService
                    .getLocationByIdFromFetchedData(listingIncomingDTO.getLocation_id()));
            newListing.setListingStatus(this.listingStatusService
                    .getListingStatusByIdFromFetchedData(Integer.parseInt(listingIncomingDTO.getListing_status())));
            newListing.setMarketplace(this.marketplaceService
                    .getMarketplaceByIdFromFetchedData(Integer.parseInt(listingIncomingDTO.getMarketplace())));

            listingRepository.saveListing(newListing);
        }
    }

    public void setListingRepository(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public void setFetchedListings(List<ListingIncomingDTO> fetchedListings) {
        this.fetchedListings = fetchedListings;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public void setListingStatusService(ListingStatusService listingStatusService) {
        this.listingStatusService = listingStatusService;
    }

    public void setMarketplaceService(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    public void setListingValidationService(ListingValidationService listingValidationService) {
        this.listingValidationService = listingValidationService;
    }

    public void setListingAPIDAO(ListingAPIDAO listingAPIDAO) {
        this.listingAPIDAO = listingAPIDAO;
    }
}
