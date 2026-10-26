package bootcamp.hibernate_practical.dto;

import bootcamp.hibernate_practical.enums.BorrowedStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateBookRequest {

    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;

    private String genre;
    @Min(value = 1, message = "Publication year must be at least 1")
    private int publicationYear;

    private boolean available;
    private BorrowedStatus borrowedStatus;

    @AssertTrue(message = "Publication year cannot be in the future")
    public boolean isPublicationYearValid() {
        return publicationYear <= LocalDate.now().getYear();
    }
}
