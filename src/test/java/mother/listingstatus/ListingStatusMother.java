package mother.listingstatus;

import worldofbooks.listingreportingsystem.model.entity.ListingStatus;

public class ListingStatusMother {

    public static ListingStatus getListingStatusWithFixIdAndWithoutListing() {
        ListingStatus listingStatus = new ListingStatus();
        listingStatus.setId(0);
        listingStatus.setStatusName("ACTIVE");

        return listingStatus;
    }
}
