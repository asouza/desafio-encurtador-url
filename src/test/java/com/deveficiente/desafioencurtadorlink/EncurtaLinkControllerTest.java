package com.deveficiente.desafioencurtadorlink;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class EncurtaLinkControllerTest {

	@Autowired
	private CustomMockMvc mvc;

	@Property(tries = 10000)
	@Label("cadastro de novos enderecos")
	public void teste(
			@ForAll @StringLength(min = 1, max = 10) @AlphaChars String restoEndereco)
			throws Exception {

		String url = "http://teste.com/" + restoEndereco;

		mvc.post("/api/encurta", Map.of("link", url))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

}
