package mother.marketplace;

import worldofbooks.listingreportingsystem.model.entity.Marketplace;

public class MarketplaceMother {

    private static int latestId = -1;

    public static Marketplace getMarketplaceWithNameOf(String marketplaceName) {
        Marketplace marketplace = new Marketplace();
        marketplace.setId(getNextId());

        marketplace.setMarketplaceName(marketplaceName);
        return marketplace;
    }

    public static Marketplace getMarketplaceWithFixIdAndWithoutListing() {
        Marketplace marketplace = new Marketplace();
        marketplace.setId(0);
        marketplace.setMarketplaceName("EBAY");

        return marketplace;
    }

    private static int getNextId(){
        latestId += 1;
        return latestId;
    }
}
