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
    @Query(value = """
            select * from produto p
            inner join produto_categoria pc on p.id = pc.produto_id
            where pc.categoria_id = :categoriaId
            """, nativeQuery = true)
    Page<Produto> findByCategoriasId(@Param("categoriaId") Long categoryId, Pageable pageable);
}
