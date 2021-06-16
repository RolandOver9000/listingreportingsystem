package worldofbooks.listingreportingsystem.dao.implementation;

import worldofbooks.listingreportingsystem.dao.repository.ListingStatusRepository;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;

import javax.persistence.EntityManager;

public class ListingStatusDAODB implements ListingStatusRepository {

    private EntityManager entityManager;

    public ListingStatusDAODB(EntityManager entityManager) {
        this.setEntityManager(entityManager);
    }

    @Override
    public void saveListingStatus(ListingStatus newListingStatus) {
        entityManager.persist(newListingStatus);
        System.out.println(entityManager.find(ListingStatus.class, newListingStatus.getId()).toString());
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
