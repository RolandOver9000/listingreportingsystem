package worldofbooks.listingreportingsystem.service;

import worldofbooks.listingreportingsystem.dao.repository.database.MarketplaceDbRepository;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.MarketplaceApiRepository;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;

import java.util.List;

public class MarketplaceService {

    private MarketplaceDbRepository marketplaceDBRepository;
    private MarketplaceApiRepository marketplaceAPIRepository;

    public MarketplaceService(MarketplaceDbRepository newMarketplaceDbRepository,
                              MarketplaceApiRepository newMarketplaceApiRepository) {

        this.marketplaceDBRepository = newMarketplaceDbRepository;
        this.marketplaceAPIRepository = newMarketplaceApiRepository;
    }

    public void fetchAndSaveData() {
        this.saveMarketplaceListToDB(this.marketplaceAPIRepository.getAllMarketplaces());
    }

    public void saveMarketplaceListToDB(List<Marketplace> marketplaces){
        for(Marketplace marketplace : marketplaces){
            marketplaceDBRepository.saveMarketplace(marketplace);
        }
    }

    public Marketplace getMarketplaceByIdFromAPI(int id) {
        return this.marketplaceAPIRepository.getMarketplaceById(id);
    }

    public void setMarketplaceDBRepository(MarketplaceDbRepository marketplaceDBRepository) {
        this.marketplaceDBRepository = marketplaceDBRepository;
    }

    public void setMarketplaceAPIRepository(MarketplaceApiRepository marketplaceAPIRepository) {
        this.marketplaceAPIRepository = marketplaceAPIRepository;
    }
}
