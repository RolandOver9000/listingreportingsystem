package worldofbooks.listingreportingsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.repository.MarketplaceRepository;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;

public class MarketplaceService {

    private static final String MARKETPLACE_ENDPOINT_URL = "https://my.api.mockaroo.com/marketplace?key=63304c70";
    private HttpRequestService httpRequestService;
    private MarketplaceRepository marketplaceRepository;
    private List<Marketplace> fetchedMarketplaces;

    public MarketplaceService(HttpRequestService newHttpRequestService,
                              MarketplaceRepository newMarketplaceRepository) {

        this.setHttpRequestService(newHttpRequestService);
        this.setMarketplaceRepository(newMarketplaceRepository);
    }

    public void fetchAndSaveData() {
        String fetchMarketplaceData = this.fetchMarketplaceData();
        this.setFetchedMarketplaces(this.getMarketplaceListFromJson(fetchMarketplaceData));
        this.saveMarketplaceListElements(this.getFetchedMarketplaces());
    }

    public String fetchMarketplaceData() {
        return this.httpRequestService.sendGetRequest(MARKETPLACE_ENDPOINT_URL);
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

    public void setHttpRequestService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
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
}
