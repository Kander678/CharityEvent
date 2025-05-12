package ser.mil.charityevent.domain.exception;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public DomainException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
