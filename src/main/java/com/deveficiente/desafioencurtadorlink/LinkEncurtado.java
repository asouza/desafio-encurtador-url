package com.deveficiente.desafioencurtadorlink;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

@Entity
public class LinkEncurtado {
	@Id
	@NotBlank
	public final String idPublico;
	private @NotBlank @URL String linkOriginal;
	
	
	public LinkEncurtado(@NotBlank @URL String link, @NotBlank String idPublico) {
		this.linkOriginal = link;
		this.idPublico = idPublico;
	}
}
