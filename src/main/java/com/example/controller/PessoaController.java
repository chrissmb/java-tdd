package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Pessoa;
import com.example.service.PessoaService;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
	
	@Autowired
	PessoaService pessoaService;
	
	@GetMapping(value = "{id}")
	public Pessoa getPessoaById(@PathVariable Long id) {
		return pessoaService.getPessoaById(id);
	}

	@PostMapping()
	public Pessoa savePessoa(@RequestBody Pessoa pessoa) {
		return pessoaService.savePessoa(pessoa);
	}

}
