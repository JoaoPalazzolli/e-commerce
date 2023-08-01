package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Boolean existsByNome(String nome);

    @Transactional(readOnly = true)
    Optional<Estado> findByNome(String nome);
}
