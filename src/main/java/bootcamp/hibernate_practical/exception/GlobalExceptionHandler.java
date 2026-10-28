package bootcamp.hibernate_practical.exception;

import bootcamp.hibernate_practical.client.ExternalApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ErrorResponse handleBookNotFound(BookNotFoundException exception) {
        HttpStatus status = exception.getClass().getAnnotation(ResponseStatus.class).value();
        return ErrorResponse.create(exception, status, exception.getMessage());
    }

    @ExceptionHandler(InvalidBookStateException.class)
    public ErrorResponse handleBookNotFound(InvalidBookStateException exception) {
        HttpStatus status = exception.getClass().getAnnotation(ResponseStatus.class).value();
        return ErrorResponse.create(exception, status, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        ErrorResponse response = ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, "Validation failed");
        response.getBody().setProperty("errors", errors);
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleBadJson(HttpMessageNotReadableException exception) {
        return ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, "Request body is invalid or malformed");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String message = "Invalid value '" + exception.getValue() + "' for parameter '" + exception.getName() + "'";
        if (exception.getRequiredType() != null && exception.getRequiredType().isEnum()) {
            String allowed = Arrays.stream(exception.getRequiredType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            message += ". Allowed values: " + allowed;
        }
        return ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ErrorResponse handleExternalApi(ExternalApiException exception) {
        return ErrorResponse.create(exception, HttpStatus.BAD_GATEWAY, exception.getMessage());
    }

}
