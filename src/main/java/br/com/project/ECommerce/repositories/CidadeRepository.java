package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Boolean existsByNomeAndEstadoNome(String nome, String estadoNome);

    Optional<Cidade> findByNome(String nome);
}
