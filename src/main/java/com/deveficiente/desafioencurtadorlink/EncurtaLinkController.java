package com.deveficiente.desafioencurtadorlink;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class EncurtaLinkController {
	
	@Autowired
	private EntityManager manager;
	@Autowired
	//1
	private TransactionProxy transactionProxy;
	
	private static final Logger log = LoggerFactory
			.getLogger(EncurtaLinkController.class);


	@PostMapping(value = "/api/encurta")
	//1
	public URI executa(@RequestBody @Valid NovoLinkForm request,UriComponentsBuilder uriBuilder) {
		System.out.println("executando...");
		//1
		try {
			String idLink = RandomStringUtils.randomAlphanumeric(6);
			//1
			LinkEncurtado novoLink = new LinkEncurtado(request.link,idLink);
			//1
			transactionProxy.executeInTransaction(() -> {
				manager.persist(novoLink);			
			});
			URI novoRedirecionamento = uriBuilder.path("/{idPublico}").buildAndExpand(novoLink.idPublico).toUri();
			log.info("[LinkGerado] Novo link gerado {}",novoRedirecionamento);
			return novoRedirecionamento;
			//1
		} catch (DataIntegrityViolationException exception) {
			log.info("[IdDuplicado] Tentando gerar um novo id para links. A tentativa anterior deu errado",exception);
			//aqui eu poderia ter uma política de retry. Preciso? Será que vou ficar em loop infinito?
			return executa(request,uriBuilder);
		}		
		
	}
	
}
