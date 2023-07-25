package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Optional<Estado> findByNome(String nome);

}
