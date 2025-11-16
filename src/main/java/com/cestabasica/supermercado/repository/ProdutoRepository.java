package com.cestabasica.supermercado.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cestabasica.supermercado.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
  Optional<Produto> findByCodigoBarras(String codigoBarras);
}
