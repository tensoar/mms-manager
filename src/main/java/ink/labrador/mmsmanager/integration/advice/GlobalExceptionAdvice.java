package ink.labrador.mmsmanager.integration.advice;

import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.exception.BusinessException;
import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public R<String> handleBusinessException(BusinessException e) {
        System.out.println("e = " + e);
        return R.fail(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpServerErrorException.class)
    public R<String> handleHttpServerErrorException(HttpServerErrorException e) {
        System.out.println("e = " + e);
        String message = e.getMessage();
        String codeStr = String.valueOf(e.getStatusCode().value());
        if (message.startsWith(codeStr)) {
            message = message.substring(codeStr.length()).trim();
        }
        return R.fail(e.getStatusCode().value(), message);
    }

    @ResponseBody
    @ExceptionHandler(FormValueTransformerException.class)
    public R<String> handleFormValueTransformerException(FormValueTransformerException e) {
        return R.fail(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R<String> handleDefaultException(Exception e) {
        e.printStackTrace();
        return R.fail("内部错误");
    }
}
