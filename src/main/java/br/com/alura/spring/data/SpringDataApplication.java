package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CurdCargoService;
import br.com.alura.spring.data.service.CurdFuncionarioService;
import br.com.alura.spring.data.service.CurdUnidadeTrabalhoService;
import br.com.alura.spring.data.service.RelatorioFuncionarioDinamico;
import br.com.alura.spring.data.service.RelatorioService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner{
	private final CurdCargoService cargoService;	
	private final CurdFuncionarioService funcionarioService;
	private final CurdUnidadeTrabalhoService unidadeTrabalhoService;
	private final RelatorioService relatorioService;
	private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamico;
	private Boolean system = true;
	
//	injeção de dependencia para gerar um "new" no CargoRepository
	public SpringDataApplication(CurdCargoService cargoService,CurdFuncionarioService funcionarioService,CurdUnidadeTrabalhoService unidadeTrabalhoService, 
								RelatorioService relatorioService, RelatorioFuncionarioDinamico relatorioFuncionarioDinamico) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeTrabalhoService = unidadeTrabalhoService;
		this.relatorioService = relatorioService;
		this.relatorioFuncionarioDinamico = relatorioFuncionarioDinamico;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

//	Metodo necessario da interface CommandLineRunner
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		while(system) {
			System.out.println("Em que local voce quer realizar ações");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Funcionario");
			System.out.println("3 - Unidade de Trabalho");
			System.out.println("4 - Relatorios");
			System.out.println("5 - Relatorios Dinamicos");
			
			int action = scanner.nextInt();
			if(action == 1)
				cargoService.inicial(scanner,action);
			else if(action == 2 )
				funcionarioService.inicial(scanner, action);
			else if(action == 3 )
				unidadeTrabalhoService.inicial(scanner, action);
			else if(action == 4)
				relatorioService.inicial(scanner, action);
			else if(action == 5)
				relatorioFuncionarioDinamico.inicial(scanner, action);
			else
				system = false;
		}
		
//		 Cargo cargo = new Cargo();  
//		 cargo.setDescricao("Desenvolvedor Java Pleno"); //Cria um novo cargo
//		 repository.save(cargo); //salva no banco de dados
	}
	
	

}
