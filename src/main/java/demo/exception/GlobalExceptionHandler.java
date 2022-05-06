package demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
        System.out.println("global exception called");

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND, request.getDescription(false));
        System.out.println("resource not found exception called");

        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRequest(HttpMessageNotReadableException exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), "wrong input data", HttpStatus.BAD_REQUEST, request.getDescription(false));
        System.out.println("bad request exception called");

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler()
//    public ResponseEntity<?> handleEmptyInput()
}
