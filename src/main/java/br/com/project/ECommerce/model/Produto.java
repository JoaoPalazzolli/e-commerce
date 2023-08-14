package br.com.project.ECommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private Integer estoque;

    @Column(nullable = false)
    private LocalDateTime publicado;

    @Column(nullable = false)
    private String imagem;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "produto_categoria",
            joinColumns = {@JoinColumn(name = "produto_id")},
            inverseJoinColumns = {@JoinColumn(name = "categoria_id")})
    private Set<Categoria> categorias;

}
