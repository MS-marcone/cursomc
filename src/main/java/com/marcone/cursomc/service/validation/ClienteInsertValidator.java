package com.marcone.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcone.cursomc.domain.Cliente;
import com.marcone.cursomc.domain.enums.TipoCliente;
import com.marcone.cursomc.dto.NovoClienteDTO;
import com.marcone.cursomc.repositories.ClienteRepository;
import com.marcone.cursomc.resources.exceptions.FieldMessage;
import com.marcone.cursomc.service.validation.utils.ValidarCpfCnpj;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, NovoClienteDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(NovoClienteDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCode()) && !ValidarCpfCnpj.isValidCpf(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF Invalido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCode()) && !ValidarCpfCnpj.isValidCnpj(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ Invalido!"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if ( aux != null) {
			list.add(new FieldMessage("email", "Email ja existente"));
		}
		

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFielName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
