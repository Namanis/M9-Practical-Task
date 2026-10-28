package bootcamp.hibernate_practical.controller;

import bootcamp.hibernate_practical.client.ExternalApiException;
import bootcamp.hibernate_practical.client.ExternalBookClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExternalBookPageController {

    private final ExternalBookClient externalBookClient;

    public ExternalBookPageController(ExternalBookClient externalBookClient) {
        this.externalBookClient = externalBookClient;
    }

    @GetMapping("/external-books")
    public String externalBooks(@RequestParam(required = false) Long id, Model model) {
        try {
            if (id == null) {
                model.addAttribute("books", externalBookClient.getAllBooks());
            } else {
                model.addAttribute("book", externalBookClient.getBook(id));
            }
        } catch (ExternalApiException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "external-books";
    }
}