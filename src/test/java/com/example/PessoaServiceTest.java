package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.model.Pessoa;
import com.example.repository.PessoaRepository;
import com.example.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaServiceTest {
	
	@TestConfiguration
	public class PessoaServiceTestConfig {
		
		@Bean
		PessoaService pessoaService() {
			return new PessoaService();
		}
		
		@Bean
		ObjectMapper objMapper() {
			return new ObjectMapper();
		}
	}
	
	@Autowired
	PessoaService pessoaService;
	
	@Autowired
	ObjectMapper objMapper;
	
	@MockBean
	PessoaRepository pessoaRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeEach
	public void setup() {
		when(pessoaRepository.findById(1L)).thenReturn(Optional.of(instancePessoa(1L)));
		when(pessoaRepository.save(instancePessoa(null))).thenReturn(instancePessoa(1L));
	}
	
	private Pessoa instancePessoa(Long id) {
		return Pessoa
				.builder()
				.id(id)
				.nome("Joao").build();
	}
	
	@Test
	public void pessoaServiceTestGetPessoa() {
		Pessoa p = pessoaService.getPessoaById(1L);
		assertThat(p).isNotNull();
		assertEquals(p.getNome(), "Joao");
	}

	@Test
	public void pessoaServiceTestGetPessoaException() {
		assertThatThrownBy(() -> pessoaService.getPessoaById(2L))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("pessoa nula.");
	}
	
	@Test
	public void pessoaServiceTestSavePessoa() {
		Pessoa p = pessoaService.savePessoa(instancePessoa(null));
		assertEquals(p, instancePessoa(1L));
	}
	
	@Test
	public void getPessoaByIdController() throws Exception {
		mockMvc
		.perform(get("/pessoa/1"))	
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void savePessoaController() throws Exception {
		String json = objMapper.writeValueAsString(instancePessoa(null));
		mockMvc
			.perform(post("/pessoa")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
