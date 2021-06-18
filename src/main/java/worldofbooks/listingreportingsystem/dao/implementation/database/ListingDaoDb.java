package worldofbooks.listingreportingsystem.dao.implementation.database;

import worldofbooks.listingreportingsystem.dao.repository.database.ListingDbRepository;
import worldofbooks.listingreportingsystem.model.entity.Listing;

import javax.persistence.EntityManager;

public class ListingDaoDb implements ListingDbRepository {

    private EntityManager entityManager;

    public ListingDaoDb(EntityManager newEntityManager) {
        this.setEntityManager(newEntityManager);
    }

    @Override
    public void saveListing(Listing newListing) {
        entityManager.getTransaction().begin();
        entityManager.persist(newListing);
        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
