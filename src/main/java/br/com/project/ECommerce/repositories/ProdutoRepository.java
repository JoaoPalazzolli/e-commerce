package br.com.project.ECommerce.repositories;

import br.com.project.ECommerce.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Boolean existsByDescricaoAndMarca(String descricao, String marca);

    @Transactional(readOnly = true)
    @Query("""
            select p from Produto p where lower(p.descricao) like lower(concat('%',:nomeProduto,'%'))
            """)
    Page<Produto> searchByName(@Param(value = "nomeProduto") String nomeProduto, Pageable pageable);

    @Transactional(readOnly = true)
    /*@Query("""
            select p from Produto p where p.categorias.id = categoryId
            """) */
    Page<Produto> findByCategoriasId(Long categoryId, Pageable pageable);
}
