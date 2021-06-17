package worldofbooks.listingreportingsystem.dao.implementation;

import worldofbooks.listingreportingsystem.dao.repository.MarketplaceRepository;
import worldofbooks.listingreportingsystem.model.entity.ListingStatus;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;

import javax.persistence.EntityManager;

public class MarketplaceDAODB implements MarketplaceRepository {

    private EntityManager entityManager;

    public MarketplaceDAODB(EntityManager entityManager) {
        this.setEntityManager(entityManager);
    }

    @Override
    public void saveMarketplace(Marketplace newMarketplace) {
        entityManager.getTransaction().begin();
        entityManager.persist(newMarketplace);
        System.out.println(entityManager.find(Marketplace.class, newMarketplace.getId()).toString());
        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
