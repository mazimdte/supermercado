package com.cestabasica.supermercado.repository;

import java.util.*;
import com.cestabasica.supermercado.domain.MovimentacaoEstoque;

public interface MovimentacaoRepository {
  MovimentacaoEstoque save(MovimentacaoEstoque m);
  List<MovimentacaoEstoque> findByProdutoId(UUID produtoId);
}
