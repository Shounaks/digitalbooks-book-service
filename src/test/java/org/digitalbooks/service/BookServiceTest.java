package org.digitalbooks.service;

import org.digitalbooks.entity.Book;
import org.digitalbooks.exception.BookServiceException;
import org.digitalbooks.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService = new BookService();

    @Test
    void addBook_ValidTest(){
        //Expected
        Long authorId = 1L;
        Book book = Book.builder().bookId(1L).title("ABCDE").build();

        //Mocks
        Mockito.when(bookRepository.findByAuthorId(any())).thenReturn(List.of());
        Mockito.when(bookRepository.save(any())).thenReturn(book);

        //When
        Long bookId = bookService.addBook(authorId, book);

        //Then
        assertThat(bookId).isEqualTo(1L);
    }

    @Test
    void addBook_InvalidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).title("ABCDE").build();

        //Mocks
        Mockito.when(bookRepository.findByAuthorId(any())).thenReturn(List.of(book));

        //Then
        assertThrows(BookServiceException.class,()->bookService.addBook(authorId, book));
        assertThat(bookId).isEqualTo(1L);

    }

    @Test
    void retrieveBookDataById_ValidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).authorId(authorId).title("ABCDE").build();

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //When
        Book newBook = bookService.retrieveBookDataById(bookId);

        //Then
        assertThat(newBook).isEqualTo(book);
    }

    @Test
    void retrieveBookDataById_InValidTest(){
        //Expected
        Long bookId = 1L;

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.empty());

        //Then
        assertThrows(BookServiceException.class,()-> bookService.retrieveBookDataById(bookId));
    }

    @Test
    void updateBook_ValidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).authorId(authorId).title("ABCDE").build();

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //When
        Long newBookId = bookService.updateBook(authorId,bookId, book);

        //Then
        assertThat(newBookId).isEqualTo(1L);
    }

    @Test
    void updateBook_InvalidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).title("ABCDE").build();

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //Then
        assertThrows(BookServiceException.class,()->bookService.updateBook(authorId,bookId, book));
    }

    @Test
    void unBlockBook_ValidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).authorId(authorId).title("ABCDE").blocked(true).build();

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //When
        Long newBookId = bookService.unBlockBook(authorId, bookId);
        //Then
        assertThat(newBookId).isEqualTo(bookId);
    }

    @Test
    void blockBook_ValidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).authorId(authorId).title("ABCDE").blocked(false).build();

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //When
        Long newBookId = bookService.blockBook(authorId, bookId);
        //Then
        assertThat(newBookId).isEqualTo(bookId);
    }

    @Test
    void unBlockBook_InValidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).authorId(authorId).title("ABCDE").blocked(false).build();

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //Then
        assertThrows(BookServiceException.class,()->bookService.unBlockBook(authorId, bookId));
    }

    @Test
    void blockBook_InValidTest(){
        //Expected
        Long authorId = 1L;
        Long bookId = 1L;
        Book book = Book.builder().bookId(bookId).authorId(authorId).title("ABCDE").blocked(false).build();

        //Mocks
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //Then
        assertThrows(BookServiceException.class,()->bookService.unBlockBook(authorId, bookId));
    }
}