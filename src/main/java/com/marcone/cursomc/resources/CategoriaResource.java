//package com.marcone.cursomc.recursos;
package com.marcone.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marcone.cursomc.domain.Categoria;
import com.marcone.cursomc.dto.CategoriaDTO;
import com.marcone.cursomc.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService catserv;
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> buscarTodos(){
		List<Categoria> lista = catserv.buscarTodos();
		List<CategoriaDTO> listaDto = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDto);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscar(@PathVariable Integer id){
		Categoria obj = catserv.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO objDto){
		Categoria obj = catserv.deDto(objDto);
		obj = catserv.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = catserv.deDto(objDto);
		obj.setId(id);
		obj = catserv.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		catserv.deletar(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> paginacao(
			@RequestParam(value = "page", defaultValue="0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue="ASC") String direction){
		Page<Categoria> lista = catserv.paginacao(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listaDto = lista.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listaDto);
		
	}
}
