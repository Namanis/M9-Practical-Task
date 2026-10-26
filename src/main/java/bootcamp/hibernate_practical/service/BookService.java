package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.enums.BorrowedStatus;
import bootcamp.hibernate_practical.exception.BookNotFoundException;
import bootcamp.hibernate_practical.exception.InvalidBookStateException;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getGenre(),
                request.getPublicationYear(),
                true,
                BorrowedStatus.IN_LIBRARY
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return mapToResponse(book);
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPublicationYear(request.getPublicationYear());
        book.setAvailable(request.isAvailable());
        updateAvailability(book, request.isAvailable());

        Book saved = bookRepository.save(book);

        return mapToResponse(saved);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);

    }

    public List<BookResponse> findByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BookResponse> findAvailableBooks() {
        return bookRepository.findByAvailableTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.isAvailable(),
                book.getBorrowedStatus()
        );
    }

    public List<BookResponse> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BookResponse borrowBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (book.getBorrowedStatus() == BorrowedStatus.UNAVAILABLE) {
            throw new InvalidBookStateException("Book with id " + id + " is not available");
        }
        if (book.getBorrowedStatus() == BorrowedStatus.BORROWED) {
            throw new InvalidBookStateException("Book with id " + id + " is already borrowed");
        }

        book.setBorrowedStatus(BorrowedStatus.BORROWED);
        return mapToResponse(bookRepository.save(book));
    }

    public BookResponse returnBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (book.getBorrowedStatus() != BorrowedStatus.BORROWED) {
            throw new InvalidBookStateException("Book with id " + id + " is not borrowed");
        }

        book.setBorrowedStatus(BorrowedStatus.IN_LIBRARY);
        return mapToResponse(bookRepository.save(book));
    }

    public List<BookResponse> findByBorrowedStatus(BorrowedStatus status) {
        return bookRepository.findByBorrowedStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BookResponse> findBooksInLibrary() {
        return bookRepository.findByBorrowedStatus(BorrowedStatus.IN_LIBRARY)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void updateAvailability(Book book, boolean available) {
        book.setAvailable(available);

        if (!available) {
            book.setBorrowedStatus(BorrowedStatus.UNAVAILABLE);
        } else if (book.getBorrowedStatus() == BorrowedStatus.UNAVAILABLE) {
            book.setBorrowedStatus(BorrowedStatus.IN_LIBRARY);
        }
    }

}
