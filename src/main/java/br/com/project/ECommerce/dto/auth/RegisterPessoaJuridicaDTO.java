package br.com.project.ECommerce.dto.auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterPessoaJuridicaDTO extends RegisterUserDTO implements Serializable {

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;

}
