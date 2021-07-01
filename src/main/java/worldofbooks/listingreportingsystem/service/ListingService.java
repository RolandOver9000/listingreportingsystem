package worldofbooks.listingreportingsystem.service;

import worldofbooks.listingreportingsystem.dao.repository.database.ListingDbRepository;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.ListingApiRepository;
import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;
import worldofbooks.listingreportingsystem.model.entity.Listing;
import worldofbooks.listingreportingsystem.service.validation.IncomingListingValidationService;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListingService {

    private ListingDbRepository listingDBRepository;
    private ListingApiRepository listingAPIRepository;
    private final LocationService locationService;
    private final ListingStatusService listingStatusService;
    private final MarketplaceService marketplaceService;
    private final IncomingListingValidationService incomingListingValidationService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    public ListingService(ListingDbRepository newListingDbRepository,
                          LocationService newLocationService,
                          ListingStatusService newListingStatusService,
                          MarketplaceService newMarketplaceService,
                          IncomingListingValidationService newIncomingListingValidationService,
                          ListingApiRepository newListingAPIRepository) {

        this.listingDBRepository = newListingDbRepository;
        this.locationService = newLocationService;
        this.listingStatusService = newListingStatusService;
        this.marketplaceService = newMarketplaceService;
        this.incomingListingValidationService = newIncomingListingValidationService;
        this.listingAPIRepository = newListingAPIRepository;
    }

    public void fetchAndSaveValidData() {
        List<ListingIncomingDTO> incomingListings = this.listingAPIRepository.getAllListings();
        List<ListingIncomingDTO> validatedIncomingListings =
                this.incomingListingValidationService.getValidatedIncomingListings(incomingListings);
        this.saveListingListElementsFromFetchedDataToDB(validatedIncomingListings);
    }

    public void saveListingToDB(Listing listing){
        listingDBRepository.saveListing(listing);
    }

    private void saveListingListElementsFromFetchedDataToDB(List<ListingIncomingDTO> incomingListings) {
        for(ListingIncomingDTO listingIncomingDTO : incomingListings) {
            Listing newListing = new Listing();
            newListing.setId(listingIncomingDTO.getId());
            newListing.setTitle(listingIncomingDTO.getTitle());
            newListing.setDescription(listingIncomingDTO.getDescription());
            newListing.setListingPrice(listingIncomingDTO.getListing_price());
            newListing.setCurrency(listingIncomingDTO.getCurrency());
            newListing.setQuantity(Integer.parseInt(listingIncomingDTO.getQuantity()));
            newListing.setOwnerEmailAddress(listingIncomingDTO.getOwner_email_address());
            newListing.setUploadTime(listingIncomingDTO.getUpload_time() != null ?
                    StringConverterUtil
                            .tryFormatDateFromString(listingIncomingDTO.getUpload_time(), formatter) : null);
            newListing.setLocation(this.locationService
                    .getLocationByIdFromAPI(listingIncomingDTO.getLocation_id()));
            newListing.setListingStatus(this.listingStatusService
                    .getListingStatusByIdFromAPI(Integer.parseInt(listingIncomingDTO.getListing_status())));
            newListing.setMarketplace(this.marketplaceService
                    .getMarketplaceByIdFromAPI(Integer.parseInt(listingIncomingDTO.getMarketplace())));

            this.saveListingToDB(newListing);
        }
    }
}
