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
        entityManager.persist(newListing);
        System.out.println(entityManager.find(Listing.class, newListing.getId()).toString());
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}