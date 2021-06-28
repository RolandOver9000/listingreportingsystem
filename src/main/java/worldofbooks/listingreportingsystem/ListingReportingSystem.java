package worldofbooks.listingreportingsystem;

import worldofbooks.listingreportingsystem.service.*;
import worldofbooks.listingreportingsystem.util.JPAUtil;

import javax.persistence.EntityManager;

public class ListingReportingSystem {


    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        DataHandlerService dataHandlerService = new DataHandlerService(entityManager);

        dataHandlerService.fetchAndSaveData();
        dataHandlerService.handleListingReport();

        entityManager.close();
        JPAUtil.shutdown();
    }
}
