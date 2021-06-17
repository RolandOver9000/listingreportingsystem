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
        System.out.println(entityManager.find(Location.class, newLocation.getId()).toString());
        entityManager.getTransaction().commit();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
