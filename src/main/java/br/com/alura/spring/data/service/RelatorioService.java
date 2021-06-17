package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatorioService {
	private Boolean system = true;
	private final FuncionarioRepository funcionarioRepository;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	
	public RelatorioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner, Integer action) {
		while(system) {
			System.out.println("<Funcionarios>");
			System.out.println("Escolha a opção:");
			System.out.println("0 - Sair");
			System.out.println("1 - Buscar por nome");
			System.out.println("2 - Buscar por nome, Salario maior que x e Data de contratacao");
			System.out.println("3 - Buscar Data de contratacao maior que 'x'");
			System.out.println("4 - Buscar por Salario");
			action = scanner.nextInt();
			
			switch (action) {
			case 1:
				BuscarFuncionarioPorNome(scanner);
				break;
				
			case 2:
				BuscarFuncionarioPorNomeSalarioDataContratacao(scanner);
				break;
			case 3:
				BuscaFuncionarioPorDataMaior(scanner);
				break;
			case 4:
				PesquisaFuncionarioSalariuo();
				break;
				
			default:
				system = false;
				break;
			}
		}
	}
	
	private void BuscarFuncionarioPorNome(Scanner scanner) {
		System.out.println("Nome do funcionario");
		String nome = scanner.nextLine();
		nome += scanner.nextLine();
		
		List<Funcionario> func = funcionarioRepository.findByNome(nome);
		func.forEach(System.out::println);
	}
	
	private void BuscarFuncionarioPorNomeSalarioDataContratacao(Scanner scanner) {
		System.out.println("Nome do funcionario");
		String nome = scanner.nextLine();
		nome += scanner.nextLine();
		
		System.out.println("Digite a data da contratacao (dd/mm/aaaa)");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data,formatter);
		
		System.out.println("Qual salario deseja pesquisar");
		BigDecimal salario = scanner.nextBigDecimal();
		
		List<Funcionario> func = funcionarioRepository.findNomeSalarioDataContratacao(nome, salario, localDate);
		func.forEach(System.out::println);
	}
	
	private void BuscaFuncionarioPorDataMaior(Scanner scanner) {
		System.out.println("Digite a data da contratacao (dd/mm/aaaa)");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data,formatter);
		
		List<Funcionario> func = funcionarioRepository.findByDataContratacaoGreaterThan(localDate);
		func.forEach(System.out::println);
	}
	
	private void PesquisaFuncionarioSalariuo() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f-> System.out.println("Funcionario{ id:" + f.getId() + " | Nome: " + f.getNome() + " | Salario: " + f.getSalario() + " }"));
	}
}
