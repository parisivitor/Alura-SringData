package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {
	
	private final FuncionarioRepository funcionarioRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	
	
	public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner, Integer action) {
		System.out.println("Digite o nome");
		String nome = scanner.next();
		if(nome.equalsIgnoreCase("NULL")) {
			nome = null;
		}
		
		System.out.println("Digite o cpf");
		String cpf = scanner.next();
		if(cpf.equalsIgnoreCase("NULL")) {
			cpf = null;
		}
		
		System.out.println("Digite o salario");
		String salario = scanner.next();
		BigDecimal salarioB;
		if(salario.equalsIgnoreCase("0")) {
			salarioB = null;
		}else {
			salarioB = new BigDecimal(salario);
		}
		
		System.out.println("Digite a data (dd/MM/yyyy)");
		String data = scanner.next();
		LocalDate localDate;
		if(data.equalsIgnoreCase("NULL")) {
			localDate = null;
		}else {
			localDate = LocalDate.parse(data, formatter);
		}
		
		
		List<Funcionario> func = funcionarioRepository.findAll(Specification
				.where(
					SpecificationFuncionario.nome(nome))
					.or(SpecificationFuncionario.cpf(cpf))
					.or(SpecificationFuncionario.dataContratacao(localDate))
					.or(SpecificationFuncionario.salario(salarioB))
				);
		func.forEach(System.out::println);
	}
	

}
