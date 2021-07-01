package worldofbooks.listingreportingsystem.service.report;

import org.json.JSONObject;
import worldofbooks.listingreportingsystem.dao.repository.database.ListingDbRepository;
import worldofbooks.listingreportingsystem.service.FtpService;
import worldofbooks.listingreportingsystem.util.FileHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListingReportService {
    private ListingDbRepository listingDbRepository;
    private FtpService ftpService;
    private final static String EBAY_DB_MARKETPLACE_NAME = "EBAY";
    private final static String AMAZON_DB_MARKETPLACE_NAME = "AMAZON";
    private static final String LISTING_REPORT_FILE_PATH = "src/main/reports/";

    public ListingReportService(ListingDbRepository newListingDbRepository,
                                FtpService newFtpService) {
        this.listingDbRepository = newListingDbRepository;
        this.ftpService = newFtpService;
    }

    public void generateListingReport_thenSaveToFile_thenUploadReportToFtpServer() {
        JSONObject generatedJsonReport = this.generateListingReport();

        String savedReportFileName = this.saveReportToFile(generatedJsonReport);
        String targetFtpDirectory = "/reports";
        this.ftpService.uploadFile(
                LISTING_REPORT_FILE_PATH + savedReportFileName,
                savedReportFileName,
                targetFtpDirectory);
    }

    private String saveReportToFile(JSONObject generatedJsonReport) {
        return FileHandler.generateJsonFileFromString_thenSaveFileToDirectory(
                generatedJsonReport.toString(2),
                LISTING_REPORT_FILE_PATH);
    }

    private JSONObject generateListingReport() {
        Map<String, String> reports = new LinkedHashMap<>();
        Map<String, Map<String, String>> monthlyReports = new HashMap<>();

        reports.put("Total listing count", this.getTotalListingCount());
        reports.put("Total eBay listing count",
                this.getTotalListingCountByMarketplaceName(EBAY_DB_MARKETPLACE_NAME));

        reports.put("Total eBay listing price",
                this.getTotalListingPriceByMarketplaceName(EBAY_DB_MARKETPLACE_NAME));

        reports.put("Average eBay listing price",
                this.countAverageValue(
                        reports.get("Total eBay listing count"),
                        reports.get("Total eBay listing price")));

        reports.put("Total Amazon listing count",
                this.getTotalListingCountByMarketplaceName(AMAZON_DB_MARKETPLACE_NAME));

        reports.put("Total Amazon listing price",
                this.getTotalListingPriceByMarketplaceName(AMAZON_DB_MARKETPLACE_NAME));

        reports.put("Average Amazon listing price",
                this.countAverageValue(
                        reports.get("Total Amazon listing count"),
                        reports.get("Total Amazon listing price")));

        reports.put("Best lister email address", this.getBestListingOwnerEmail());
        monthlyReports.put("Total eBay listing count per month",
                this.listingDbRepository.getListingCountByMarketplaceNamePerMonth(EBAY_DB_MARKETPLACE_NAME));

        monthlyReports.put("Total eBay listing price per month",
                this.listingDbRepository.getTotalListingPriceByMarketplaceNamePerMonth(EBAY_DB_MARKETPLACE_NAME));

        monthlyReports.put("Average eBay listing price per month",
                this.getAverageListingPricePerMonthFromJsonString(
                        monthlyReports.get("Total eBay listing count per month"),
                        monthlyReports.get("Total eBay listing price per month"))
                );

        monthlyReports.put("Total Amazon listing count per month",
                this.listingDbRepository.getListingCountByMarketplaceNamePerMonth(AMAZON_DB_MARKETPLACE_NAME));

        monthlyReports.put("Total Amazon listing price per month",
                this.listingDbRepository.getTotalListingPriceByMarketplaceNamePerMonth(AMAZON_DB_MARKETPLACE_NAME));

        monthlyReports.put("Average Amazon listing price per month",
                this.getAverageListingPricePerMonthFromJsonString(
                        monthlyReports.get("Total Amazon listing count per month"),
                        monthlyReports.get("Total Amazon listing price per month")));

        monthlyReports.put("Best lister email address of the month",
                this.listingDbRepository.getBestListingEmailAddressPerMonth());

        JSONObject jsonReports = this.generateJSONObjectFromMap(reports);
        JSONObject jsonMonthlyReports = this.generateJSONObjectFromMap(monthlyReports);
        jsonReports.append("Monthly reports", jsonMonthlyReports);

        return jsonReports;
    }

    private <K, V> JSONObject generateJSONObjectFromMap(Map<K, V> map) {
        return new JSONObject(map);
    }

    private Map<String, String> getAverageListingPricePerMonthFromJsonString(Map<String, String> count, Map<String, String> sum) {
        Map<String, String> resultMap = new HashMap<>();
        System.out.println(count);

        for (Map.Entry<String, String> sumMapElement : sum.entrySet()) {
            BigDecimal sumValue = new BigDecimal(sumMapElement.getValue());
            BigDecimal countValue = new BigDecimal(count.get(sumMapElement.getKey()));
            resultMap.put(sumMapElement.getKey(),
                            sumValue.divide(countValue, 2, RoundingMode.DOWN).toString());
        }
        return resultMap;
    }

    private String getBestListingOwnerEmail() {
        return this.listingDbRepository.getBestListingOwnerEmailAddress();
    }

    private String countAverageValue(String totalCount, String sum) {
        return new BigDecimal(sum).divide(new BigDecimal(totalCount), 2, RoundingMode.DOWN).toString();
    }

    private String getTotalListingPriceByMarketplaceName(String marketplaceName) {
        return String.valueOf(this.listingDbRepository.getTotalListingPriceByMarketplaceName(marketplaceName));
    }

    private String getTotalListingCountByMarketplaceName(String marketplaceName) {
        return String.valueOf(this.listingDbRepository.getListingCountByMarketplaceName(marketplaceName));
    }

    private String getTotalListingCount() {
        return String.valueOf(this.listingDbRepository.getListingCount());
    }
}