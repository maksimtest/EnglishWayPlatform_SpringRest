package platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorConstants {
    // registration
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
    USERNAME_HAS_WRONG_FORMAT(HttpStatus.BAD_REQUEST);

    private ErrorConstants(HttpStatus status){
        code = status.value();
    }
    final int code;
}
