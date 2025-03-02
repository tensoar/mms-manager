package ink.labrador.mmsmanager.integration.exception;

import org.springframework.http.HttpStatus;

public class ValidatorException extends BusinessException {
    public ValidatorException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
