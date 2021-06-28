package worldofbooks.listingreportingsystem.dao.implementation.database;

import worldofbooks.listingreportingsystem.dao.repository.database.ListingDbRepository;
import worldofbooks.listingreportingsystem.model.entity.Listing;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
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
    public Listing getBestListing() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Listing> criteriaQuery = criteriaBuilder.createQuery(Listing.class);
        Root<Listing> rootListing = criteriaQuery.from(Listing.class);
        try {
            criteriaQuery.select(rootListing);
            criteriaQuery.orderBy(criteriaBuilder.desc(
                    criteriaBuilder.prod(rootListing.get("listingPrice"), rootListing.get("quantity"))));
            TypedQuery<Listing> listing = entityManager.createQuery(criteriaQuery).setMaxResults(1);
            return listing.getSingleResult();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> getListingCountByMarketplaceNamePerMonth(String marketplaceName) {
        Map<String, String> resultMap;
        try {
            String sql = "SELECT listing.year, listing.month, COUNT(listing.id) FROM Listing listing " +
                    "WHERE listing.marketplace.id = " +
                    "(SELECT (marketplace.id) FROM Marketplace marketplace " +
                    "WHERE marketplace.marketplaceName = :marketplaceName) " +
                    "GROUP BY listing.year, listing.month";

            TypedQuery<Tuple> query = entityManager
                    .createQuery(sql, Tuple.class)
                    .setParameter("marketplaceName", marketplaceName);
            resultMap = query.getResultStream().collect(Collectors.toMap(
                    singleResult -> (singleResult.get(0).toString() + "-" + singleResult.get(1).toString()),
                    singleResult -> (singleResult.get(2).toString())
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
            String sql = "SELECT listing.year, listing.month, SUM(listing.listingPrice) FROM Listing listing " +
                    "WHERE listing.marketplace.id = " +
                    "(SELECT (marketplace.id) FROM Marketplace marketplace " +
                    "WHERE marketplace.marketplaceName = :marketplaceName) " +
                    "GROUP BY listing.year, listing.month";

            TypedQuery<Tuple> query = entityManager
                    .createQuery(sql, Tuple.class)
                    .setParameter("marketplaceName", marketplaceName);
            resultMap = query.getResultStream().collect(Collectors.toMap(
                    singleResult -> (singleResult.get(0).toString() + "-" + singleResult.get(1).toString()),
                    singleResult -> (singleResult.get(2).toString())
            ));
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
