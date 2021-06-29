package listingreportingsystem.dao.implementation.database;

import mother.listing.ListingMother;
import mother.marketplace.MarketplaceMother;
import org.junit.jupiter.api.*;
import worldofbooks.listingreportingsystem.dao.implementation.database.ListingDaoDb;
import worldofbooks.listingreportingsystem.dao.implementation.database.MarketplaceDaoDb;
import worldofbooks.listingreportingsystem.model.entity.Listing;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;
import worldofbooks.listingreportingsystem.util.JPAUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class ListingDaoDbTest {

    private static ListingDaoDb listingDaoDb;
    private static MarketplaceDaoDb marketplaceDaoDb;
    private static EntityManager entityManager;

    @BeforeAll
    public static void setUp(){
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        listingDaoDb = new ListingDaoDb(entityManager);
        marketplaceDaoDb = new MarketplaceDaoDb(entityManager);
    }


    @AfterAll
    public static void tearDown(){
        entityManager.close();
        JPAUtil.shutdown();
    }

    @Test
    @DisplayName("Save one Listing.")
    public void saveOneListingTest(){
        Listing testListing = ListingMother.getListingOnlyWithRandomId();

        listingDaoDb.saveListing(testListing);

        assertEquals(1, listingDaoDb.getListingCount());
    }

    @Test
    @DisplayName("Get two listing count by marketplace name.")
    public void getTwoListingCountsByMarketplaceNameTest() {
        Listing testListingWithEbayMarket = ListingMother.getListingOnlyWithRandomId();
        Listing testListingWithEbayMarket2 = ListingMother.getListingOnlyWithRandomId();
        Listing testListingWithAmazonMarket = ListingMother.getListingOnlyWithRandomId();

        Marketplace testMarketplaceEbay = MarketplaceMother.getMarketplaceWithNameOf("EBAY");
        Marketplace testMarketplaceAmazon = MarketplaceMother.getMarketplaceWithNameOf("AMAZON");

        testListingWithEbayMarket.setMarketplace(testMarketplaceEbay);
        testListingWithEbayMarket2.setMarketplace(testMarketplaceEbay);
        testListingWithAmazonMarket.setMarketplace(testMarketplaceAmazon);

        marketplaceDaoDb.saveMarketplace(testMarketplaceEbay);
        marketplaceDaoDb.saveMarketplace(testMarketplaceAmazon);
        listingDaoDb.saveListing(testListingWithEbayMarket);
        listingDaoDb.saveListing(testListingWithEbayMarket2);
        listingDaoDb.saveListing(testListingWithAmazonMarket);

        assertEquals(2,
                listingDaoDb.getListingCountByMarketplaceName(testMarketplaceEbay.getMarketplaceName()));
    }

    @Test
    @DisplayName("Get total listing price by marketplace name.")
    public void getTotalListingPriceByMarketplaceNameTest(){
        Listing testListingWithEbayMarket = ListingMother.getListingWithRandomPrice();
        Listing testListingWithEbayMarket2 = ListingMother.getListingWithRandomPrice();
        Listing testListingWithAmazonMarket = ListingMother.getListingWithRandomPrice();

        Marketplace testMarketplaceEbay = MarketplaceMother.getMarketplaceWithNameOf("EBAY");
        Marketplace testMarketplaceAmazon = MarketplaceMother.getMarketplaceWithNameOf("AMAZON");

        testListingWithEbayMarket.setMarketplace(testMarketplaceEbay);
        testListingWithEbayMarket2.setMarketplace(testMarketplaceEbay);
        testListingWithAmazonMarket.setMarketplace(testMarketplaceAmazon);

        marketplaceDaoDb.saveMarketplace(testMarketplaceEbay);
        marketplaceDaoDb.saveMarketplace(testMarketplaceAmazon);
        listingDaoDb.saveListing(testListingWithEbayMarket);
        listingDaoDb.saveListing(testListingWithEbayMarket2);
        listingDaoDb.saveListing(testListingWithAmazonMarket);

        BigDecimal totalEbayListingPrice = testListingWithEbayMarket.getListingPrice()
                .add(testListingWithEbayMarket2.getListingPrice());

        assertEquals(totalEbayListingPrice,
                listingDaoDb.getTotalListingPriceByMarketplaceName(testMarketplaceEbay.getMarketplaceName()));
    }

    @Test
    @DisplayName("Get best listing.")
    public void getBestListingTest() {
        Listing testListing = ListingMother.
                getListingWithGivenPriceAndQuantity(BigDecimal.valueOf(200), 10);

        Listing testListing2 = ListingMother.
                getListingWithGivenPriceAndQuantity(BigDecimal.valueOf(300), 11);

        listingDaoDb.saveListing(testListing);
        listingDaoDb.saveListing(testListing2);

        assertEquals(testListing2,
                listingDaoDb.getBestListing());
    }
}
