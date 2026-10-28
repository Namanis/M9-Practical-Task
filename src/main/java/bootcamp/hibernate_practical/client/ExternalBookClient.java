package bootcamp.hibernate_practical.client;


import bootcamp.hibernate_practical.dto.LvBootcampBookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.time.Duration;
import java.util.List;

@Component
public class ExternalBookClient {
    private final RestClient restClient;
    private final String apiToken;

    public ExternalBookClient(@Value("${external-api.base-url}") String baseUrl,
                              @Value("${external-api.token}") String apiToken) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(2));
        requestFactory.setReadTimeout(Duration.ofSeconds(3));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();
        this.apiToken = apiToken;
    }

    public LvBootcampBookResponse getBook(Long id) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ExternalApiException("External API token is not configured (set EXTERNAL_API_TOKEN)");
        }

        try {
            LvBootcampBookResponse book = restClient.get()
                    .uri("/books/{id}", id)
                    .headers(headers -> headers.setBearerAuth(apiToken))
                    .retrieve()
                    .body(LvBootcampBookResponse.class);

            if (book == null) {
                throw new ExternalApiException("External API returned an empty response for book " + id);
            }
            return book;
        } catch (RestClientException e) {
            throw toExternalApiException(e);
        }
    }

    public List<LvBootcampBookResponse> getAllBooks() {
        if (apiToken == null || apiToken.isBlank()) {
            throw new ExternalApiException("External API token is not configured (set EXTERNAL_API_TOKEN)");
        }

        try {
            LvBootcampBookResponse[] books = restClient.get()
                    .uri("/books")
                    .headers(headers -> headers.setBearerAuth(apiToken))
                    .retrieve()
                    .body(LvBootcampBookResponse[].class);

            if (books == null) {
                throw new ExternalApiException("External API returned an empty response for the book list");
            }
            return List.of(books);
        } catch (RestClientException e) {
            throw toExternalApiException(e);
        }
    }

    private ExternalApiException toExternalApiException(RestClientException e) {
        if (e instanceof HttpClientErrorException exception) {
            int status = exception.getStatusCode().value();
            if (status == 401) {
                return new ExternalApiException("External API rejected our token (401 Unauthorized)", e);
            }
            if (status == 403) {
                return new ExternalApiException("External API denied access (403 Forbidden)", e);
            }
            return new ExternalApiException("Request rejected: " + exception.getStatusCode(), e);
        }
        if (e instanceof HttpServerErrorException exception) {
            return new ExternalApiException("External service error: " + exception.getStatusCode(), e);
        }
        if (e instanceof ResourceAccessException) {
            return new ExternalApiException("External service unreachable: " + e.getMessage(), e);
        }
        return new ExternalApiException("Invalid response from external service: " + e.getMessage(), e);
    }
}
