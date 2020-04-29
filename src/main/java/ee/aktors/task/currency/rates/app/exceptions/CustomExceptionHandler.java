package ee.aktors.task.currency.rates.app.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Validation failed. " + ex.getBindingResult().getFieldErrors().stream()
                .map(e -> "Property " + e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; ")));
        exceptionDto.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDto.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDto> handleCustomException(CustomException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setStatus(ex.getHttpStatus().value());
        exceptionDto.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(exceptionDto, ex.getHttpStatus());
    }

}
