package worldofbooks.listingreportingsystem.dao.implementation.database;

import worldofbooks.listingreportingsystem.dao.repository.database.ListingStatusDbRepository;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;

import javax.persistence.EntityManager;

public class ListingStatusDaoDb implements ListingStatusDbRepository {

    private EntityManager entityManager;

    public ListingStatusDaoDb(EntityManager entityManager) {
        this.setEntityManager(entityManager);
    }

    @Override
    public void saveListingStatus(ListingStatus newListingStatus) {
        entityManager.getTransaction().begin();
        entityManager.persist(newListingStatus);
        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
