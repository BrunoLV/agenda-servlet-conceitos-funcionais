package br.com.valhala.agenda.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import br.com.valhala.agenda.db.ConnectionUtils;
import br.com.valhala.agenda.db.dao.ContatoDao;
import br.com.valhala.agenda.modelo.Contato;

public class ContatoService {

	public static Contato buscaPorId(final Long id) throws SQLException {
		try (Connection conexao = ConnectionUtils.connectionSupplier.get()) {
			return ContatoDao.buscaPorId(id, conexao);
		}
	}

	public static boolean deleta(final Long id) throws SQLException {
		try (Connection conexao = ConnectionUtils.connectionSupplier.get()) {
			try {
				ContatoDao.excluir(id, conexao);
				conexao.commit();
				return true;
			} catch (SQLException e) {
				conexao.rollback();
				throw e;
			}
		}
	}

	public static Collection<Contato> lista() throws SQLException {
		try (Connection conexao = ConnectionUtils.connectionSupplier.get()) {
			final Collection<Contato> lista = ContatoDao.lista(conexao);
			return lista;
		}
	}

	public static boolean salva(final Contato contato) throws SQLException {
		try (Connection conexao = ConnectionUtils.connectionSupplier.get()) {
			try {
				if (contato.getId() == null) {
					ContatoDao.insere(contato, conexao);
				} else {
					ContatoDao.atualiza(contato, conexao);
				}
				conexao.commit();
				return true;
			} catch (Exception e) {
				conexao.rollback();
				throw e;
			}
		}
	}

}
