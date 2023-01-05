package org.digitalbooks.repository;

import org.digitalbooks.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainsIgnoreCase(String title);
    List<Book> findByPriceLessThan(Double price);

    List<Book> findByPriceLessThanEqual(Double price);

    List<Book> findByCategoryLikeIgnoreCase(String category);

    List<Book> findByPublisherContainsIgnoreCase(String publisher);

    List<Book> findByTitleLikeIgnoreCase(String title);

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByBlockedFalse();

    List<Book> findByPublisherLikeIgnoreCase(String publisher);

    List<Book> findByPublishedDateAfter(LocalDate publishedDate);

}
