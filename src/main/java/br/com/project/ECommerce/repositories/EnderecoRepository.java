package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Cidade;
import br.com.project.ECommerce.model.Endereco;
import br.com.project.ECommerce.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Boolean existsByClienteIdAndCepAndNumeroAndCidadeNomeAndCidadeEstadoNome(Long clienteId, String cep, String numero, String cidadeNome, String cidadeEstadoNome);

    Boolean existsByClienteIdAndCepAndNumeroAndCidadeNomeAndCidadeEstadoNomeAndLogradouroAndComplementoAndBairro(Long clienteId,
                                                                                                                 String cep, String numero,
                                                                                                                 String cidadeNome, String cidadeEstadoNome,
                                                                                                                 String logradouro, String complemento,
                                                                                                                 String bairro);
}
