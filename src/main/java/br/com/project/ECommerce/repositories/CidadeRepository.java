package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Boolean existsByNomeAndEstadoNome(String nome, String estadoNome);

    @Transactional(readOnly = true)
    Optional<Cidade> findByNomeAndEstadoNome(String nome, String estadoNome);
}
