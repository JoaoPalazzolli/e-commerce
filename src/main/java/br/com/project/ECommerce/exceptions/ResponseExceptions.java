package br.com.project.ECommerce.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ResponseExceptions {

    private LocalDateTime timestamp;
    private String message;
    private String description;
}
