package ser.mil.charityevent.controller.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ser.mil.charityevent.domain.exception.DomainException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class HandleApiException {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionError> handleDomainException(DomainException e) {
        ExceptionError exceptionError = new ExceptionError(e.getMessage(), LocalDateTime.now(), e.getStatus().value());
        return ResponseEntity.status(e.getStatus()).body(exceptionError);
    }
}
