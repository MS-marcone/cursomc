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

import com.marcone.cursomc.domain.Cliente;
import com.marcone.cursomc.dto.ClienteDTO;
import com.marcone.cursomc.dto.NovoClienteDTO;
import com.marcone.cursomc.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService cliserv;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> buscarTodos(){
		List<Cliente> lista = cliserv.buscarTodos();
		List<ClienteDTO> listaDto = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDto);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Integer id){
		Cliente obj = cliserv.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody NovoClienteDTO objDto){
		Cliente obj = cliserv.deDto(objDto);
		obj = cliserv.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = cliserv.deDto(objDto);
		obj.setId(id);
		obj = cliserv.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		cliserv.deletar(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> paginacao(
			@RequestParam(value = "page", defaultValue="0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue="ASC") String direction){
		Page<Cliente> lista = cliserv.paginacao(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listaDto = lista.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(listaDto);
		
	}
	

}
