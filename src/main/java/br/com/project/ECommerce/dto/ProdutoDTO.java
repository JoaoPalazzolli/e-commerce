package br.com.project.ECommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;
    private String marca;
    private Double preco;
    private Integer estoque;
    private LocalDateTime publicado;
    private Set<CategoriaDTO> categorias = new HashSet<>();
    private String imagem;
}
