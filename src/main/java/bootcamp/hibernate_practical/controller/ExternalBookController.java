package bootcamp.hibernate_practical.controller;

import bootcamp.hibernate_practical.client.ExternalBookClient;
import bootcamp.hibernate_practical.dto.LvBootcampBookResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external/books")
public class ExternalBookController {

    private final ExternalBookClient externalBookClient;

    public ExternalBookController(ExternalBookClient externalBookClient) {
        this.externalBookClient = externalBookClient;
    }

    @GetMapping("/{id}")
    public LvBootcampBookResponse getExternalBook(@PathVariable Long id) {
        return externalBookClient.getBook(id);
    }
}
