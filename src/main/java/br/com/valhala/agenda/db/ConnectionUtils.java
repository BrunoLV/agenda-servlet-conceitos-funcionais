package br.com.valhala.agenda.db;

import br.com.valhala.agenda.erro.AppException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public abstract class ConnectionUtils {

	public static final Supplier<DataSource> dataSourceSupplier = () -> {
		try {

			Context contextInicial = new InitialContext();
			Context contextoAmbiente = (Context) contextInicial.lookup("java:/comp/env");
			DataSource dataSource = (DataSource) contextoAmbiente.lookup("jdbc/agenda");

			return dataSource;

		} catch (NamingException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

	public static final Supplier<Connection> connectionSupplier = () -> {
		try {

			Connection conexao = dataSourceSupplier.get().getConnection();
			conexao.setAutoCommit(false);

			return conexao;

		} catch (SQLException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

}
