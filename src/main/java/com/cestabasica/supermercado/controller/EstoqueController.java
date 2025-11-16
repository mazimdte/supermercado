package com.cestabasica.supermercado.controller;

import java.time.Instant;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.cestabasica.supermercado.domain.MovimentacaoEstoque;
import com.cestabasica.supermercado.domain.Produto;
import com.cestabasica.supermercado.domain.TipoMovimentacao;
import com.cestabasica.supermercado.repository.MovimentacaoRepository;
import com.cestabasica.supermercado.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

  @Autowired
  private ProdutoRepository produtoRepo;

  @Autowired
  private MovimentacaoRepository movRepo;

  // Entrada de estoque
  @PostMapping("/{produtoId}/entrada")
  public ResponseEntity<?> entrada(
      @PathVariable UUID produtoId,
      @RequestParam Integer quantidade,
      @RequestParam(required = false) String motivo,
      @RequestParam(required = false) String responsavel) {

    Optional<Produto> opt = produtoRepo.findById(produtoId);
    if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
    if (quantidade == null || quantidade <= 0) return ResponseEntity.badRequest().body("Quantidade deve ser > 0.");

    Produto p = opt.get();
    int atual = p.getEstoqueAtual() == null ? 0 : p.getEstoqueAtual();
    p.setEstoqueAtual(atual + quantidade);
    produtoRepo.save(p);

    MovimentacaoEstoque m = MovimentacaoEstoque.builder()
        .produto(p) // << relacionamento JPA
        .tipo(TipoMovimentacao.ENTRADA)
        .quantidade(quantidade)
        .motivo(motivo)
        .responsavel(responsavel)
        .dataHora(Instant.now())
        .build();

    movRepo.save(m);
    return ResponseEntity.ok(p);
  }

  // Saída de estoque
  @PostMapping("/{produtoId}/saida")
  public ResponseEntity<?> saida(
      @PathVariable UUID produtoId,
      @RequestParam Integer quantidade,
      @RequestParam(required = false) String motivo,
      @RequestParam(required = false) String responsavel) {

    Optional<Produto> opt = produtoRepo.findById(produtoId);
    if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
    if (quantidade == null || quantidade <= 0) return ResponseEntity.badRequest().body("Quantidade deve ser > 0.");

    Produto p = opt.get();
    int atual = p.getEstoqueAtual() == null ? 0 : p.getEstoqueAtual();
    if (atual - quantidade < 0) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Estoque insuficiente.");
    }

    p.setEstoqueAtual(atual - quantidade);
    produtoRepo.save(p);

    MovimentacaoEstoque m = MovimentacaoEstoque.builder()
        .produto(p) // << relacionamento JPA
        .tipo(TipoMovimentacao.SAIDA)
        .quantidade(quantidade)
        .motivo(motivo)
        .responsavel(responsavel)
        .dataHora(Instant.now())
        .build();

    movRepo.save(m);
    return ResponseEntity.ok(p);
  }

  // Histórico por produto
@GetMapping("/{produtoId}/historico")
public ResponseEntity<?> historico(@PathVariable UUID produtoId) {
  if (!produtoRepo.existsById(produtoId)) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
  }
  return ResponseEntity.ok(movRepo.buscarHistoricoComProduto(produtoId));
}
}
