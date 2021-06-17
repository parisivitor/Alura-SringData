package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CurdUnidadeTrabalhoService {

	private Boolean system = true;
	private final UnidadeTrabalhoRepository repository;
	
	public CurdUnidadeTrabalhoService(UnidadeTrabalhoRepository repository) {
		this.repository = repository;
	}
	
	public void inicial(Scanner scanner, Integer action) {
		while(system) {
			System.out.println("<Unidade de Trabalho>");
			System.out.println("Escolha a opção:");
			System.out.println("0 - Sair");
			System.out.println("1 - Adicionar");
			System.out.println("2 - Modificar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			action = scanner.nextInt();
			
			switch (action) {
			case 1:
				salvar(scanner);
				break;
				
			case 2:
				atualizar(scanner);
				break;
				
			case 3:
				visualizar(scanner);
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
		
	}
	
	private void salvar(Scanner scanner) {
		System.out.println("Descricao da unidade de trabalho");
		String descricao = scanner.nextLine();
		descricao += scanner.nextLine();
		
		System.out.println("Local unidade de trabalho");
		String end = scanner.nextLine();
		end += scanner.nextLine();	
		
		UnidadeTrabalho unidade = new UnidadeTrabalho();
		unidade.setDescricao(descricao);
		unidade.setEndereco(end);
		repository.save(unidade);
		System.out.println("Salvo!");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Id");
		Integer id = scanner.nextInt();
		
		System.out.println("Descricao da unidade de trabalho");
		String descricao = scanner.nextLine();
		descricao += scanner.nextLine();
		
		System.out.println("Local unidade de trabalho");
		String end = scanner.nextLine();
		end += scanner.nextLine();
		
		UnidadeTrabalho unidade = new UnidadeTrabalho();
		unidade.setId(id);
		unidade.setDescricao(descricao);
		unidade.setEndereco(end);
		repository.save(unidade);
		System.out.println("Atualizado");
	}
	
	private void visualizar(Scanner scanner) {
		Iterable<UnidadeTrabalho> unidades = repository.findAll();
		unidades.forEach(unidade -> System.out.println(unidade));
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id");
		Integer id = scanner.nextInt();
		repository.deleteById(id);
		System.out.println("Campo deletado com o valor de id=" + id);
	}

}
