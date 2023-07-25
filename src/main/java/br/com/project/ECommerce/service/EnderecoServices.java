package br.com.project.ECommerce.service;

import br.com.project.ECommerce.dto.EnderecoDTO;
import br.com.project.ECommerce.model.Cidade;
import br.com.project.ECommerce.model.Endereco;
import br.com.project.ECommerce.model.Estado;
import br.com.project.ECommerce.repositories.CidadeRepository;
import br.com.project.ECommerce.repositories.ClienteRepository;
import br.com.project.ECommerce.repositories.EnderecoRepository;
import br.com.project.ECommerce.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServices {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public ResponseEntity registrarEndereco(EnderecoDTO enderecoDTO){

        var cliente = clienteRepository.findById(1L).orElseThrow();

        var estado = Estado.builder()
                .nome(enderecoDTO.getCidadeEstadoNome())
                .build();

        estadoRepository.save(estado);

        var cidade = Cidade.builder()
                .nome(enderecoDTO.getCidadeNome())
                .estado(estado)
                .build();

        cidadeRepository.save(cidade);

        var endereco = Endereco.builder()
                .logradouro(enderecoDTO.getLogradouro())
                .cep(enderecoDTO.getCep())
                .bairro(enderecoDTO.getBairro())
                .complemento(enderecoDTO.getComplemento())
                .numero(enderecoDTO.getNumero())
                .cliente(cliente)
                .cidade(cidade)
                .build();

        enderecoRepository.save(endereco);

        return ResponseEntity.ok().build();
    }
}
