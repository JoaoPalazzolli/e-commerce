package br.com.project.ECommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuperBuilder
@Entity
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_generator")
    @TableGenerator(name = "user_generator", table = "user_gen")
    private Long id;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "conta_criada")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Builder.Default private final LocalDateTime contaCriada = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private Set<Endereco> enderecos;

    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set<String> telefones;
}
