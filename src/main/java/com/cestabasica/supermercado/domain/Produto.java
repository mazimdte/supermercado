package com.cestabasica.supermercado.domain;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;   // << JPA (Spring Boot 3 usa jakarta.*)
import lombok.*;

@Entity
@Table(name = "produto")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Produto {

  // PK UUID gravada como BINARY(16) no MySQL
  @Id
  @GeneratedValue
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(nullable = false)
  private String nome;

  @Column(name = "codigo_barras", unique = true)
  private String codigoBarras;

  // Enum salvo como texto (ex.: HIGIENE, ACOUGUE…)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Categoria categoria;

  @Column(precision = 12, scale = 2)
  private BigDecimal precoCusto;

  @Column(precision = 12, scale = 2)
  private BigDecimal precoVenda;

  @Column(nullable = false)
  private Integer estoqueAtual;

  @Column(nullable = false)
  private Integer estoqueMinimo;

  @Column(nullable = false)
  private Boolean ativo;
}
    