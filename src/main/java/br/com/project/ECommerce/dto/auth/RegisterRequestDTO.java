package br.com.project.ECommerce.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private String nomeCompleto;
    private String cpf;
    private String telefones;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

}
