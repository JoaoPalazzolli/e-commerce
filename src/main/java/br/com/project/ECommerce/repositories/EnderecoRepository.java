package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
