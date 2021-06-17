package worldofbooks.listingreportingsystem.service;

import worldofbooks.listingreportingsystem.dao.implementation.ListingDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.ListingStatusDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.LocationDAODB;
import worldofbooks.listingreportingsystem.dao.implementation.MarketplaceDAODB;
import worldofbooks.listingreportingsystem.service.validation.ListingValidationService;

import javax.persistence.EntityManager;

public class DataHandlerService {
    private EntityManager entityManager;
    private HttpRequestService httpRequestService;
    private ListingService listingService;
    private ListingStatusService listingStatusService;
    private LocationService locationService;
    private MarketplaceService marketplaceService;
    private ListingValidationService listingValidationService;

    public DataHandlerService(HttpRequestService newHttpRequestService, EntityManager newEntityManager) {
        this.setHttpRequestService(newHttpRequestService);
        this.setEntityManager(newEntityManager);
    }

    public void fetchAndSaveData() {
        this.generateHelperServices();
        this.startFetchAndSaveBySubServices();
    }

    private void generateHelperServices() {
        this.setMarketplaceService(new MarketplaceService(httpRequestService, new MarketplaceDAODB(entityManager)));
        this.setLocationService(new LocationService(httpRequestService, new LocationDAODB(entityManager)));
        this.setListingStatusService(new ListingStatusService(httpRequestService, new ListingStatusDAODB(entityManager)));
        this.setListingValidationService(new ListingValidationService(this.marketplaceService,
                this.locationService,
                this.listingStatusService));

        this.setListingService(new ListingService(
                httpRequestService,
                new ListingDAODB(entityManager),
                locationService,
                listingStatusService,
                marketplaceService,
                listingValidationService));
    }

    private void startFetchAndSaveBySubServices() {
        listingStatusService.fetchAndSaveData();
        locationService.fetchAndSaveData();
        marketplaceService.fetchAndSaveData();
        listingService.fetchAndSaveValidData();
    }

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
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
