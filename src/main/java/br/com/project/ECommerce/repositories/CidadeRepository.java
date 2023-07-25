package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
