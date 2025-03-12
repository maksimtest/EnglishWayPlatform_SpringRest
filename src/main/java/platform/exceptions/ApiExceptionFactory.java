package platform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public enum ApiExceptionFactory {
    // registration
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST),
    UNKNOWN_PROBLEM_WITH_REGISTRATION(HttpStatus.BAD_REQUEST),
    REMEMBER_SAVE_FUNCTION(HttpStatus.BAD_REQUEST),
    UNKNOWN_PROBLEM_WITH_ACTIVATION(HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_IS_INVALID(HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_IS_EXPIRED(HttpStatus.BAD_REQUEST),
    UNKNOWN_EMAIL_PROBLEM(HttpStatus.BAD_REQUEST),
    // remember password by email,
    REMEMBER_EMAIL_IS_INVALID(HttpStatus.BAD_REQUEST),
    UNKNOWN_PROBLEM_WITH_REMEMBER_PASSWORD(HttpStatus.BAD_REQUEST),
    UNSUCCESSFUL_ATTEMPT_TO_CHANGE_PASSWORD(HttpStatus.BAD_REQUEST),
    // auth
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED);

    ////////////////////////////////////////////////////////////
    private ApiExceptionFactory(HttpStatus status){
        code = status;
    }
    public static ResponseEntity<ApiExceptionFactory> get(ApiExceptionFactory error){
        return new ResponseEntity <ApiExceptionFactory>(error, error.code);
    }
    final HttpStatus code;

}
