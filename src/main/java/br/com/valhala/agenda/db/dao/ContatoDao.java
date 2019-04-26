package br.com.valhala.agenda.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.modelo.Telefone;
import br.com.valhala.agenda.modelo.enums.EnumTipoTelefone;

public final class  ContatoDao {

	private static final String SQL_ATUALIZA_CONTATO = "UPDATE contato SET nome = ? WHERE id = ?";
	private static final String SQL_ATUALIZA_TELEFONE_CONTATO = "UPDATE telefone SET ddd = ?, numero = ?, tipo = ? WHERE id = ?";
	private static final String SQL_BUSCA_ID = "SELECT * FROM contato WHERE id = ?";
	private static final String SQL_BUSCA_TELEFONE_CONTATO = "SELECT * FROM telefone WHERE id_contato = ?";
	private static final String SQL_EXCLUI_CONTATO = "DELETE FROM contato WHERE id = ?";
	private static final String SQL_EXCLUI_TELEFONE = "DELETE FROM telefone WHERE id = ?";
	private static final String SQL_EXCLUI_TELEFONES_CONTATO = "DELETE FROM telefone WHERE id_contato = ?";
	private static final String SQL_INSERE_CONTATO = "INSERT INTO contato (nome) VALUES (?)";
	private static final String SQL_INSERE_TELEFONE_CONTATO = "INSERT INTO telefone (ddd, numero, tipo, id_contato) VALUES (?, ?, ?, ?)";
	private static final String SQL_LISTA = "SELECT * FROM contato";
	private static final String SQL_PESQUISA_IDS_TELEFONES_CONTATO = "SELECT id FROM telefone WHERE id_contato = ?";

	private static BiConsumer<Contato, Connection> atualizaContatoConsumer = (contato, conexao) -> {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_ATUALIZA_CONTATO)) {
			stmt.setString(1, contato.getNome());
			stmt.setLong(2, contato.getId());
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	private static Function<Long, BiFunction<Telefone, Connection, Optional<Long>>> insereTelefoneContatoConsumer = id -> (
			telefone, conexao) -> {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_INSERE_TELEFONE_CONTATO,
				java.sql.Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, telefone.getDdd());
			stmt.setString(2, telefone.getNumero());
			stmt.setString(3, telefone.getTipo().name());
			stmt.setLong(4, id);
			stmt.execute();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					return Optional.of(rs.getLong(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Optional.empty();
	};

	private static BiConsumer<Telefone, Connection> atualizaTelefoneConsumer = (telefone, conexao) -> {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_ATUALIZA_TELEFONE_CONTATO)) {
			stmt.setString(1, telefone.getDdd());
			stmt.setString(2, telefone.getNumero());
			stmt.setString(3, telefone.getTipo().name());
			stmt.setLong(4, telefone.getId());
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	private static Function<Long, BiConsumer<Collection<Telefone>, Connection>> insereTelefonesContatoConsumer = id -> (
			telefones, conexao) -> {
		telefones.stream().forEach(t -> insereTelefoneContatoConsumer.apply(id).apply(t, conexao));
	};

	private static BiFunction<Contato, Connection, Collection<Long>> atualizaTelefonesContatoConsumer = (contato,
			conexao) -> {
		final Collection<Long> idsPersistidos = new HashSet<>();
		if (contato.getTelefones() != null) {
			contato.getTelefones().stream().forEach(t -> {
				if (t.getId() != null) {
					atualizaTelefoneConsumer.accept(t, conexao);
					idsPersistidos.add(contato.getId());
				} else {
					insereTelefoneContatoConsumer.apply(contato.getId()).apply(t, conexao)
							.ifPresent(idTelefone -> idsPersistidos.add(idTelefone));
				}
			});
		}
		return idsPersistidos;
	};

	private static BiConsumer<Long, Connection> deletaTelefoneConsumer = (id, conexao) -> {
		try (PreparedStatement stmtDelete = conexao.prepareStatement(SQL_EXCLUI_TELEFONE)) {
			stmtDelete.setLong(1, id);
			stmtDelete.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	private static BiFunction<Long, Connection, Collection<Long>> consultaIdsTelefonesContatoFunction = (id,
			conexao) -> {
		final Set<Long> idsRecuperadosBanco = new HashSet<>();
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_PESQUISA_IDS_TELEFONES_CONTATO)) {
			stmt.setLong(1, id);
			stmt.executeQuery();
			try (ResultSet rs = stmt.getResultSet()) {
				while (rs.next()) {
					idsRecuperadosBanco.add(rs.getLong(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return idsRecuperadosBanco;
	};

	private static Function<Long, BiConsumer<Collection<Long>, Connection>> deletaTelefonesContatoFunction = id -> (ids,
			conexao) -> {
		consultaIdsTelefonesContatoFunction.apply(id, conexao).stream().filter(idTelefone -> !ids.contains(id))
				.forEach(idTelefone -> deletaTelefoneConsumer.accept(idTelefone, conexao));
	};

	private static BiFunction<Long, Connection, Optional<Contato>> buscaDadosContatoIdFunction = (id, conexao) -> {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_BUSCA_ID)) {
			stmt.setLong(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return Optional.of(new Contato.Builder().id(rs.getLong("id")).nome(rs.getString("nome")).build());
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Optional.empty();
	};

	private static BiFunction<Contato, Connection, Contato> completaDadosContatoFunction = (contato, conexao) -> {

		final Collection<Telefone> telefones = new HashSet<>();

		try (PreparedStatement stmtTelefone = conexao.prepareStatement(SQL_BUSCA_TELEFONE_CONTATO)) {
			stmtTelefone.setLong(1, contato.getId());

			try (ResultSet rsTelefone = stmtTelefone.executeQuery()) {
				while (rsTelefone.next()) {
					telefones.add(new Telefone.Builder().id(rsTelefone.getLong("id")).ddd(rsTelefone.getString("ddd"))
							.numero(rsTelefone.getString("numero"))
							.tipo(EnumTipoTelefone.valueOf(rsTelefone.getString("tipo"))).build());
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return contato.adicionaTelefones(telefones);

	};
	private static BiConsumer<Long, Connection> deletaTelefonesConsumer = (id, conexao) -> {

		try (PreparedStatement stmt = conexao.prepareStatement(SQL_EXCLUI_TELEFONES_CONTATO)) {
			stmt.setLong(1, id);
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	};

	private static BiConsumer<Long, Connection> deletaContatoConsumer = (id, conexao) -> {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_EXCLUI_CONTATO)) {
			stmt.setLong(1, id);
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	private static BiFunction<Contato, Connection, Optional<Long>> insereContatoFunction = (contato, conexao) -> {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_INSERE_CONTATO,
				java.sql.Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, contato.getNome());
			stmt.execute();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					return Optional.of(rs.getLong(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Optional.empty();
	};

	private static Function<Connection, Optional<Collection<Contato>>> listaContatosFunction = (conexao) -> {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_LISTA)) {
			try (ResultSet rs = stmt.executeQuery()) {
				Collection<Contato> contatos = new ArrayList<>	();
				while (rs.next()) {
					Contato contato = new Contato.Builder().id(rs.getLong("id")).nome(rs.getString("nome")).build();
					contatos.add(contato);
				}
				if (!contatos.isEmpty()) {
					return Optional.of(contatos);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Optional.empty();
	};

	private ContatoDao() {
		super();
	}

	public static void atualiza(final Contato contato, final Connection conexao) {
		atualizaContatoConsumer.accept(contato, conexao);
		deletaTelefonesContatoFunction.apply(contato.getId())
				.accept(atualizaTelefonesContatoConsumer.apply(contato, conexao), conexao);
	}

	public static Contato buscaPorId(final Long id, final Connection conexao) {
		AtomicReference<Contato> contato = new AtomicReference<>();
		buscaDadosContatoIdFunction.apply(id, conexao).ifPresent(
				c -> contato.set(new Contato.Builder().from(completaDadosContatoFunction.apply(c, conexao)).build()));
		return contato.get();
	}

	public static void excluir(final Long id, final Connection conexao) throws SQLException {
		deletaTelefonesConsumer.andThen(deletaContatoConsumer).accept(id, conexao);
	}

	public static Long insere(final Contato contato, final Connection conexao) throws SQLException {
		AtomicReference<Long> idGerado = new AtomicReference<>();
		insereContatoFunction.apply(contato, conexao).ifPresent(id -> {
			insereTelefonesContatoConsumer.apply(id).accept(contato.getTelefones(), conexao);
			idGerado.set(id);
		});
		return idGerado.get();
	}

	public static Collection<Contato> lista(final Connection conexao) throws SQLException {
		return listaContatosFunction.apply(conexao).orElse(Collections.emptyList());
	}

}
