package mother.listing;

import worldofbooks.listingreportingsystem.model.entity.Listing;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class ListingMother {

    private static Random random = new Random();

    public static Listing getListingOnlyWithRandomId(){
        Listing generatedListing = new Listing();
        generatedListing.setId(UUID.randomUUID());

        return generatedListing;
    }

    public static Listing getListingWithRandomPrice(){
        Listing generatedListing = new Listing();
        generatedListing.setId(UUID.randomUUID());
        generatedListing.setListingPrice(BigDecimal.valueOf(getRandomNumberBetween0And1000()));

        return generatedListing;
    }

    public static Listing getListingWithGivenPriceAndQuantity(BigDecimal price, int quantity) {
        Listing generatedListing = new Listing();
        generatedListing.setId(UUID.randomUUID());
        generatedListing.setListingPrice(price);
        generatedListing.setQuantity(quantity);

        return generatedListing;
    }

    private static int getRandomNumberBetween0And1000() {
        return random.nextInt(1000);
    }
}
