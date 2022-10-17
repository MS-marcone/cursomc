package com.marcone.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcone.cursomc.domain.Categoria;
import com.marcone.cursomc.dto.CategoriaDTO;
import com.marcone.cursomc.repositories.CategoriaRepository;
import com.marcone.cursomc.service.exception.DataIntegrityException;
import com.marcone.cursomc.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catrepo;

	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = catrepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id));
	}
	
	public List<Categoria> buscarTodos(){
		return catrepo.findAll();
	}

	public Categoria inserir(Categoria obj) {
		return catrepo.save(obj);
	}

	public Categoria atualizar(Categoria obj) {
		Categoria newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return catrepo.save(newObj);
	}

	public void deletar(Integer id) {
		buscar(id);

		try {
			catrepo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Categoria com produtos cadastrados nao pode ser deletada");
		}
	}
	
	public Page<Categoria> paginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return catrepo.findAll(pageRequest);
		
	}
	
	public Categoria deDto(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
		
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
		
	}
}
