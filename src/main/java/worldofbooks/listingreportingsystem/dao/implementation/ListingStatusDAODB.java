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
        entityManager.getTransaction().begin();
        entityManager.persist(newListingStatus);
        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
