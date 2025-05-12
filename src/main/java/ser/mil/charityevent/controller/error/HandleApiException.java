package ser.mil.charityevent.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionError> handleIllegalArgumentException(IllegalArgumentException e) {
        String message = e.getMessage();
        if (message != null && message.contains("No enum constant")) {
            message = "Invalid value for one of the fields.";
        }
        ExceptionError error = new ExceptionError(message, LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionError> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        String message = "Invalid request payload.";
        Throwable cause = e.getCause();
        if (cause != null && cause.getMessage() != null && cause.getMessage().contains("Currency")) {
            message = "Unsupported currency provided. Supported values: PLN, EURO, USD.";
        }
        ExceptionError error = new ExceptionError(message, LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionError> handleNullPointerException(NullPointerException e) {
        String message = e.getMessage() != null ? e.getMessage() : "A required field was null.";
        ExceptionError error = new ExceptionError(message, LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
