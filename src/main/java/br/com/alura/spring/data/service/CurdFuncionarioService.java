package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CurdFuncionarioService {

	private Boolean system = true;
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository unidadeRepository;
	
	public CurdFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, UnidadeTrabalhoRepository unidadeRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeRepository = unidadeRepository;
	}
	
	public void inicial(Scanner scanner, Integer action) {
		while(system) {
			System.out.println("<Funcionarios>");
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
		System.out.println("Nome do funcionario");
		String nome = scanner.nextLine();
		nome += scanner.nextLine();
		
		System.out.println("Cpf do funcionaio");
		String cpf = scanner.nextLine();
		cpf += scanner.nextLine();
		
		System.out.println("Salario do funcionario");
		BigDecimal salario = scanner.nextBigDecimal();
		
		System.out.println("Digite o id do cargo");
        Integer cargoId = scanner.nextInt();
        
        List<UnidadeTrabalho> unidades = unidade(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.now());
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo!");
	}
	
	private List<UnidadeTrabalho> unidade(Scanner scanner) {
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();
		
		while(isTrue) {
			System.out.println("Digite o Id da unidade (Para sair digite 0)");
			Integer unidadeId = scanner.nextInt();
			
			if(unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeRepository.findById(unidadeId);
				unidades.add(unidade.get());
			}
			else
				isTrue = false;
		}
		return unidades;
	}

	private void atualizar(Scanner scanner) {
		System.out.println("Id");
		Integer id = scanner.nextInt();
		
		System.out.println("Nome do funcionario");
		String nome = scanner.nextLine();
		nome += scanner.nextLine();
		
		System.out.println("Cpf do funcionaio");
		String cpf = scanner.nextLine();
		cpf += scanner.nextLine();
		
		System.out.println("Salario do funcionario");
		BigDecimal salario = scanner.nextBigDecimal();
		
		System.out.println("Digite o cargoId");
        Integer cargoId = scanner.nextInt();
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.now());
		funcionario.setCargo(cargo.get());
		
		funcionarioRepository.save(funcionario);
		System.out.println("Atualizado");
	}
	
	private void visualizar(Scanner scanner) {
		System.out.println("Qual pagina voce deseja visualizar");
		Integer page = scanner.nextInt();
		
//		Pageable pegeable = PageRequest.of(page, 5, Sort.unsorted());//fazer paginacao por ordem padrao
		Pageable pegeable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC,"nome"));//fazer paginacao por nome em ordem DESC ou ASC, onde nome é o nome do atributo na classe funcionario
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pegeable);
		
		System.out.println(funcionarios);
		System.out.println("Pagina atual: " + funcionarios.getNumber());
		System.out.println("Total de elementos: " + funcionarios.getTotalElements());
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id");
		Integer id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Campo deletado com o valor de id=" + id);
	}

}
