package com.deveficiente.desafioencurtadorlink;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

public class NovoLinkForm {

	@NotBlank
	@URL
	public final String link;

	@JsonCreator(mode = Mode.PROPERTIES)
	public NovoLinkForm(@NotBlank @URL String link) {
		this.link = link;
	}
	
	
}
