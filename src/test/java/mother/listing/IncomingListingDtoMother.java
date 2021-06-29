package mother.listing;

import mother.listingstatus.ListingStatusMother;
import mother.location.LocationMother;
import mother.marketplace.MarketplaceMother;
import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class IncomingListingDtoMother {

    public static ListingIncomingDTO getValidIncomingListingDto() {
        ListingIncomingDTO listingIncomingDTO = new ListingIncomingDTO();
        listingIncomingDTO.setId(UUID.randomUUID());
        listingIncomingDTO.setCurrency("HUF");
        listingIncomingDTO.setUpload_time(LocalDateTime.now().toString());
        listingIncomingDTO.setDescription("Google listing");
        listingIncomingDTO.setListing_price(BigDecimal.TEN);
        listingIncomingDTO.setLocation_id(
                LocationMother.getLocationWithFixUUIDAndWithoutListing().getId());
        listingIncomingDTO.setListing_status(
                String.valueOf(ListingStatusMother.getListingStatusWithFixIdAndWithoutListing().getId())
        );
        listingIncomingDTO.setMarketplace(
                String.valueOf(MarketplaceMother.getMarketplaceWithFixIdAndWithoutListing().getId())
        );
        listingIncomingDTO.setOwner_email_address("random.user@gmail.com");
        listingIncomingDTO.setQuantity("10");
        listingIncomingDTO.setTitle("Google listing");

        return listingIncomingDTO;
    }

    public static ListingIncomingDTO getInvalidIncomingListingDto() {
        ListingIncomingDTO listingIncomingDTO = new ListingIncomingDTO();
        listingIncomingDTO.setId(UUID.randomUUID());

        return listingIncomingDTO;
    }
}
