package com.marcone.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcone.cursomc.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
