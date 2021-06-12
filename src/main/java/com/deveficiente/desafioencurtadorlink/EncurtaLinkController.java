package com.deveficiente.desafioencurtadorlink;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class EncurtaLinkController {
	
	@Autowired
	private EntityManager manager;
	@Autowired
	private GeradorIdLink geradorId;
	
	private static final Logger log = LoggerFactory
			.getLogger(EncurtaLinkController.class);


	@PostMapping(value = "/api/encurta")
	@Transactional
	public URI executa(@RequestBody @Valid NovoLinkForm request,UriComponentsBuilder uriBuilder) {
		LinkEncurtado novoLink = new LinkEncurtado(request.link,geradorId.nextId());		
		manager.persist(novoLink);
		
		URI novoRedirecionamento = uriBuilder.path("/{idPublico}").buildAndExpand(novoLink.idPublico).toUri();
		log.info("[LinkGerado] Novo link gerado {}",novoRedirecionamento);
		return novoRedirecionamento;
		
	}
	
}
