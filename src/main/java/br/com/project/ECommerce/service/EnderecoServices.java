package br.com.project.ECommerce.service;

import br.com.project.ECommerce.dto.enderecos.EnderecoDTO;
import br.com.project.ECommerce.mapper.Mapper;
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

import java.util.logging.Logger;

@Service
public class EnderecoServices {

    private static final Logger log = Logger.getLogger(EnderecoServices.class.getName());

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public ResponseEntity<EnderecoDTO> registrarEndereco(EnderecoDTO enderecoDTO){

        var cliente = clienteRepository.findByUserEmail("j@gmail.com").orElseThrow();

        if (!cidadeRepository.existsByNomeAndEstadoNome(enderecoDTO.getCidadeNome(), enderecoDTO.getCidadeEstadoNome()))
            registrarCidadeEstado(enderecoDTO);

        var cidade = cidadeRepository.findByNome(enderecoDTO.getCidadeNome()).orElseThrow();

        var endereco = Mapper.parseObject(enderecoDTO, Endereco.class);

        endereco.setCliente(cliente);
        endereco.setCidade(cidade);

        enderecoRepository.save(endereco);

        var dto = Mapper.parseObject(endereco, EnderecoDTO.class);

        return ResponseEntity.ok(dto);
    }

    private void registrarCidadeEstado(EnderecoDTO enderecoDTO){
        log.info("Registrando Cidade e Estado");

        var estado = Estado.builder()
                .nome(enderecoDTO.getCidadeEstadoNome())
                .build();

        estadoRepository.save(estado);

        var cidade = Cidade.builder()
                .nome(enderecoDTO.getCidadeNome())
                .estado(estado)
                .build();

        cidadeRepository.save(cidade);
    }
}
