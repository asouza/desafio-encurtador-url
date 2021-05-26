package com.deveficiente.desafioencurtadorlink;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.URL;

@Entity
public class LinkEncurtado {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank @URL String linkOriginal;
	@NotBlank
	private String idPublico;
	
	
	public LinkEncurtado(@NotBlank @URL String link, @Positive int quantidadeCaracteres) {
		this.linkOriginal = link;
		this.idPublico = RandomStringUtils.randomAlphabetic(quantidadeCaracteres);
	}
}
