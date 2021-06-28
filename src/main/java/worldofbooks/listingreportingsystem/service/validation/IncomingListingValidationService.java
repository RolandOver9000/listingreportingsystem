package worldofbooks.listingreportingsystem.service.validation;

import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;
import worldofbooks.listingreportingsystem.service.ListingStatusService;
import worldofbooks.listingreportingsystem.service.LocationService;
import worldofbooks.listingreportingsystem.service.MarketplaceService;
import worldofbooks.listingreportingsystem.util.FileHandler;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

public class IncomingListingValidationService {

    private final MarketplaceService marketplaceService;
    private final LocationService locationService;
    private final ListingStatusService listingStatusService;
    private List<ListingIncomingDTO> validListings;
    private List<String> invalidListings;
    private List<String> invalidFields;
    private static final String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
    private static final String LISTING_VALIDATION_LOG_FILE_PATH = "src/main/logs/";

    public IncomingListingValidationService(MarketplaceService newMarketplaceService,
                                            LocationService newLocationService,
                                            ListingStatusService newListingStatusService) {
        this.marketplaceService = newMarketplaceService;
        this.locationService = newLocationService;
        this.listingStatusService = newListingStatusService;
    }

    public List<ListingIncomingDTO> getValidatedIncomingListings(List<ListingIncomingDTO> incomingListings) {
        this.startValidateListings(incomingListings);
        this.saveInvalidListingsToLogFile();
        return this.validListings;
    }

    private void startValidateListings(List<ListingIncomingDTO> listings) {
        this.invalidFields = new ArrayList<>();
        this.validListings = new ArrayList<>();
        this.invalidListings = new ArrayList<>();

        for(ListingIncomingDTO listing : listings) {
            this.checkId(listing.getId());
            this.checkTitle(listing.getTitle());
            this.checkDescription(listing.getDescription());
            this.checkLocationId(listing.getLocation_id());
            this.checkListingPrice(listing.getListing_price());
            this.checkCurrency(listing.getCurrency());
            this.checkQuantity(listing.getQuantity());
            this.checkListingStatus(listing.getListing_status());
            this.checkMarketplace(listing.getMarketplace());
            this.checkOwnerEmailAddress(listing.getOwner_email_address());
            this.checkUpdateTime(listing.getUpload_time());

            this.evaluateValidation(listing);
            this.invalidFields.clear();
        }
    }

    private void evaluateValidation(ListingIncomingDTO listing) {
        if(this.invalidFields.size() == 0) {
            this.validListings.add(listing);
        }
        else {
            this.invalidListings.add(this.formatStringForLog(listing));
        }
    }

    private String formatStringForLog(ListingIncomingDTO listing) {
        StringBuilder formattedLogString = new StringBuilder();
        formattedLogString
                .append(listing.getId())
                .append(";")
                .append(this.marketplaceService
                        .getMarketplaceByIdFromAPI(Integer.parseInt(listing.getMarketplace()))
                        .getMarketplaceName());

        for(String invalidField : this.invalidFields) {
            formattedLogString
                    .append(";")
                    .append(invalidField);
        }
        return formattedLogString.toString();
    }

    private void saveInvalidListingsToLogFile() {
        FileHandler.generateListingLogCsvFileFromString_thenSaveToDirectory(
                this.invalidListings,
                LISTING_VALIDATION_LOG_FILE_PATH);
    }


    private void checkUpdateTime(String uploadTime) {
        if(uploadTime == null) {
            this.invalidFields.add("upload_time");
        }
    }

    private void checkOwnerEmailAddress(String ownerEmailAddress) {
        if(ownerEmailAddress == null || !this.isEmailFormatValid(ownerEmailAddress)) {
            this.invalidFields.add("owner_email_address");
        }
    }

    private boolean isEmailFormatValid(String email){
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    private void checkMarketplace(String marketplace) {
        if(marketplace == null ||
                this.marketplaceService.getMarketplaceByIdFromAPI(Integer.parseInt(marketplace)) == null) {
            this.invalidFields.add("marketplace");
        }
    }

    private void checkListingStatus(String listingStatus) {
        if(listingStatus == null ||
                this.listingStatusService.getListingStatusByIdFromAPI(Integer.parseInt(listingStatus)) == null){
            this.invalidFields.add("listing_status");
        }
    }

    private void checkQuantity(String quantity) {
        if(quantity == null || Integer.parseInt(quantity) <= 0) {
            this.invalidFields.add("quantity");
        }
    }

    private void checkCurrency(String currency) {
        if(currency == null || currency.length() != 3) {
            this.invalidFields.add("currency");
        }
    }

    private void checkListingPrice(BigDecimal listingPrice) {
        if(listingPrice == null || listingPrice.compareTo(BigDecimal.ZERO) <= 0 || listingPrice.scale() != 2) {
            this.invalidFields.add("listing_price");
        }
    }

    private void checkLocationId(UUID id) {
        if(id == null || this.locationService.getLocationByIdFromAPI(id) == null) {
            this.invalidFields.add("location_id");
        }
    }

    private void checkDescription(String description) {
        if(description == null) {
            this.invalidFields.add("description");
        }
    }

    private void checkTitle(String title) {
        if(title == null) {
            this.invalidFields.add("title");
        }
    }

    private void checkId(UUID id) {
        if(id == null)  {
            this.invalidFields.add("id");
        }
    }
}
