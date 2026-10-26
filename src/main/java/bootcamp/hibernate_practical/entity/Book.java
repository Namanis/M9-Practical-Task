package bootcamp.hibernate_practical.entity;

import bootcamp.hibernate_practical.enums.BorrowedStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;

    private String genre;
    private int publicationYear;
    private boolean available;

    @Enumerated(EnumType.STRING)
    private BorrowedStatus borrowedStatus;

    public Book(String title, String author, String genre, int publicationYear, boolean available, BorrowedStatus borrowedStatus) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.available = available;
        this.borrowedStatus = borrowedStatus;
    }
}
