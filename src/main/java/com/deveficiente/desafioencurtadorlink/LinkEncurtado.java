package com.deveficiente.desafioencurtadorlink;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

@Entity
public class LinkEncurtado {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank @URL String linkOriginal;
	@NotBlank
	public final String idPublico;
	
	
	public LinkEncurtado(@NotBlank @URL String link, String idPublico) {
		this.linkOriginal = link;
		this.idPublico = idPublico;
	}
}
