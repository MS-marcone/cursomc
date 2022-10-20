package com.marcone.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.marcone.cursomc.domain.Cliente;
import com.marcone.cursomc.dto.ClienteDTO;
import com.marcone.cursomc.repositories.ClienteRepository;
import com.marcone.cursomc.resources.exceptions.FieldMessage;

public class ClienteAtualizarValidator implements ConstraintValidator<ClienteAtualizar, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteAtualizar ann) {
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(map.get("id"));
		List<FieldMessage> list = new ArrayList<>();

		
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if ( aux != null && !aux.getId().equals(uriId)) {
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
