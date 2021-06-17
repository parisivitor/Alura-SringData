package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

@Repository
//public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer>{
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario>{  //faz paginação
	List<Funcionario> findByNome(String nome);
	List<Funcionario> findByNome(String nome, Pageable pageable); //busca por nome, com paginacao
	
	
	List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario, LocalDate data);
	// os dois modos faz a mesma busca, porem o de baixo é uma JPQL
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :data")
	List<Funcionario> findNomeSalarioDataContratacao(String nome, BigDecimal salario, LocalDate data);
	
	
	List<Funcionario> findByCargoDescricao(String descricao);
	// os dois modos faz a mesma busca, porem o de baixo é uma JPQL
	@Query("SELECT f FROM Funcionario f JOIN f.cargo c WHERE c.descricao = :descricao")
	List<Funcionario> findByCargoPelaDescricao(String descricao);
	
	//Necessitou colocar o _ pois Unidade de trabalho é uma entidade com nome composto
	List<Funcionario> findByUnidadeTrabalhos_Descricao(String descricao);
	// os dois modos faz a mesma busca, porem o de baixo é uma JPQL
	@Query("SELECT f FROM Funcionario f JOIN f.unidadeTrabalhos u WHERE u.descricao = :descricao")
	List<Funcionario> findByUnidadeTrabalhosDescricao(String descricao);
	
	
//	Utilizando query nativa do banco de dados, assim utilizada o a sintaxe sql
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true )   // !! NAO FUNCIONO! !! 
	List<Funcionario> findByDataContratacaoMaior(LocalDate data);
	List<Funcionario> findByDataContratacaoGreaterThan(LocalDate data);
	
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();
}	
