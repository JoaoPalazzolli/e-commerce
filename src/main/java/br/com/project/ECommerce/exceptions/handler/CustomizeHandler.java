package br.com.project.ECommerce.exceptions.handler;

import br.com.project.ECommerce.service.exceptions.ConflictExceptions;
import br.com.project.ECommerce.exceptions.ResponseExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class CustomizeHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseExceptions> allExceptionsHandler(Exception ex, WebRequest request){

        var responseException = new ResponseExceptions(LocalDateTime.now(), ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity<>(responseException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictExceptions.class)
    public ResponseEntity<ResponseExceptions> conflictExceptionsHandler(Exception ex, WebRequest request){

        var responseException = new ResponseExceptions(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(responseException, HttpStatus.CONFLICT);
    }

}
