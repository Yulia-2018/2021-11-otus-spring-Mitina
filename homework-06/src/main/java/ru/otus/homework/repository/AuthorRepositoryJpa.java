package ru.otus.homework.repository;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Component
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> getByName(String name) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }
}
