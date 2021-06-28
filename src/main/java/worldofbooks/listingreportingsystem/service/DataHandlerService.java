package worldofbooks.listingreportingsystem.service;

import org.apache.commons.net.ftp.FTPClient;
import worldofbooks.listingreportingsystem.dao.implementation.externalapi.ListingDaoApi;
import worldofbooks.listingreportingsystem.dao.implementation.externalapi.ListingStatusDaoApi;
import worldofbooks.listingreportingsystem.dao.implementation.externalapi.LocationDaoApi;
import worldofbooks.listingreportingsystem.dao.implementation.externalapi.MarketplaceDaoApi;
import worldofbooks.listingreportingsystem.dao.implementation.database.ListingDaoDb;
import worldofbooks.listingreportingsystem.dao.implementation.database.ListingStatusDaoDb;
import worldofbooks.listingreportingsystem.dao.implementation.database.LocationDbDaoDb;
import worldofbooks.listingreportingsystem.dao.implementation.database.MarketplaceDaoDb;
import worldofbooks.listingreportingsystem.dao.implementation.ftp.ListingDaoFtp;
import worldofbooks.listingreportingsystem.service.report.ListingReportService;
import worldofbooks.listingreportingsystem.service.validation.IncomingListingValidationService;

import javax.persistence.EntityManager;

public class DataHandlerService {
    private EntityManager entityManager;
    private ListingService listingService;
    private ListingStatusService listingStatusService;
    private LocationService locationService;
    private MarketplaceService marketplaceService;
    private IncomingListingValidationService incomingListingValidationService;
    private ListingReportService listingReportService;

    public DataHandlerService(EntityManager newEntityManager) {
        this.setEntityManager(newEntityManager);
    }

    public void fetchAndSaveData() {
        this.generateServices();
        this.startFetchAndSaveBySubServices();
    }

    public void handleListingReport() {
        this.listingReportService.generateListingReport_thenSaveToFile_thenUploadReportToFtpServer();
    }

    private void generateServices() {
        this.setMarketplaceService(new MarketplaceService(
                new MarketplaceDaoDb(entityManager),
                new MarketplaceDaoApi()));

        this.setLocationService(new LocationService(
                new LocationDbDaoDb(entityManager),
                new LocationDaoApi()));

        this.setListingStatusService(new ListingStatusService(
                new ListingStatusDaoDb(entityManager),
                new ListingStatusDaoApi()));

        this.setIncomingListingValidationService(new IncomingListingValidationService(this.marketplaceService,
                this.locationService,
                this.listingStatusService));

        this.setListingService(new ListingService(
                new ListingDaoDb(entityManager),
                locationService,
                listingStatusService,
                marketplaceService,
                incomingListingValidationService,
                new ListingDaoApi()));

        this.listingReportService = new ListingReportService(
                new ListingDaoDb(entityManager),
                new ListingDaoFtp(new FTPClient()));
    }

    private void startFetchAndSaveBySubServices() {
        listingStatusService.fetchAndSaveData();
        locationService.fetchAndSaveData();
        marketplaceService.fetchAndSaveData();
        listingService.fetchAndSaveValidData();
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

    public void setIncomingListingValidationService(IncomingListingValidationService incomingListingValidationService) {
        this.incomingListingValidationService = incomingListingValidationService;
    }
}
