package worldofbooks.listingreportingsystem.service.validation;

import org.apache.commons.validator.routines.EmailValidator;
import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;
import worldofbooks.listingreportingsystem.service.ListingStatusService;
import worldofbooks.listingreportingsystem.service.LocationService;
import worldofbooks.listingreportingsystem.service.MarketplaceService;
import worldofbooks.listingreportingsystem.util.FileHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class ListingValidationService {

    private List<ListingIncomingDTO> validListings;
    private List<String> invalidListings;
    private List<String> invalidFields;
    private MarketplaceService marketplaceService;
    private LocationService locationService;
    private ListingStatusService listingStatusService;
    private static final String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";

    public ListingValidationService(MarketplaceService newMarketplaceService,
                                    LocationService newLocationService,
                                    ListingStatusService newListingStatusService) {
        this.setMarketplaceService(newMarketplaceService);
        this.setLocationService(newLocationService);
        this.setListingStatusService(newListingStatusService);
    }

    public List<ListingIncomingDTO> validateIncomingListings(List<ListingIncomingDTO> incomingListings) {
        this.startValidateListings(incomingListings);
        this.saveInvalidFieldsToLogFile();
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
                        .getMarketplaceByIdFromFetchedData(Integer.parseInt(listing.getMarketplace()))
                        .getMarketplaceName());

        for(String invalidField : this.invalidFields) {
            formattedLogString
                    .append(";")
                    .append(invalidField);
        }
        return formattedLogString.toString();
    }


    private void saveInvalidFieldsToLogFile() {
        FileHandler.writeToListingValidationLogFile(this.invalidListings);
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
                this.marketplaceService.getMarketplaceByIdFromFetchedData(Integer.parseInt(marketplace)) == null) {
            this.invalidFields.add("marketplace");
        }
    }

    private void checkListingStatus(String listingStatus) {
        if(listingStatus == null ||
                this.listingStatusService.getListingStatusByIdFromFetchedData(Integer.parseInt(listingStatus)) == null){
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
        if(id == null || this.locationService.getLocationByIdFromFetchedData(id) == null) {
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

    public void setMarketplaceService(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public void setListingStatusService(ListingStatusService listingStatusService) {
        this.listingStatusService = listingStatusService;
    }
}
