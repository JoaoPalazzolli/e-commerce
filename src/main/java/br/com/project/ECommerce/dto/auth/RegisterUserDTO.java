package br.com.project.ECommerce.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class RegisterUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private String nomeCompleto;
    private Set<String> telefones;
}
