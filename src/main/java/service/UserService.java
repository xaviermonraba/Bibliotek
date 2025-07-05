package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotekPU");

    public UserService() {

    }

    public User getUserById(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        EntityManager em = this.getEntityManager();

        try {
            return em.find(User.class, userName);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        User existingUser = em.find(User.class, user.getUsername());
        if (existingUser != null) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("User already exists: " + user.getUsername());
        }
        em.persist(user);
        em.getTransaction().commit();

    }

    public void updateUserPenaltyDays(String userId, int penaltyDays) {
        User user = getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        if (penaltyDays < 0) {
            throw new IllegalArgumentException("Penalty days cannot be negative");
        }
        user.setPoints(user.getPoints() - penaltyDays);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
