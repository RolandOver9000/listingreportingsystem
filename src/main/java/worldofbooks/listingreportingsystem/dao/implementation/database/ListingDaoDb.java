package worldofbooks.listingreportingsystem.dao.implementation.database;

import worldofbooks.listingreportingsystem.dao.repository.database.ListingDbRepository;
import worldofbooks.listingreportingsystem.model.entity.Listing;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListingDaoDb implements ListingDbRepository {

    private EntityManager entityManager;

    public ListingDaoDb(EntityManager newEntityManager) {
        this.entityManager = newEntityManager;
    }

    @Override
    public void saveListing(Listing newListing) {
        entityManager.getTransaction().begin();
        entityManager.persist(newListing);
        entityManager.getTransaction().commit();
    }

    @Override
    public long getListingCount() {
        String sql = "SELECT COUNT(listing.id) FROM Listing listing";
        Query query = entityManager.createQuery(sql);
        return (long)query.getSingleResult();
    }

    @Override
    public long getListingCountByMarketplaceName(String marketplaceName) {
        try {
            String sql = "SELECT COUNT(listing.id) FROM Listing listing " +
                    "WHERE listing.marketplace.id = " +
                    "(SELECT (marketplace.id) FROM Marketplace marketplace " +
                    "WHERE marketplace.marketplaceName = :marketplaceName)";
            Query query = entityManager.createQuery(sql).setParameter("marketplaceName", marketplaceName);
            return (long) query.getSingleResult();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public BigDecimal getTotalListingPriceByMarketplaceName(String marketplaceName) {
        try {
            String sql = "SELECT SUM(listing.listingPrice) FROM Listing listing " +
                    "WHERE listing.marketplace.id = " +
                    "(SELECT (marketplace.id) FROM Marketplace marketplace " +
                    "WHERE marketplace.marketplaceName = :marketplaceName)";
            Query query = entityManager.createQuery(sql).setParameter("marketplaceName", marketplaceName);
            return new BigDecimal(String.valueOf(query.getSingleResult()));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return new BigDecimal(-1);
    }

    @Override
    public String getBestListingOwnerEmailAddress() {
        try {
            String sql = "SELECT owneremailaddress FROM listing " +
                    "ORDER BY (listingprice*quantity) DESC " +
                    "LIMIT 1";
            Query query = entityManager.createNativeQuery(sql);
            return query.getSingleResult().toString();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Map<String, String> getListingCountByMarketplaceNamePerMonth(String marketplaceName) {
        Map<String, String> resultMap;
        try {
            String sql = "SELECT EXTRACT(YEAR FROM uploadtime) as year, EXTRACT(MONTH FROM uploadtime) as month, COUNT(id) " +
                    "FROM listing " +
                    "WHERE marketplace_id = (SELECT id FROM marketplace WHERE marketplacename=:marketplaceName) " +
                    "GROUP BY EXTRACT(YEAR FROM uploadtime), EXTRACT(MONTH FROM uploadtime)";

            List<Tuple> query = entityManager.createNativeQuery(sql, Tuple.class)
                    .setParameter("marketplaceName", marketplaceName)
                    .getResultList();
            resultMap = query.stream().collect(Collectors.toMap(
                    singleResult -> ((int)Double.parseDouble(singleResult.get(0).toString()) + "-" +
                            (int)Double.parseDouble(singleResult.get(1).toString())),
                    singleResult -> (singleResult.get(2).toString()),
                    (singleResult1, singleResult2) -> singleResult1
            ));
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> getTotalListingPriceByMarketplaceNamePerMonth(String marketplaceName) {
        Map<String, String> resultMap;
        try {
            String sql = "SELECT EXTRACT(YEAR FROM uploadtime) as year, " +
                    "EXTRACT(MONTH FROM uploadtime) as month, " +
                    "SUM(listingprice) " +
                    "FROM listing " +
                    "WHERE marketplace_id = (SELECT id FROM marketplace WHERE marketplacename=:marketplaceName) " +
                    "GROUP BY EXTRACT(YEAR FROM uploadtime), EXTRACT(MONTH FROM uploadtime)";

            List<Tuple> query = entityManager.createNativeQuery(sql, Tuple.class)
                    .setParameter("marketplaceName", marketplaceName)
                    .getResultList();
            resultMap = query.stream().collect(Collectors.toMap(
                    singleResult -> ((int)Double.parseDouble(singleResult.get(0).toString()) + "-" +
                            (int)Double.parseDouble(singleResult.get(1).toString())),
                    singleResult -> (singleResult.get(2).toString()),
                    (singleResult1, singleResult2) -> singleResult1
            ));
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> getBestListingEmailAddressPerMonth() {
        Map<String, String> resultMap;
        try {
            String sql = "SELECT owneremailaddress, EXTRACT(YEAR FROM uploadtime) as year, " +
                    "EXTRACT(MONTH FROM uploadtime) as month, " +
                    "CAST(listingprice*quantity AS varchar) FROM listing " +
                    "INNER JOIN (SELECT CONCAT(EXTRACT(YEAR FROM uploadtime), " +
                    "EXTRACT(MONTH FROM uploadtime), (MAX(listingprice*quantity))) as result FROM listing " +
                    "GROUP BY EXTRACT(YEAR FROM uploadtime), EXTRACT(MONTH FROM uploadtime)) joinedListing " +
                    "ON joinedListing.result = CONCAT(EXTRACT(YEAR FROM uploadtime), " +
                    "CONCAT(EXTRACT(MONTH FROM uploadtime), CAST(listingprice*quantity AS varchar)))";

            List<Tuple> query = entityManager.createNativeQuery(sql, Tuple.class).getResultList();
            resultMap = query.stream().collect(Collectors.toMap(
                    singleResult -> ((int)Double.parseDouble(singleResult.get(1).toString()) + "-" +
                            (int)Double.parseDouble(singleResult.get(2).toString())),
                    singleResult -> (singleResult.get(0).toString()),
                    (singleResult1, singleResult2) -> singleResult1
            ));
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}