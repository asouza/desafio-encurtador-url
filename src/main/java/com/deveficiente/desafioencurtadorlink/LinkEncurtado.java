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
	public final @NotBlank @URL String linkOriginal;
	
	@Deprecated
	public LinkEncurtado() {
		this.idPublico = null;
		this.linkOriginal = null;
	}
	
	public LinkEncurtado(@NotBlank @URL String link, @NotBlank String idPublico) {
		this.linkOriginal = link;
		this.idPublico = idPublico;
	}
}
