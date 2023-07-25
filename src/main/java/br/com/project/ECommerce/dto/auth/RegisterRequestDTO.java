package br.com.project.ECommerce.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
}
