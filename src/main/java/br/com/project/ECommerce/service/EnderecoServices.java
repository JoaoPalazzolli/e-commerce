package br.com.project.ECommerce.service;

import br.com.project.ECommerce.controller.EnderecoController;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

@Service
public class EnderecoServices {

    private static final Logger LOGGER = Logger.getLogger(EnderecoServices.class.getName());

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PagedResourcesAssembler<EnderecoDTO> assembler;

    @Transactional(readOnly = true)
    public ResponseEntity<PagedModel<EntityModel<EnderecoDTO>>> findAll(Integer page, Integer size, String direction, String orderBy){
        LOGGER.info("Procurando Todos os Endereços");

        var enderecoDTO = enderecoRepository.findAll(pageable(page, size, direction, orderBy))
                .map(x -> Mapper.parseObject(x, EnderecoDTO.class));

        enderecoDTO.map(x -> x.add(linkTo(methodOn(EnderecoController.class).findById(x.getId())).withSelfRel()));

        var link = linkTo(methodOn(EnderecoController.class).findAll(page, size, direction, orderBy)).withSelfRel();

        return ResponseEntity.ok(assembler.toModel(enderecoDTO, link));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<EnderecoDTO> findById(Long id){
        LOGGER.info("Procurando Endereço Pelo ID");

        var endereco = enderecoRepository.findById(id).orElseThrow();

        var enderecoDTO = Mapper.parseObject(endereco, EnderecoDTO.class);

        enderecoDTO.add(linkTo(methodOn(EnderecoController.class).findById(id)).withSelfRel());

        return ResponseEntity.ok(enderecoDTO);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<EnderecoDTO> createAddress(EnderecoDTO enderecoDTO) {
        LOGGER.info("Registrando Endereço");

        var cliente = clienteRepository.findByUserEmail("alemão@gmail.com").orElseThrow();

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

        dto.add(linkTo(methodOn(EnderecoController.class).findById(dto.getId())).withSelfRel());

        LOGGER.info("Endereço Registrado com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<EnderecoDTO> updateAddress(EnderecoDTO enderecoDTO){
        LOGGER.info("Atualizando Endereço");

        var cliente = clienteRepository.findByUserEmail("alemão@gmail.com").orElseThrow();

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

        dto.add(linkTo(methodOn(EnderecoController.class).findById(dto.getId())).withSelfRel());

        LOGGER.info("Endereço Atualizado com Sucesso");

        return ResponseEntity.ok(dto);
    }

    @Transactional(readOnly = false)
    public ResponseEntity<?> deleteAddress(Long id){
        LOGGER.info("Deletando Endereço");

        var endereco = enderecoRepository.findById(id).orElseThrow();

        enderecoRepository.delete(endereco);

        LOGGER.info("Endereço Deletado com Sucesso");
        return ResponseEntity.noContent().build();
    }

    private void registrarCidade(EnderecoDTO enderecoDTO){
        LOGGER.info("Registrando Cidade");

        if (!estadoRepository.existsByNome(enderecoDTO.getCidadeEstadoNome()))
            registrarEstado(enderecoDTO);

        var estado = estadoRepository.findByNome(enderecoDTO.getCidadeEstadoNome()).orElseThrow();

        var cidade = Cidade.builder()
                .estado(estado)
                .nome(enderecoDTO.getCidadeNome())
                .build();

        cidadeRepository.save(cidade);

        LOGGER.info("Cidade e Estado Registrado com sucesso");
    }

    private void registrarEstado(EnderecoDTO enderecoDTO){
        LOGGER.info("Registrando Estado");

        var estado = Estado.builder()
                .nome(enderecoDTO.getCidadeEstadoNome())
                .build();

        estadoRepository.save(estado);
    }

    private Pageable pageable(Integer page, Integer size, String direction, String orderBy){
        var sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        return PageRequest.of(page, size, Sort.by(sortDirection, orderBy));
    }
}
