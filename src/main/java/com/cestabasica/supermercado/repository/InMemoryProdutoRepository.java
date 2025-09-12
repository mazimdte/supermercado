package com.cestabasica.supermercado.repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.cestabasica.supermercado.domain.Produto;

@Repository
public class InMemoryProdutoRepository implements ProdutoRepository {

  private final Map<UUID, Produto> db = new ConcurrentHashMap<>();

  @Override
  public Produto save(Produto p) {
    if (p.getId() == null) p.setId(UUID.randomUUID());
    db.put(p.getId(), p);
    return p;
  }

  @Override
  public Optional<Produto> findById(UUID id) {
    return Optional.ofNullable(db.get(id));
  }

  @Override
  public List<Produto> findAll() {
    return new ArrayList<>(db.values());
  }

  @Override
  public void deleteById(UUID id) {
    db.remove(id);
  }

  @Override
  public Optional<Produto> findByCodigoBarras(String codigo) {
    if (codigo == null) return Optional.empty();
    return db.values().stream()
      .filter(p -> codigo.equals(p.getCodigoBarras()))
      .findFirst();
  }
}
