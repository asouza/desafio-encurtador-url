package com.deveficiente.desafioencurtadorlink;

import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class GeradorIdLink {

	private EntityManager manager;
	private TransactionProxy transactionProxy;
	
	private static final Logger log = LoggerFactory
			.getLogger(GeradorIdLink.class);

	
	public GeradorIdLink(EntityManager manager,TransactionProxy transactionProxy) {
		super();
		this.manager = manager;
		this.transactionProxy = transactionProxy;
	}

	public String nextId() {

		try {
			String idLink = RandomStringUtils.randomAlphanumeric(6);
			transactionProxy.executeInTransaction(() -> {
				manager.persist(new IdGerado(idLink));				
			});
			return idLink;
		} catch (DataIntegrityViolationException exception) {
			log.info("[IdDuplicado] Tentando gerar um novo id para links. A tentativa anterior deu errado",exception);
			//aqui eu poderia ter uma política de retry. Preciso? Será que vou ficar em loop infinito?
			return nextId();
		}
	}
}
