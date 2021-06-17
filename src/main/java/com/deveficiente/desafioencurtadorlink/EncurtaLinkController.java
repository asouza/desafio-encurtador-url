package com.deveficiente.desafioencurtadorlink;

import java.net.URI;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class EncurtaLinkController {

	@Autowired
	private EntityManager manager;
	@Autowired
	// 1
	private TransactionProxy transactionProxy;

	private static final Logger log = LoggerFactory
			.getLogger(EncurtaLinkController.class);

	@PostMapping(value = "/api/encurta")
	// 1
	public Map<String, Object> executa(@RequestBody @Valid NovoLinkForm request,
			UriComponentsBuilder uriBuilder) {
		// 1
		try {
			String idLink = RandomStringUtils.randomAlphanumeric(6);
			// 1
			LinkEncurtado novoLink = new LinkEncurtado(request.link, idLink);
			// 1
			transactionProxy.executeInTransaction(() -> {
				manager.persist(novoLink);
			});
			URI novoRedirecionamento = uriBuilder.path("/{idPublico}")
					.buildAndExpand(novoLink.idPublico).toUri();
			log.info("[LinkGerado] Novo link gerado {}", novoRedirecionamento);
			return Map.of("redirect",novoRedirecionamento,"id",novoLink.idPublico);
			// 1
		} catch (DataIntegrityViolationException exception) {
			log.info(
					"[IdDuplicado] Tentando gerar um novo id para links. A tentativa anterior deu errado",
					exception);
			// aqui eu poderia ter uma política de retry. Preciso? Será que vou
			// ficar em loop infinito?
			return executa(request, uriBuilder);
		}

	}

	@GetMapping("/{id}")
	public HttpEntity<?> redireciona(
			@PathVariable("id") String idLinkEncurtado) {
		URI enderecoOriginal = URI.create(manager.find(LinkEncurtado.class,
				idLinkEncurtado).linkOriginal);
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(enderecoOriginal).build();
	}

}
