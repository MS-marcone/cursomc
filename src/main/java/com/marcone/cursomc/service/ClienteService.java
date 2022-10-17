package com.marcone.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcone.cursomc.domain.Cidade;
import com.marcone.cursomc.domain.Cliente;
import com.marcone.cursomc.domain.Endereco;
import com.marcone.cursomc.domain.enums.TipoCliente;
import com.marcone.cursomc.dto.ClienteDTO;
import com.marcone.cursomc.dto.NovoClienteDTO;
import com.marcone.cursomc.repositories.ClienteRepository;
import com.marcone.cursomc.repositories.EnderecoRepository;
import com.marcone.cursomc.service.exception.DataIntegrityException;
import com.marcone.cursomc.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clirepo;
	
	@Autowired
	private EnderecoRepository endrepo;
	
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = clirepo.findById(id);		
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id ));
	}
	
	public List<Cliente> buscarTodos(){
		return clirepo.findAll();
	}
	
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = clirepo.save(obj);
		endrepo.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente atualizar(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return clirepo.save(newObj);
	}

	public void deletar(Integer id) {
		buscar(id);

		try {
			clirepo.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Cliente com pedidos cadastrados nao pode ser deletada");
		}
	}
	
	public Page<Cliente> paginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clirepo.findAll(pageRequest);
		
	}
	
	public Cliente deDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
		
	}
	
	public Cliente deDto(NovoClienteDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj()	, TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogadouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
