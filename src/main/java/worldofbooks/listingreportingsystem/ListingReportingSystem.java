package worldofbooks.listingreportingsystem;

import worldofbooks.listingreportingsystem.service.*;
import worldofbooks.listingreportingsystem.util.HttpRequestUtil;
import worldofbooks.listingreportingsystem.util.JPAUtil;

import javax.persistence.EntityManager;

public class ListingReportingSystem {


    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        DataHandlerService dataHandlerService = new DataHandlerService(httpRequestUtil, entityManager);

        dataHandlerService.fetchAndSaveData();

        entityManager.close();
        JPAUtil.shutdown();
    }
}
