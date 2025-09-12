package com.cestabasica.supermercado.repository;

import java.util.*;
import com.cestabasica.supermercado.domain.Produto;

public interface ProdutoRepository {
  Produto save(Produto p);
  Optional<Produto> findById(UUID id);
  List<Produto> findAll();
  void deleteById(UUID id);
  Optional<Produto> findByCodigoBarras(String codigo);
}
