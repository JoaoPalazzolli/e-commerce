package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUserEmail(String email);
}
