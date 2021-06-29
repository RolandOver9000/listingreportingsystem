package worldofbooks.listingreportingsystem.dao.repository.database;

import worldofbooks.listingreportingsystem.model.entity.Listing;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public interface ListingDbRepository {
    void saveListing(Listing newListing);

    long getListingCount();
    long getListingCountByMarketplaceName(String marketplaceName);
    BigDecimal getTotalListingPriceByMarketplaceName(String marketplaceName);
    Listing getBestListing();
    Map<String, String> getListingCountByMarketplaceNamePerMonth(String marketplaceName);
    Map<String, String> getTotalListingPriceByMarketplaceNamePerMonth(String marketplaceName);
    Map<String, String> getBestListingEmailAddressPerMonth();
}
