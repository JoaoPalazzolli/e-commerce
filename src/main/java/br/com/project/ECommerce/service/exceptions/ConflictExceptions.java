package br.com.project.ECommerce.service.exceptions;

import br.com.project.ECommerce.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictExceptions extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public ConflictExceptions() {
        super(ErrorMessages.CONFLICT);
    }

    public ConflictExceptions(String msg) {
        super(msg);
    }
}

