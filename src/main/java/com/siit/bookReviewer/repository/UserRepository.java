package com.siit.bookReviewer.repository;

import com.siit.bookReviewer.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.security.InvalidParameterException;
import java.util.List;

public class UserRepository {

//  EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
//  EntityManager entityManager = emFactory.createEntityManager();
//
private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
        TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u", User.class);
        return typedQuery.getResultList();
    }

    public void addUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public User checkIfExists(String email, String password) {
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u WHERE u.email = :email AND u.password = :password", User.class);
            return typedQuery
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidParameterException("User is not found in the database");
        }
    }

    public User getUserByEmail(String email) {
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u WHERE u.email = :email", User.class);
            return typedQuery
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("User is not found in the database");
        }
    }

    public User getUserById(int userId) {
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u WHERE u.id = :userId", User.class);
            return typedQuery
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidParameterException("User is not found in the database userId");
        }
    }

}




