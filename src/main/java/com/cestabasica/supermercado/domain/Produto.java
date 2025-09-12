package com.cestabasica.supermercado.domain;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {
  private UUID id;
  private String nome;
  private String codigoBarras;   // opcional
  private Categoria categoria;   // enum
  private BigDecimal precoCusto; // pode ser null se quiser simplificar mais
  private BigDecimal precoVenda;
  private Integer estoqueAtual;
  private Integer estoqueMinimo;
  private Boolean ativo;
}

