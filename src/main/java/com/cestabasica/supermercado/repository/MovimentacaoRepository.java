package com.cestabasica.supermercado.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cestabasica.supermercado.domain.MovimentacaoEstoque;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEstoque, UUID> {

  List<MovimentacaoEstoque> findByProduto_Id(UUID produtoId);

  @Query("""
    select m
    from MovimentacaoEstoque m
    join fetch m.produto p
    where p.id = :produtoId
    order by m.dataHora desc
  """)
  List<MovimentacaoEstoque> buscarHistoricoComProduto(@Param("produtoId") UUID produtoId);
}
