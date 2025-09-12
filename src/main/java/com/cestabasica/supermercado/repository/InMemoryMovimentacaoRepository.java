package com.cestabasica.supermercado.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.cestabasica.supermercado.domain.MovimentacaoEstoque;

@Repository
public class InMemoryMovimentacaoRepository implements MovimentacaoRepository {

  private final Map<UUID, List<MovimentacaoEstoque>> db = new ConcurrentHashMap<>();

  @Override
  public MovimentacaoEstoque save(MovimentacaoEstoque m) {
    if (m.getId() == null) m.setId(UUID.randomUUID());
    db.computeIfAbsent(m.getProdutoId(), k -> Collections.synchronizedList(new ArrayList<>()))
      .add(m);
    return m;
  }

  @Override
  public List<MovimentacaoEstoque> findByProdutoId(UUID produtoId) {
    return new ArrayList<>(db.getOrDefault(produtoId, Collections.emptyList()));
  }
}
