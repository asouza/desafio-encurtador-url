package com.deveficiente.desafioencurtadorlink;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpHeaders;

@Entity
public class Click {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private @Valid @NotNull LinkEncurtado link;
	@MapKeyColumn(name = "header")
	@ElementCollection
	@Column(name = "valores")
	private Map<String, String> headers;

	public Click(@Valid @NotNull LinkEncurtado link, @NotNull HttpHeaders headers) {
		this.link = link;
		this.headers = headers.toSingleValueMap();
	}

	
}
