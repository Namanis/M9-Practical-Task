package bootcamp.hibernate_practical.dto;

import java.math.BigDecimal;

public record LvBootcampBookResponse(Long id,
                                     String title,
                                     String author,
                                     String genre,
                                     BigDecimal price,
                                     String isbn,
                                     Integer publishedYear) {
}
