package worldofbooks.listingreportingsystem.dao.implementation.database;

import worldofbooks.listingreportingsystem.dao.repository.database.LocationDbRepository;
import worldofbooks.listingreportingsystem.model.entity.Location;

import javax.persistence.EntityManager;

public class LocationDbDaoDb implements LocationDbRepository {

    private EntityManager entityManager;

    public LocationDbDaoDb(EntityManager newEntityManager) {
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
