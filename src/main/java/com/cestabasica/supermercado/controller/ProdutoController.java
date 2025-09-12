package com.cestabasica.supermercado.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.cestabasica.supermercado.domain.Produto;
import com.cestabasica.supermercado.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

  @Autowired
  private ProdutoRepository repo;

  // CREATE
  @PostMapping
  public ResponseEntity<Produto> criar(@RequestBody Produto body) {
    // validações manuais simples (opcional)
    if (body.getNome() == null || body.getNome().isBlank()) {
      return ResponseEntity.badRequest().build();
    }

    // impedir duplicidade por código de barras (opcional)
    if (body.getCodigoBarras() != null) {
      Optional<Produto> existente = repo.findByCodigoBarras(body.getCodigoBarras());
      if (existente.isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }
    }

    // defaults simples
    if (body.getEstoqueAtual() == null) body.setEstoqueAtual(0);
    if (body.getEstoqueMinimo() == null) body.setEstoqueMinimo(0);
    if (body.getAtivo() == null) body.setAtivo(true);

    Produto salvo = repo.save(body);
    return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
  }

  // READ all
  @GetMapping
  public ResponseEntity<List<Produto>> listar() {
    return ResponseEntity.ok(repo.findAll());
  }

  // READ by id
  @GetMapping("/{id}")
  public ResponseEntity<Produto> buscar(@PathVariable UUID id) {
    return repo.findById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  // UPDATE
  @PutMapping("/{id}")
  public ResponseEntity<Produto> atualizar(@PathVariable UUID id, @RequestBody Produto body) {
    Optional<Produto> existenteOpt = repo.findById(id);
    if (existenteOpt.isEmpty()) return ResponseEntity.notFound().build();

    Produto existente = existenteOpt.get();

    // checagem simples de código de barras (não obrigatório)
    if (body.getCodigoBarras() != null) {
      repo.findByCodigoBarras(body.getCodigoBarras()).ifPresent(outro -> {
        if (!outro.getId().equals(id)) {
          throw new RuntimeException("Código de barras já utilizado."); // simples
        }
      });
    }

    existente.setNome(body.getNome() != null ? body.getNome() : existente.getNome());
    existente.setCodigoBarras(body.getCodigoBarras());
    existente.setCategoria(body.getCategoria());
    existente.setPrecoCusto(body.getPrecoCusto());
    existente.setPrecoVenda(body.getPrecoVenda());
    existente.setEstoqueAtual(body.getEstoqueAtual());
    existente.setEstoqueMinimo(body.getEstoqueMinimo());
    existente.setAtivo(body.getAtivo() == null ? existente.getAtivo() : body.getAtivo());

    Produto salvo = repo.save(existente);
    return ResponseEntity.ok(salvo);
  }

  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable UUID id) {
    Optional<Produto> existente = repo.findById(id);
    if (existente.isEmpty()) return ResponseEntity.notFound().build();

    repo.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
