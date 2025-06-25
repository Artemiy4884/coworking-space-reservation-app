package dao;

import entities.CoworkingSpace;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoworkingSpaceDAO extends BaseDAO {

    public static Map<Integer, CoworkingSpace> getAllSpaces() {
        try (EntityManager entityManager = entityManager()) {
            List<CoworkingSpace> coworkingSpaces = entityManager.createQuery("FROM CoworkingSpace", CoworkingSpace.class).getResultList();
            Map<Integer, CoworkingSpace> spaces = new HashMap<>();
            for (CoworkingSpace space : coworkingSpaces) {
                spaces.put(space.getId(), space);
            }
            return spaces;
        }
    }


    public static void addSpace(CoworkingSpace space) {
        try (EntityManager entityManager = entityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(space);
            entityManager.getTransaction().commit();
        }
    }

    public static boolean removeSpace(int id) {
        try (EntityManager entityManager = entityManager()) {
            CoworkingSpace space = entityManager.find(CoworkingSpace.class, id);
            if (space == null) return false;

            entityManager.getTransaction().begin();
            entityManager.remove(space);
            entityManager.getTransaction().commit();
            return true;
        }
    }

    public static void updateAvailability(int id, boolean available) {
        try (EntityManager entityManager = entityManager()) {
            CoworkingSpace space = entityManager.find(CoworkingSpace.class, id);
            if (space != null) {
                entityManager.getTransaction().begin();
                space.setAvailable(available);
                entityManager.getTransaction().commit();
            }
        }
    }
}
