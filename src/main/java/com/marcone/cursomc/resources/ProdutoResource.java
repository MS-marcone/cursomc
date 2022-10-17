//package com.marcone.cursomc.recursos;
package com.marcone.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcone.cursomc.domain.Produto;
import com.marcone.cursomc.dto.ProdutoDTO;
import com.marcone.cursomc.resources.utils.URL;
import com.marcone.cursomc.service.ProdutoService;



@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService prodServ;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> buscar(@PathVariable Integer id){
		Produto obj = prodServ.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> paginacao(
			@RequestParam(value = "nome", defaultValue="0") String nome,
			@RequestParam(value = "categoria", defaultValue="0") String categoria,			
			@RequestParam(value = "page", defaultValue="0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue="ASC") String direction){
		String nomeDecoded = URL.decodeParam(nome);
		Iterable<Integer> ids = URL.decodeIntList(categoria);
		Page<Produto> lista = prodServ.pesquisar(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listaDto = lista.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listaDto);
		
	}
	

}
