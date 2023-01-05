package org.digitalbooks.service;

//import jakarta.transaction.Transactional;

import org.digitalbooks.entity.Book;
import org.digitalbooks.exception.BookServiceException;
import org.digitalbooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class BookService {

    @Autowired
    BookRepository bookRepository;

    private static Optional<Double> checkIfPriceIsANum(String price) {
        try {
            return Optional.of(Double.parseDouble(price));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    public Long addBook(Long authorId, Book book) {
        // IF Author has a book of same name, don't create that book
        List<Book> booksBySameAuthor = bookRepository.findByAuthorId(authorId);
        if (booksBySameAuthor.stream().map(Book::getTitle).anyMatch(book.getTitle()::equals)) {
            throw new BookServiceException(("Book Already Present, Cannot Add Again"));
        } else return bookRepository.save(book).getBookId();
    }

    public Long updateBook(Long authorId, Long bookId, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent() && authorId.equals(optionalBook.get().getAuthorId())) {
            Book savedBook = optionalBook.get();
            //Update Old Object
            savedBook.setBookId(bookId);
            savedBook.setTitle(book.getTitle());
            savedBook.setCategory(book.getCategory());
            savedBook.setPrice(book.getPrice());
            savedBook.setAuthorId(authorId);
            savedBook.setPublisher(book.getPublisher());
            savedBook.setPublishedDate(book.getPublishedDate());
            savedBook.setContent(book.getContent());
            savedBook.setBlocked(book.getBlocked());
            return bookId;
        } else throw new BookServiceException("Update Failed: Book with given ID not found");
    }

    public Long deleteBook(Long authorId, Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent() && authorId.equals(optionalBook.get().getAuthorId())) {
            bookRepository.deleteById(bookId);
            return bookId;
        } else throw new BookServiceException("Delete Failed: Book with given BookID & UserID not found");
    }

    public List<Book> retrieveAllBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public Long blockBook(Long authorId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent() && bookOptional.get().getAuthorId().equals(authorId)) {
            Book book = bookOptional.get();
            if (Boolean.TRUE.equals(book.getBlocked())) {
                throw new BookServiceException("Block Error: Book Already Blocked");
            }
            book.setBlocked(true);
            return bookId;
        } else throw new BookServiceException("Block Error: Book doesn't Exist");
    }

    public Long unBlockBook(Long authorId, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent() && bookOptional.get().getAuthorId().equals(authorId)) {
            Book book = bookOptional.get();
            if (Boolean.FALSE.equals(book.getBlocked())) {
                throw new BookServiceException("Block Error: Book Already Un-blocked");
            }
            book.setBlocked(false);
            return bookId;
        } else throw new BookServiceException("Block Error: Book doesn't Exist");
    }

    public List<Book> retrieveAllBooks() {
        return bookRepository.findByBlockedFalse();
    }

    public Book retrieveBookDataById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookServiceException("Read Failed: Book with given BookID not found"));
    }

    public List<Book> retrieveBooksByQuery(String category, String title, String price, String publisher) {

        List<Book> booksBySameCategory = filterBlockedBooks(bookRepository.findByCategoryLikeIgnoreCase(category));
        List<Book> booksBySameTitle = filterBlockedBooks(bookRepository.findByTitleContainsIgnoreCase(title));
        List<Book> booksBySamePublisher = filterBlockedBooks(bookRepository.findByPublisherContainsIgnoreCase(publisher));
        List<Book> books = Stream.of(booksBySameCategory, booksBySamePublisher, booksBySameTitle)
                .flatMap(List::stream).collect(Collectors.toList());
        Optional<Double> priceValue = checkIfPriceIsANum(price);
        if (priceValue.isPresent()) {
            List<Book> booksByLowerPrice = filterBlockedBooks(bookRepository.findByPriceLessThanEqual(priceValue.get()));
            books.addAll(booksByLowerPrice);
        }
        return books;
    }

    private List<Book> filterBlockedBooks(List<Book> books){
        return books.stream()
                .filter(Predicate.not(Book::getBlocked))
                .collect(Collectors.toList());
    }
}
