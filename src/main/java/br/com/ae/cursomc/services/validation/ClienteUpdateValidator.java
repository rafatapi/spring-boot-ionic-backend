package br.com.ae.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.ae.cursomc.domain.Cliente;
import br.com.ae.cursomc.dto.ClienteDTO;
import br.com.ae.cursomc.repositories.ClienteRepository;
import br.com.ae.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		// lista de erros dos campos
		List<FieldMessage> list = new ArrayList<>();
		
		// Buscando o codigo do cliente - que esta na URL
		@SuppressWarnings("unchecked")
		Map<String,String> map = (Map<String,String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		// Verifica se existe cliente com o mesmo email 
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if ((aux != null) && (!aux.getId().equals(uriId))) {
			list.add(new FieldMessage("email","Email ja existente."));
		}
		
		// Transforma a lista de erro personalizados da List<FieldMessage> para a lista de erros do framework
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}
