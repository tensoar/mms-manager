package ink.labrador.mmsmanager.integration.exception;

import ink.labrador.mmsmanager.integration.R;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
@Getter
@Slf4j
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(R.CODE code, String message) {
        super(message);
        this.code = code.getValue();
    }
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(String message) {
        super(message);
        this.code = R.CODE.FAILED.getValue();
    }
}
