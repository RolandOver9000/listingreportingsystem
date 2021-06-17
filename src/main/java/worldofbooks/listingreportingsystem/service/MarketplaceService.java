package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.externalapi.MarketplaceAPIDTO;
import worldofbooks.listingreportingsystem.dao.repository.MarketplaceRepository;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;

public class MarketplaceService {

    private MarketplaceRepository marketplaceRepository;
    private List<Marketplace> fetchedMarketplaces;
    private MarketplaceAPIDTO marketplaceAPIDTO;

    public MarketplaceService(MarketplaceRepository newMarketplaceRepository,
                              MarketplaceAPIDTO newMarketplaceAPIDTO) {

        this.setMarketplaceRepository(newMarketplaceRepository);
        this.setMarketplaceAPIDTO(newMarketplaceAPIDTO);
    }

    public void fetchAndSaveData() {
        String fetchMarketplaceData = this.marketplaceAPIDTO.fetchData();
        this.setFetchedMarketplaces(this.getMarketplaceListFromJson(fetchMarketplaceData));
        this.saveMarketplaceListElements(this.getFetchedMarketplaces());
    }

    public List<Marketplace> getMarketplaceListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<Marketplace>>() {});
    }

    public void saveMarketplaceListElements(List<Marketplace> marketplaces){
        for(Marketplace marketplace : marketplaces){
            marketplaceRepository.saveMarketplace(marketplace);
        }
    }

    public Marketplace getMarketplaceByIdFromFetchedData(int searchedId) {
        return fetchedMarketplaces.stream()
                .filter(marketplace -> marketplace.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }

    public void setMarketplaceRepository(MarketplaceRepository marketplaceRepository) {
        this.marketplaceRepository = marketplaceRepository;
    }

    public void setFetchedMarketplaces(List<Marketplace> fetchedMarketplaces) {
        this.fetchedMarketplaces = fetchedMarketplaces;
    }

    public List<Marketplace> getFetchedMarketplaces() {
        return fetchedMarketplaces;
    }

    public void setMarketplaceAPIDTO(MarketplaceAPIDTO marketplaceAPIDTO) {
        this.marketplaceAPIDTO = marketplaceAPIDTO;
    }
}
