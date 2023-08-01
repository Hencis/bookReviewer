package com.siit.bookReviewer.repository;

import com.siit.bookReviewer.model.User;
import jakarta.persistence.*;

import java.security.InvalidParameterException;
import java.util.List;

public class UserRepository {

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

    public void deleteAccount(String email) {
        try {
            entityManager.getTransaction().begin();

            String userIdQuery = "SELECT u.id FROM User u WHERE u.email = :email";
            Integer userId = entityManager.createQuery(userIdQuery, Integer.class)
                    .setParameter("email", email)
                    .getSingleResult();

            String deleteBookReviewsQuery = "DELETE FROM BookReview br WHERE br.user.id = :userId";
            entityManager.createQuery(deleteBookReviewsQuery)
                    .setParameter("userId", userId)
                    .executeUpdate();

            String deleteUserQuery = "DELETE FROM User u WHERE u.email = :email";
            entityManager.createQuery(deleteUserQuery)
                    .setParameter("email", email)
                    .executeUpdate();

            entityManager.getTransaction().commit();
            entityManager.clear();

        } catch (NoResultException e) {
            throw new InvalidParameterException("User is not found in the database userid");
        }
    }

    public int updateUserInfo(String newFirstName, String newLastName, String email) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery(
                    "UPDATE User u SET u.firstName = :newFirstName, u.lastName = :newLastName" + " WHERE u.email = :email");
            query.setParameter("email", email);
            query.setParameter("newFirstName", newFirstName);
            query.setParameter("newLastName", newLastName);
            int rowsModified = query.executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
            return rowsModified;
        } catch (NoResultException e) {
            throw new InvalidParameterException("User is not found in the database userid");
        }
    }

}




