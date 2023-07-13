package com.siit.bookReviewer.repository;

import com.siit.bookReviewer.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.security.InvalidParameterException;
import java.util.List;


public class BookRepository {

//    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
//   EntityManager entityManager = emFactory.createEntityManager();

    private final EntityManager entityManager;

    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> findAll() {
        TypedQuery<Book> typedQuery = entityManager.createQuery("select b from Book b", Book.class);
        List<Book> books = typedQuery.getResultList();
        return books;
    }

    public Book getByTitle(String title) {
        try {
            TypedQuery<Book> typedQuery = entityManager.createQuery("select b from Book b WHERE b.title = :title", Book.class);
            return typedQuery
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidParameterException("Book is not found in the database");
        }
    }

    public Book getBookById(int bookId) {
        try {
            TypedQuery<Book> typedQuery = entityManager.createQuery("select b from Book b WHERE b.id = :bookId", Book.class);
            return typedQuery
                    .setParameter("bookId", bookId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidParameterException("Book is not found in the database bookId");
        }
    }
}
