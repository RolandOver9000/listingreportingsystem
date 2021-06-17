package worldofbooks.listingreportingsystem.dao.implementation;

import worldofbooks.listingreportingsystem.dao.repository.ListingRepository;
import worldofbooks.listingreportingsystem.model.entity.Listing;

import javax.persistence.EntityManager;

public class ListingDAODB implements ListingRepository {

    private EntityManager entityManager;

    public ListingDAODB(EntityManager newEntityManager) {
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
