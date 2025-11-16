package com.cestabasica.supermercado.domain;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "movimentacao_estoque")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MovimentacaoEstoque {

  // PK UUID (BINARY(16) no MySQL)
  @Id
  @GeneratedValue
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  // RELACIONAMENTO: N movimentações -> 1 produto
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "produto_id", nullable = false, columnDefinition = "BINARY(16)")
  @ToString.Exclude
  private Produto produto;

  // Enum salvo como texto
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TipoMovimentacao tipo; // ENTRADA | SAIDA

  @Column(nullable = false)
  private Integer quantidade;

  private String motivo;       // ex.: Compra, Venda, Ajuste
  private String responsavel;  // opcional

  @Column(nullable = false)
  private Instant dataHora;
}
