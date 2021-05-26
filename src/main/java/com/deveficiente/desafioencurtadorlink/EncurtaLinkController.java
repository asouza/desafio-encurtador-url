package com.deveficiente.desafioencurtadorlink;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncurtaLinkController {
	
	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/api/encurta")
	@Transactional
	public void executa(@RequestBody @Valid NovoLinkForm request) {
		LinkEncurtado novoLink = new LinkEncurtado(request.link,6);
		manager.persist(novoLink);
		
	}
	
}
