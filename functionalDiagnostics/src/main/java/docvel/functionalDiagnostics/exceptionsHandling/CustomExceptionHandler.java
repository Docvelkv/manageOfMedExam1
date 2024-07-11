package docvel.registry.exceptionsHandling;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalParameter.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorContent throwingIllegalParameter(IllegalParameter ex){
        return new ErrorContent(Map.of(
                "status", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage()));
    }

    @ExceptionHandler(EmptyList.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ErrorContent throwingEmptyList(EmptyList ex){
        return new ErrorContent(Map.of(
                "status", HttpStatus.EXPECTATION_FAILED.value(),
                "message", ex.getMessage()));
    }

    @ExceptionHandler(ServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorContent throwingServerError(ServerError ex){
        return new ErrorContent(Map.of(
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "message", ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorList throwingConstraintViolation(ConstraintViolationException ex){
        List<ErrorContent> errors = ex.getConstraintViolations()
                        .stream()
                        .map(error -> new ErrorContent(Map.of(
                                "status", HttpStatus.BAD_REQUEST.value(),
                                "beanClass", error.getRootBeanClass().getName(),
                                "field", error.getPropertyPath().toString(),
                                "value", error.getInvalidValue(),
                                "message", error.getMessage())))
                        .toList();
        return new ErrorList(errors);
    }
}