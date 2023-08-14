package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Transactional(readOnly = true)
    Optional<Categoria> findByNome(String nome);

    Boolean existsByNome(String nome);
}
