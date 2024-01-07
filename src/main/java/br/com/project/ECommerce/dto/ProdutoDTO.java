package br.com.project.ECommerce.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<CategoriaDTO> categorias;
    private String imagem;
}
