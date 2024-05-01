package com.stocksapi.repository;

import com.stocksapi.model.Dividends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DividendsRepository extends JpaRepository<Dividends, Integer> {
    Optional<Dividends> findByStocksId(Integer id);
}


// vamos precisar numa query os dividendos dos úlimos 12 meses...pegar data atual e passar a de 12 meses atras como parametro (para podermos calcular preço teto)
// em vez de fazer select * vamos pegar somente o atributo value...vamos ter que somar esse array de valores