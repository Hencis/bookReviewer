package com.siit.bookReviewer.repository;

import com.siit.bookReviewer.model.Book;
import com.siit.bookReviewer.model.BookReview;
import com.siit.bookReviewer.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.security.InvalidParameterException;
import java.util.List;

public class BookReviewRepository {

    private final EntityManager entityManager;

    public BookReviewRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<BookReview> findAll() {
        TypedQuery<BookReview> typedQuery = entityManager.createQuery("select br from BookReview br", BookReview.class);
        return typedQuery.getResultList();
    }

    public List<BookReview> getReviewsByBookId(int bookId) {
        try {
            TypedQuery<BookReview> typedQuery = entityManager.createQuery(
                    "SELECT br FROM BookReview br WHERE br.book.id= :bookId",
                    BookReview.class
            );
            typedQuery.setParameter("bookId", bookId);
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            throw new InvalidParameterException("Book is not found in the database ");
        }
    }

    public void add(User user,
                    Book book,
                    String reviewMessage,
                    Integer rating) {
        entityManager.getTransaction().begin();
        User emUser = entityManager.find(User.class, user.getId());
        Book emBook = entityManager.find(Book.class, book.getId());
        BookReview bookReview =
                new BookReview(
                        emUser,
                        emBook,
                        reviewMessage,
                        rating);
        entityManager.persist(bookReview);
        entityManager.getTransaction().commit();
    }

    public BookReview getReviewByUserAndBookId(Integer userId, Integer bookId) {
        try {
            TypedQuery<BookReview> typedQuery = entityManager.createQuery(
                    "SELECT br FROM BookReview br WHERE br.user.id = :userId AND br.book.id = :bookId",
                    BookReview.class
            );
            typedQuery.setParameter("userId", userId);
            typedQuery.setParameter("bookId", bookId);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidParameterException("BookReview is not found in the database!");
        }
    }


    public int deleteReview(Integer userId, Integer bookId) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery(
                    "DELETE FROM BookReview r WHERE r.user.id = :userId AND r.book.id = :bookId");
            query.setParameter("userId", userId);
            query.setParameter("bookId", bookId);
            int rowsModified = query.executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.flush();
            entityManager.clear();
            return rowsModified;
        } catch (NoResultException e) {
            throw new InvalidParameterException("User is not found in the database userid");
        }
    }

    public int updateReview(Integer userId, Integer bookId, String reviewMessage, Integer rating) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery(
                    "UPDATE BookReview r SET r.reviewMessage = :reviewMessage, r.rating = :rating" + " WHERE r.user.id = :userId AND r.book.id = :bookId");
            query.setParameter("userId", userId);
            query.setParameter("bookId", bookId);
            query.setParameter("reviewMessage", reviewMessage);
            query.setParameter("rating", rating);
            int rowsModified = query.executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
            return rowsModified;
        } catch (NoResultException e) {
            throw new InvalidParameterException("User is not found in the database userid");
        }
    }

    public Double calculateAverageRatingForBook(Integer bookId) {

            TypedQuery<Double> query = entityManager.createQuery(
                    "SELECT AVG(br.rating) FROM BookReview br WHERE br.book.id = :bookId",
                    Double.class
            );
            query.setParameter("bookId", bookId);

            try {
                Double averageRating = query.getSingleResult();
                if (averageRating != null) {
                    return averageRating;
                }
                else return 0.0;
                }
             catch (NoResultException e) {
                return 0.0;
            }
        }
    }

