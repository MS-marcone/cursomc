package com.marcone.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcone.cursomc.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
