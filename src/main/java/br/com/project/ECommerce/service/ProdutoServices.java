package br.com.project.ECommerce.service;

import br.com.project.ECommerce.controller.ProdutoController;
import br.com.project.ECommerce.dto.ProdutoDTO;
import br.com.project.ECommerce.mapper.Mapper;
import br.com.project.ECommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProdutoServices {

    private static final Logger LOGGER = Logger.getLogger(ProdutoServices.class.getName());

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PagedResourcesAssembler<ProdutoDTO> assembler;

    @Transactional(readOnly = true)
    public ResponseEntity<ProdutoDTO> findById(Long productId){
        LOGGER.info("Procurando Produto por ID");

        var produto = produtoRepository.findById(productId).orElseThrow();

        var dto = Mapper.parseObject(produto, ProdutoDTO.class);

        dto.add(linkTo(methodOn(ProdutoController.class).findById(productId)).withSelfRel());

        return ResponseEntity.ok(dto);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<PagedModel<EntityModel<ProdutoDTO>>> findAllProducts(Integer page, Integer size, String direction, String orderBy){
        LOGGER.info("Procurando Todos os Produtos");

       var dto = produtoRepository.findAll(pageable(page, size, direction, orderBy))
               .map(x -> Mapper.parseObject(x, ProdutoDTO.class));

       dto.map(x -> x.add(linkTo(methodOn(ProdutoController.class).findById(x.getId())).withSelfRel()));

        var link = linkTo(methodOn(ProdutoController.class)
                .findAllProducts(page, size, direction, orderBy)).withSelfRel();

        return ResponseEntity.ok(assembler.toModel(dto, link));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<PagedModel<EntityModel<ProdutoDTO>>> searchByName(String nomeProduto, Integer page, Integer size, String direction, String orderBy){
        LOGGER.info("Pesquisando Produtos");

        var produtoDTO = produtoRepository.searchByName(nomeProduto, pageable(page, size, direction, orderBy))
                .map(x -> Mapper.parseObject(x, ProdutoDTO.class));

        produtoDTO.map(x -> x.add(linkTo(methodOn(ProdutoController.class).findById(x.getId())).withSelfRel()));

        var link = linkTo(methodOn(ProdutoController.class).searchByName(nomeProduto, page, size, direction, orderBy)).withSelfRel();

        return ResponseEntity.ok(assembler.toModel(produtoDTO, link));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<PagedModel<EntityModel<ProdutoDTO>>> findAllByCategory(Long categoryId, Integer page, Integer size, String direction, String orderBy){
        LOGGER.info("Procurando Produtos pela Categoria");

        var dto = produtoRepository.findByCategoriasId(categoryId, pageable(page, size, direction, orderBy))
                .map(x -> Mapper.parseObject(x, ProdutoDTO.class));

        dto.map(x -> x.add(linkTo(methodOn(ProdutoController.class).findById(x.getId())).withSelfRel()));

        var link = linkTo(methodOn(ProdutoController.class)
                .findAllByCategory(categoryId, page, size, direction, orderBy)).withSelfRel();

        return ResponseEntity.ok(assembler.toModel(dto, link));
    }

    private Pageable pageable(Integer page, Integer size, String direction, String orderBy){
        var sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        return PageRequest.of(page, size, Sort.by(sortDirection, orderBy));
    }
}
