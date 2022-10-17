package com.marcone.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcone.cursomc.domain.Categoria;
import com.marcone.cursomc.domain.Produto;
import com.marcone.cursomc.repositories.CategoriaRepository;
import com.marcone.cursomc.repositories.ProdutoRepository;
import com.marcone.cursomc.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository prodRepo;
	
	@Autowired
	private CategoriaRepository catRepo;
	
	public Produto buscar(Integer id) {
		Optional<Produto> obj = prodRepo.findById(id);		
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id ));
	}
	
	public Page<Produto> pesquisar(String nome, Iterable<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catRepo.findAllById(ids);
		return prodRepo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}

	
}
