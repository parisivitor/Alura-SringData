package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CurdCargoService {

	private Boolean system = true;
	private final CargoRepository repository;
	
	public CurdCargoService(CargoRepository repository) {
		this.repository = repository;
	}
	
	public void inicial(Scanner scanner, Integer action) {
		while(system) {
			System.out.println("<Cargos>");
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
		System.out.println("Descricao do cargo");
		String descricao = scanner.nextLine();
		descricao += scanner.nextLine();	
		
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		repository.save(cargo);
		System.out.println("Salvo!");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Id");
		Integer id = scanner.nextInt();
		String descricao = scanner.nextLine();
		descricao += scanner.nextLine();	
		
		Cargo cargo = new Cargo();
		cargo.setId(id);
		cargo.setDescricao(descricao);
		repository.save(cargo);
		System.out.println("Atualizado");
	}
	
	private void visualizar(Scanner scanner) {
		Iterable<Cargo> cargos = repository.findAll();
		cargos.forEach(cargo -> System.out.println(cargo));
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id");
		Integer id = scanner.nextInt();
		repository.deleteById(id);
		System.out.println("Campo deletado com o valor de id=" + id);
	}

}
