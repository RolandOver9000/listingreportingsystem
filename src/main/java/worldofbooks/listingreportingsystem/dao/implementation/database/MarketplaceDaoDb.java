package worldofbooks.listingreportingsystem.dao.implementation.database;

import worldofbooks.listingreportingsystem.dao.repository.database.MarketplaceDbRepository;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;

import javax.persistence.EntityManager;

public class MarketplaceDaoDb implements MarketplaceDbRepository {

    private EntityManager entityManager;

    public MarketplaceDaoDb(EntityManager entityManager) {
        this.setEntityManager(entityManager);
    }

    @Override
    public void saveMarketplace(Marketplace newMarketplace) {
        entityManager.getTransaction().begin();
        entityManager.persist(newMarketplace);
        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
