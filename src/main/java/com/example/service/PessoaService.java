package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Pessoa;
import com.example.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository repository;
	
	public Pessoa getPessoaById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("pessoa nula."));
	}
	
	public Pessoa savePessoa(Pessoa pessoa) {
		return repository.save(pessoa);
	}
}
