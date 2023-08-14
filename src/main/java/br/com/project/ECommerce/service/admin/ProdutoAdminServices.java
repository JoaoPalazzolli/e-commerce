package br.com.project.ECommerce.service.admin;

import br.com.project.ECommerce.controller.admin.ProdutoAdminController;
import br.com.project.ECommerce.dto.CategoriaDTO;
import br.com.project.ECommerce.dto.ProdutoDTO;
import br.com.project.ECommerce.mapper.Mapper;
import br.com.project.ECommerce.model.Categoria;
import br.com.project.ECommerce.model.Produto;
import br.com.project.ECommerce.repositories.CategoriaRepository;
import br.com.project.ECommerce.repositories.ProdutoRepository;
import br.com.project.ECommerce.service.exceptions.ConflictExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProdutoAdminServices {

    private static final Logger LOGGER = Logger.getLogger(ProdutoAdminServices.class.getName());

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ProdutoDTO> findById(Long productId){
        LOGGER.info("Procurando Produto por ID");

        var produto = produtoRepository.findById(productId).orElseThrow();

        var produtoDTO = Mapper.parseObject(produto, ProdutoDTO.class);

        produtoDTO.add(linkTo(methodOn(ProdutoAdminController.class)
                .findById(productId)).withSelfRel());

        return ResponseEntity.ok(produtoDTO);
    }

    @Transactional
    public ResponseEntity<ProdutoDTO> createProduct(ProdutoDTO produtoDTO){
        LOGGER.info("Cadastrando Produto");

        if(produtoRepository.existsByDescricaoAndMarca(produtoDTO.getDescricao(), produtoDTO.getMarca()))
            throw new ConflictExceptions();

        verificarSalvarCategorias(produtoDTO.getCategorias());

        var categorias = produtoDTO.getCategorias().stream()
                .map(x -> categoriaRepository.findByNome(x.getNome())
                        .orElseThrow()).collect(Collectors.toSet());

        var produto = Mapper.parseObject(produtoDTO, Produto.class);

        produto.setCategorias(categorias);
        produto.setPublicado(LocalDateTime.now());
        produto.setImagem("NULL");

        var dto = Mapper.parseObject(produtoRepository.save(produto), ProdutoDTO.class);

        dto.add(linkTo(methodOn(ProdutoAdminController.class)
                .findById(dto.getId())).withSelfRel());

        LOGGER.info("Produto Cadastrado com Sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Transactional
    public ResponseEntity<ProdutoDTO> updateProduct(ProdutoDTO produtoDTO){
        LOGGER.info("Atualizando Produto");

        verificarSalvarCategorias(produtoDTO.getCategorias());

        var categorias = produtoDTO.getCategorias().stream()
                .map(x -> categoriaRepository.findByNome(x.getNome())
                        .orElseThrow()).collect(Collectors.toSet());

        var produtoAntigo = produtoRepository.findById(produtoDTO.getId()).orElseThrow();

        if (produtoRepository.existsByDescricaoAndMarca(
                produtoDTO.getDescricao(), produtoDTO.getMarca()) &&
                !produtoAntigo.getDescricao().equalsIgnoreCase(produtoDTO.getDescricao()))
            throw new ConflictExceptions();

        var produtoNovo = Mapper.parseObject(produtoDTO, Produto.class);

        produtoNovo.setCategorias(categorias);
        produtoNovo.setImagem(produtoAntigo.getImagem()); // possivel alteração
        produtoNovo.setPublicado(produtoAntigo.getPublicado());

        var dto = Mapper.parseObject(produtoRepository.save(produtoNovo), ProdutoDTO.class);

        dto.add(linkTo(methodOn(ProdutoAdminController.class)
                .findById(dto.getId())).withSelfRel());

        LOGGER.info("Produto Atualizado com Sucesso");
        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(Long productId){
        LOGGER.info("Deletando Produto");

        var produto = produtoRepository.findById(productId).orElseThrow();

        produtoRepository.delete(produto);

        LOGGER.info("Produto Deletado com Sucesso");
        return ResponseEntity.noContent().build();
    }

    private void verificarSalvarCategorias(Set<CategoriaDTO> nomes){
        nomes.forEach(x ->{
            if(!categoriaRepository.existsByNome(x.getNome())){
                LOGGER.info("Salvando Categoria");
                var categoria = Categoria.builder()
                        .nome(x.getNome())
                        .build();

                categoriaRepository.save(categoria);
                LOGGER.info("Categoria Salva");
            }
        });
    }
}
