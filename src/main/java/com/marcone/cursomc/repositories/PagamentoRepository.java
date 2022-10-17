package com.marcone.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcone.cursomc.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

}
