package com.deveficiente.desafioencurtadorlink;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IdGerado {

	@Id
	private String idLink;

	public IdGerado(String idLink) {
		this.idLink = idLink;
	}

}
