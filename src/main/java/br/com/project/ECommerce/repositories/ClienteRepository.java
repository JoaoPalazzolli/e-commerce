package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Transactional(readOnly = true)
    Optional<Cliente> findByUserEmail(String email);
}
