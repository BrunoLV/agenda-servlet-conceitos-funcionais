package br.com.valhala.agenda.db.dao;

import br.com.valhala.agenda.db.FabricaConexoesTeste;
import br.com.valhala.agenda.db.FlywayTestUtils;
import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.modelo.Telefone;
import br.com.valhala.agenda.modelo.enums.EnumTipoTelefone;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Testes de integração com banco de dados para minupulação de Contato")
public class ContatoDaoTest {

	private Connection conexao;

	@BeforeAll
	public static void constroiBanco() {
		new FlywayTestUtils().migrarBancoTeste();
	}

	@AfterAll
	public static void destroiBanco() {
		new FlywayTestUtils().limparBancoTeste();
	}

	@BeforeEach
	public void inicializa() {
		conexao = new FabricaConexoesTeste().getConexao();
	}

	@AfterEach
	public void limpa() {
		deletaRegistrosDasTabelas();
	}

	@Test
	@DisplayName("Inserção de contato sem telefones.")
	public void deveInserirContatoNoBancoDeDados() throws SQLException {

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").build();

		Long idGerado = ContatoDao.insere(contato, conexao);

		assertNotNull(idGerado);

	}

	@Test
	@DisplayName("Atualização apenas nos dados do contato, não dos telefones")
	public void deveAtualizarContatoNoBancoDeDados() throws SQLException {

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").build();

		Long idGerado = ContatoDao.insere(contato, conexao);

		assertNotNull(idGerado);

		Contato contatoBanco = ContatoDao.buscaPorId(idGerado, conexao);

		assertNotNull(contatoBanco);
		assertThat(contatoBanco.getNome(), equalTo("Pedro Henrique Renan Enrico Drumond"));

		Contato contatoAtualiza = new Contato.Builder().id(contatoBanco.getId()).nome("Geraldo Renan Campos")
				.telefones(contatoBanco.getTelefones()).build();

		ContatoDao.atualiza(contatoAtualiza, conexao);

		contatoBanco = ContatoDao.buscaPorId(idGerado, conexao);

		assertNotNull(contatoBanco);
		assertThat(contatoBanco.getNome(), equalTo("Geraldo Renan Campos"));
	}

	@Test
	@DisplayName("Inserção de contato completo com telefones.")
	public void deveInserirContatoComTelefonesNoBancoDeDados() throws SQLException {

		Telefone telefone = new Telefone.Builder().ddd("011").numero("3764-7751").tipo(EnumTipoTelefone.RESIDENCIAL)
				.build();

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").comTelefone(telefone)
				.build();

		Long idGerado = ContatoDao.insere(contato, conexao);

		assertNotNull(idGerado);

		Contato contatoPersistido = ContatoDao.buscaPorId(idGerado, conexao);

		assertNotNull(contatoPersistido);
		assertNotNull(contatoPersistido.getTelefones());
		assertThat(contatoPersistido.getTelefones().size(), equalTo(1));
		assertThat(contatoPersistido.getTelefones(), hasItem(telefone));

	}

	@Test
	@DisplayName("Exclusão de contato no banco de dados.")
	public void deveRemoverContatoDoBancoDeDados() throws SQLException {

		Telefone telefone = new Telefone.Builder().ddd("011").numero("3764-7751").tipo(EnumTipoTelefone.RESIDENCIAL)
				.build();

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").comTelefone(telefone)
				.build();

		Long idGerado = ContatoDao.insere(contato, conexao);

		assertNotNull(idGerado);

		Contato contatoPersistido = ContatoDao.buscaPorId(idGerado, conexao);

		assertNotNull(contatoPersistido);

		ContatoDao.excluir(idGerado, conexao);

		contatoPersistido = ContatoDao.buscaPorId(idGerado, conexao);

		assertNull(contatoPersistido);
	}

	private void deletaRegistrosDasTabelas() {
		try {
			conexao.createStatement().execute("DELETE FROM telefone");
			conexao.createStatement().execute("DELETE FROM contato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
