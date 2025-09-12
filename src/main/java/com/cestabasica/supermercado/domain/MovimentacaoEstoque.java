package com.cestabasica.supermercado.domain;

import java.time.Instant;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoEstoque {
  private UUID id;
  private UUID produtoId;
  private TipoMovimentacao tipo;
  private Integer quantidade;
  private String motivo;      // ex.: Compra, Venda, Ajuste
  private String responsavel; // opcional
  private Instant dataHora;
}
