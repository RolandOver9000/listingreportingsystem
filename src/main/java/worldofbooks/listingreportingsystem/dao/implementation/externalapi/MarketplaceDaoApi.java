package worldofbooks.listingreportingsystem.dao.implementation.externalapi;

import com.fasterxml.jackson.core.type.TypeReference;
import worldofbooks.listingreportingsystem.dao.repository.externalapi.MarketplaceApiRepository;
import worldofbooks.listingreportingsystem.model.entity.Marketplace;
import worldofbooks.listingreportingsystem.util.HttpRequestUtil;
import worldofbooks.listingreportingsystem.util.StringConverterUtil;

import java.util.List;

public class MarketplaceDaoApi implements MarketplaceApiRepository {
    private static final String MOCKAROO_API_KEY = System.getenv("MOCKAROO_API_KEY");
    private static final String MARKETPLACE_ENDPOINT_URL = "https://my.api.mockaroo.com/marketplace?key=" + MOCKAROO_API_KEY;
    private List<Marketplace> fetchedMarketplaces;


    @Override
    public List<Marketplace> getAllMarketplaces() {
        if(fetchedMarketplaces == null || fetchedMarketplaces.isEmpty()) {
            String fetchedData = this.fetchData();
            this.fetchedMarketplaces = this.getMarketplaceListFromJson(fetchedData);
        }
        return this.fetchedMarketplaces;
    }

    @Override
    public Marketplace getMarketplaceById(int searchedId) {
        return fetchedMarketplaces.stream()
                .filter(marketplace -> marketplace.getId() == searchedId)
                .findFirst()
                .orElse(null);
    }

    private List<Marketplace> getMarketplaceListFromJson(String json) {
        return StringConverterUtil.convertJsonStringToGivenType(
                json,
                new TypeReference<List<Marketplace>>() {});
    }

    public String fetchData() {
        return HttpRequestUtil.sendGetRequest(MARKETPLACE_ENDPOINT_URL);
    }
}
