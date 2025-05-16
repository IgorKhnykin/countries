package guru.ga.country.service;

import guru.ga.country.config.AppConfig;
import guru.ga.country.exception.CountryNotFoundException;
import guru.ga.country.model.ErrorJson;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final AppConfig appConfig = new AppConfig();
    private final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorJson> handleCountryNotFoundException(CountryNotFoundException ex, HttpServletRequest request) {
        LOG.error(request.getRequestURI(), ex);
        return new ResponseEntity<>(
                new ErrorJson(
                        appConfig.apiVersion,
                        HttpStatus.NOT_FOUND.value(),
                        "Country not found",
                        request.getRequestURI(),
                        ex.getMessage()),
                HttpStatus.NOT_FOUND);

    }
}
