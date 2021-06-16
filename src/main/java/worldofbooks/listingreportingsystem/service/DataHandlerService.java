package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.implementation.ListingDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.ListingStatusDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.LocationDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.MarketplaceDAODB;
import worldofbooks.listingreportingsystem.dao.repository.ListingRepository;
import worldofbooks.listingreportingsystem.dao.repository.ListingStatusRepository;
import worldofbooks.listingreportingsystem.dao.repository.LocationRepository;
import worldofbooks.listingreportingsystem.dao.repository.MarketplaceRepository;
import worldofbooks.listingreportingsystem.model.dto.ListingIncomingDTO;
import worldofbooks.listingreportingsystem.model.entity.Listing;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;
import worldofbooks.listingreportingsystem.model.entity.Location;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class DataHandlerService {
    private HttpRequestService httpRequestService;
    private EntityManager entityManager;
    private LocationRepository locationRepository;
    private ListingStatusRepository listingStatusRepository;
    private MarketplaceRepository marketplaceRepository;
    private ListingRepository listingRepository;
    private List<Location> fetchedLocations;
    private List<ListingStatus> fetchedListingStatuses;
    private List<Marketplace> fetchedMarketplaces;
    private List<ListingIncomingDTO> fetchedListings;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    private static final String LISTING_ENDPOINT_URL = "https://my.api.mockaroo.com/listing?key=63304c70";
    private static final String LISTING_STATUS_ENDPOINT_URL = "https://my.api.mockaroo.com/listingStatus?key=63304c70";
    private static final String LOCATION_ENDPOINT_URL = "https://my.api.mockaroo.com/location?key=63304c70";
    private static final String MARKETPLACE_ENDPOINT_URL = "https://my.api.mockaroo.com/marketplace?key=63304c70";

    public DataHandlerService(HttpRequestService newHttpRequestService, EntityManager newEntityManager) {
        this.setHttpRequestService(newHttpRequestService);
        this.setEntityManager(newEntityManager);
        this.generateDAOs(entityManager);
    }

    private void generateDAOs(EntityManager entityManager) {
        locationRepository = new LocationDAODB(entityManager);
        listingStatusRepository = new ListingStatusDAODB(entityManager);
        marketplaceRepository = new MarketplaceDAODB(entityManager);
        listingRepository = new ListingDAODB(entityManager);
    }

    public void handleDataFetching() {
        this.printSQLInfo();
        fetchedLocations =
                this.fetchAndConvertListingData(LOCATION_ENDPOINT_URL, new TypeReference<List<Location>>() {});

        fetchedListingStatuses =
                this.fetchAndConvertListingData(LISTING_STATUS_ENDPOINT_URL, new TypeReference<List<ListingStatus>>() {});

        fetchedMarketplaces =
                this.fetchAndConvertListingData(MARKETPLACE_ENDPOINT_URL, new TypeReference<List<Marketplace>>() {});

        fetchedListings =
                this.fetchAndConvertListingData(LISTING_ENDPOINT_URL, new TypeReference<List<ListingIncomingDTO>>() {});
    }

    public void handleDataSaving() {
        entityManager.getTransaction().begin();

        for(Location location : fetchedLocations){
            locationRepository.saveLocation(location);
        }

        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();

        for(ListingStatus listingStatus : fetchedListingStatuses) {
            listingStatusRepository.saveListingStatus(listingStatus);
        }

        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();

        for(Marketplace marketplace : fetchedMarketplaces) {
            marketplaceRepository.saveMarketplace(marketplace);
        }

        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();

        for(ListingIncomingDTO listingIncomingDTO : fetchedListings) {
            Listing newListing = new Listing();
            newListing.setId(listingIncomingDTO.getId());
            newListing.setTitle(listingIncomingDTO.getTitle());
            newListing.setDescription(listingIncomingDTO.getDescription());
            newListing.setListingPrice(BigDecimal.valueOf(listingIncomingDTO.getListing_price()));
            newListing.setCurrency(listingIncomingDTO.getCurrency());
            newListing.setQuantity(listingIncomingDTO.getQuantity());
            newListing.setOwnerEmailAddress(listingIncomingDTO.getOwner_email_address());
            System.out.println(listingIncomingDTO.getUpload_time());
            newListing.setUploadTime(listingIncomingDTO.getUpload_time() != null ?
                    StringConverterUtil.tryFormatDateFromString(listingIncomingDTO.getUpload_time(), formatter) : null);
            newListing.setLocation(this.getLocationById(listingIncomingDTO.getLocation_id()));
            newListing.setListingStatus(this.getListingStatusById(listingIncomingDTO.getListing_status()));
            newListing.setMarketplace(this.getMarketplaceById(listingIncomingDTO.getMarketplace()));

            listingRepository.saveListing(newListing);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private Marketplace getMarketplaceById(int searchedId) {
        return fetchedMarketplaces.stream()
                .filter(marketplace -> marketplace.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }

    private ListingStatus getListingStatusById(int searchedId) {
        return fetchedListingStatuses.stream()
                .filter(listingStatus -> listingStatus.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }

    private Location getLocationById(UUID searchedId) {
        return fetchedLocations.stream()
                .filter(location -> location.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }



    private void printSQLInfo() {
        entityManager.getTransaction().begin();

        // Check database version
        String sql = "select version()";

        String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
        System.out.println(result);

        entityManager.getTransaction().commit();
    }

    public <T> T fetchAndConvertListingData(String endpointURL, TypeReference<T> targetClass) {
        String fetchedData = this.httpRequestService.sendGetRequest(endpointURL);
        return this.convertJSONString(fetchedData, targetClass);
    }

    private <T> T convertJSONString(String fetchedData, TypeReference<T> targetClass) {
        return StringConverterUtil
                .convertJsonStringToGivenType(fetchedData, targetClass);
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
