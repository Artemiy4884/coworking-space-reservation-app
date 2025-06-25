package dao;

import entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDAO extends BaseDAO{

    public static List<User> getAllUsers() {
        EntityManager entityManager = entityManager();
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static void addUser(User user) {
        EntityManager entityManager = entityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }
}
