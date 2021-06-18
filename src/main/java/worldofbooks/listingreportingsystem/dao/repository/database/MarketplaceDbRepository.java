package worldofbooks.listingreportingsystem.dao.repository.database;

import worldofbooks.listingreportingsystem.model.entity.Marketplace;

public interface MarketplaceDbRepository {
    void saveMarketplace(Marketplace newMarketplace);
}
