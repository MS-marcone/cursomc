package com.marcone.cursomc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcone.cursomc.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	Optional<Pedido> findById(Integer id);

}
