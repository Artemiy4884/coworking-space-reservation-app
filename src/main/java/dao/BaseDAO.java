package dao;

import jakarta.persistence.EntityManager;
import utils.JPAUtils;

public abstract class BaseDAO {
    protected static EntityManager entityManager() {
        return JPAUtils.getEntityManager();
    }
}
