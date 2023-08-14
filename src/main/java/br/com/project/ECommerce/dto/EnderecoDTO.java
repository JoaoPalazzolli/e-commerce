package br.com.project.ECommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class EnderecoDTO extends RepresentationModel<EnderecoDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    @JsonProperty("cidade")
    private String cidadeNome;
    @JsonProperty("estado")
    private String cidadeEstadoNome;
}
