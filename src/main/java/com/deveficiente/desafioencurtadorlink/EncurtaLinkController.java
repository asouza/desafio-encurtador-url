package com.deveficiente.desafioencurtadorlink;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

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
			return Map.of("redirect", novoRedirecionamento, "id",
					novoLink.idPublico);
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

	@SuppressWarnings("deprecation")
	@GetMapping("/{id}")
	public HttpEntity<?> redireciona(@PathVariable("id") String idLinkEncurtado,
			@RequestHeader HttpHeaders headers) {
		LinkEncurtado link = manager.find(LinkEncurtado.class, idLinkEncurtado);

		WebClient client = WebClient.create("http://localhost:8080");
		RequestBodySpec spec = client.post().uri("/click/{id}",
				idLinkEncurtado);

		// 1
		headers.entrySet().forEach(entry -> {
			// colocar valores com colchetes no header gerava requisicao
			// invalida. [valor]
			spec.header(entry.getKey(),
					entry.getValue().stream().collect(Collectors.joining(",")));
		});
		
		//preciso ter um interessado no evento para que ele aconteça de fato.
		//tinha me passado nessa inteligência... achei que ele ia guardar o resultado até que tivesse um interessado.
		//tratar erros aqui, o que fazer? retry?
		spec.retrieve().bodyToMono(String.class).subscribe();


		return ResponseEntity.status(HttpStatus.FOUND).location(link.original())
				.build();
	}

	@PostMapping("/click/{id}")
	// preciso bloquear para que as chamadas só venham de localhost
	public void salvaClick(@PathVariable("id") String idLinkEncurtado,
			@RequestHeader HttpHeaders headers) {
		System.out.println("salvando clicks...");
		System.out.println(headers.entrySet());
		LinkEncurtado link = manager.find(LinkEncurtado.class, idLinkEncurtado);
		transactionProxy.executeAsyncInTransaction(
				() -> manager.persist(new Click(link, headers)));

	}

}
