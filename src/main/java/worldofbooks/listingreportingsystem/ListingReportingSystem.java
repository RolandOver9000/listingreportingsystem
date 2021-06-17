package worldofbooks.listingreportingsystem;

import worldofbooks.listingreportingsystem.service.*;
import worldofbooks.listingreportingsystem.util.JPAUtil;

import javax.persistence.EntityManager;

public class ListingReportingSystem {


    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        HttpRequestService httpRequestService = new HttpRequestService();
        DataHandlerService dataHandlerService = new DataHandlerService(httpRequestService, entityManager);

        dataHandlerService.fetchAndSaveData();

        entityManager.close();
        JPAUtil.shutdown();
    }
}
