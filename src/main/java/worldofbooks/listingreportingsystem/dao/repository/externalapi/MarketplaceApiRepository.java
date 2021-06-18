package worldofbooks.listingreportingsystem.dao.repository.externalapi;

import worldofbooks.listingreportingsystem.model.entity.Marketplace;

import java.util.List;

public interface MarketplaceApiRepository {
    List<Marketplace> getAllMarketplaces();
    Marketplace getMarketplaceById(int searchedId);
}
