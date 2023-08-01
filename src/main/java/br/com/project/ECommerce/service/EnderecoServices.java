package br.com.project.ECommerce.service;

import br.com.project.ECommerce.dto.EnderecoDTO;
import br.com.project.ECommerce.service.exceptions.ConflictExceptions;
import br.com.project.ECommerce.mapper.Mapper;
import br.com.project.ECommerce.model.Cidade;
import br.com.project.ECommerce.model.Endereco;
import br.com.project.ECommerce.model.Estado;
import br.com.project.ECommerce.repositories.CidadeRepository;
import br.com.project.ECommerce.repositories.ClienteRepository;
import br.com.project.ECommerce.repositories.EnderecoRepository;
import br.com.project.ECommerce.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional(readOnly = true)
    public ResponseEntity<List<EnderecoDTO>> findAll(){
        log.info("Procurando Todos os Endereços");

        var endereco = enderecoRepository.findAll();
        var enderecoDTO = Mapper.parseListObjects(endereco, EnderecoDTO.class);

        return ResponseEntity.ok(enderecoDTO);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<EnderecoDTO> findById(Long id){
        log.info("Procurando Endereço Pelo ID");

        var endereco = enderecoRepository.findById(id).orElseThrow();

        var enderecoDTO = Mapper.parseObject(endereco, EnderecoDTO.class);

        return ResponseEntity.ok(enderecoDTO);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<EnderecoDTO> createAddress(EnderecoDTO enderecoDTO) {
        log.info("Registrando Endereço");

        var cliente = clienteRepository.findByUserEmail("fortinitefoda12@gmail.com").orElseThrow();

        if (!cidadeRepository.existsByNomeAndEstadoNome(enderecoDTO.getCidadeNome(), enderecoDTO.getCidadeEstadoNome()))
            registrarCidade(enderecoDTO);

        var cidade = cidadeRepository.findByNomeAndEstadoNome(enderecoDTO.getCidadeNome(), enderecoDTO.getCidadeEstadoNome()).orElseThrow();

        var endereco = Mapper.parseObject(enderecoDTO, Endereco.class);

        endereco.setCliente(cliente);
        endereco.setCidade(cidade);

        if(enderecoRepository.existsByClienteIdAndCepAndNumeroAndCidadeNomeAndCidadeEstadoNome(
                cliente.getId(), enderecoDTO.getCep(),
                enderecoDTO.getNumero(), enderecoDTO.getCidadeNome(), enderecoDTO.getCidadeEstadoNome()))
            throw new ConflictExceptions();

        enderecoRepository.save(endereco);

        var dto = Mapper.parseObject(endereco, EnderecoDTO.class);

        log.info("Endereço Registrado com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<EnderecoDTO> updateAddress(EnderecoDTO enderecoDTO){
        log.info("Atualizando Endereço");

        var cliente = clienteRepository.findByUserEmail("fortinitefoda12@gmail.com").orElseThrow();

        if (!cidadeRepository.existsByNomeAndEstadoNome(enderecoDTO.getCidadeNome(), enderecoDTO.getCidadeEstadoNome()))
            registrarCidade(enderecoDTO);

        var cidade = cidadeRepository.findByNomeAndEstadoNome(enderecoDTO.getCidadeNome(), enderecoDTO.getCidadeEstadoNome()).orElseThrow();

        var endereco = enderecoRepository.findById(enderecoDTO.getId()).orElseThrow();

        endereco = Mapper.parseObject(enderecoDTO, Endereco.class);

        endereco.setCidade(cidade);
        endereco.setCliente(cliente);

        if(enderecoRepository.existsByClienteIdAndCepAndNumeroAndCidadeNomeAndCidadeEstadoNomeAndLogradouroAndComplementoAndBairro(
                cliente.getId(), enderecoDTO.getCep(), enderecoDTO.getNumero(), enderecoDTO.getCidadeNome(),
                enderecoDTO.getCidadeEstadoNome(), enderecoDTO.getLogradouro(), enderecoDTO.getComplemento(),
                enderecoDTO.getBairro()
        ))
            throw new ConflictExceptions();

        enderecoRepository.save(endereco);

        var dto = Mapper.parseObject(endereco, EnderecoDTO.class);

        return ResponseEntity.ok(dto);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<?> deleteAddress(Long id){
        var endereco = enderecoRepository.findById(id).orElseThrow();

        enderecoRepository.delete(endereco);

        return ResponseEntity.noContent().build();
    }

    private void registrarCidade(EnderecoDTO enderecoDTO){
        log.info("Registrando Cidade");

        if (!estadoRepository.existsByNome(enderecoDTO.getCidadeEstadoNome()))
            registrarEstado(enderecoDTO);

        var estado = estadoRepository.findByNome(enderecoDTO.getCidadeEstadoNome()).orElseThrow();

        var cidade = Cidade.builder()
                .estado(estado)
                .nome(enderecoDTO.getCidadeNome())
                .build();

        cidadeRepository.save(cidade);

        log.info("Cidade e Estado Registrado com sucesso");
    }

    private void registrarEstado(EnderecoDTO enderecoDTO){
        log.info("Registrando Estado");

        var estado = Estado.builder()
                .nome(enderecoDTO.getCidadeEstadoNome())
                .build();

        estadoRepository.save(estado);
    }
}
