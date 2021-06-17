package worldofbooks.listingreportingsystem.dao.implementation;

import worldofbooks.listingreportingsystem.dao.repository.LocationRepository;
import worldofbooks.listingreportingsystem.model.entity.Location;

import javax.persistence.EntityManager;

public class LocationDAODB implements LocationRepository {

    private EntityManager entityManager;

    public LocationDAODB(EntityManager newEntityManager) {
        this.setEntityManager(newEntityManager);
    }

    @Override
    public void saveLocation(Location newLocation) {
        entityManager.getTransaction().begin();
        entityManager.persist(newLocation);
        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
