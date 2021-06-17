package worldofbooks.listingreportingsystem.service;

import worldofbooks.listingreportingsystem.dao.externalapi.ListingAPIDAO;
import worldofbooks.listingreportingsystem.dao.externalapi.ListingStatusAPIDAO;
import worldofbooks.listingreportingsystem.dao.externalapi.LocationAPIDAO;
import worldofbooks.listingreportingsystem.dao.externalapi.MarketplaceAPIDTO;
import worldofbooks.listingreportingsystem.dao.implementation.ListingDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.ListingStatusDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.LocationDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.MarketplaceDAODB;
import worldofbooks.listingreportingsystem.service.validation.ListingValidationService;
import worldofbooks.listingreportingsystem.util.HttpRequestUtil;

import javax.persistence.EntityManager;

public class DataHandlerService {
    private EntityManager entityManager;
    private HttpRequestUtil httpRequestUtil;
    private ListingService listingService;
    private ListingStatusService listingStatusService;
    private LocationService locationService;
    private MarketplaceService marketplaceService;
    private ListingValidationService listingValidationService;

    public DataHandlerService(HttpRequestUtil newHttpRequestUtil, EntityManager newEntityManager) {
        this.setHttpRequestUtil(newHttpRequestUtil);
        this.setEntityManager(newEntityManager);
    }

    public void fetchAndSaveData() {
        this.generateHelperServices();
        this.startFetchAndSaveBySubServices();
    }

    private void generateHelperServices() {
        this.setMarketplaceService(new MarketplaceService(
                new MarketplaceDAODB(entityManager),
                new MarketplaceAPIDTO(httpRequestUtil)));

        this.setLocationService(new LocationService(
                new LocationDAODB(entityManager),
                new LocationAPIDAO(httpRequestUtil)));

        this.setListingStatusService(new ListingStatusService(
                new ListingStatusDAODB(entityManager),
                new ListingStatusAPIDAO(httpRequestUtil)));

        this.setListingValidationService(new ListingValidationService(this.marketplaceService,
                this.locationService,
                this.listingStatusService));

        this.setListingService(new ListingService(
                new ListingDAODB(entityManager),
                locationService,
                listingStatusService,
                marketplaceService,
                listingValidationService,
                new ListingAPIDAO(httpRequestUtil)));
    }

    private void startFetchAndSaveBySubServices() {
        listingStatusService.fetchAndSaveData();
        locationService.fetchAndSaveData();
        marketplaceService.fetchAndSaveData();
        listingService.fetchAndSaveValidData();
    }

    public void setHttpRequestUtil(HttpRequestUtil httpRequestUtil) {
        this.httpRequestUtil = httpRequestUtil;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setListingService(ListingService listingService) {
        this.listingService = listingService;
    }

    public void setListingStatusService(ListingStatusService listingStatusService) {
        this.listingStatusService = listingStatusService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public void setMarketplaceService(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    public void setListingValidationService(ListingValidationService listingValidationService) {
        this.listingValidationService = listingValidationService;
    }
}
