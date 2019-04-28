package br.com.valhala.agenda.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Supplier;

import br.com.valhala.agenda.erro.AppException;

public abstract class ConnectionUtilsTest {
	
	public static final Supplier<Properties> propriedadesBancoSupplier = () -> {
		try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
			Properties properties = new Properties();
			properties.load(stream);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	};

	public static final Supplier<Connection> connectionSupplier = () -> {
		try {
			
			Properties propriedadesBanco = propriedadesBancoSupplier.get();
			
			Connection conexao = 
					DriverManager.getConnection(
							propriedadesBanco.getProperty("h2.url"),
							propriedadesBanco.getProperty("h2.user"), 
							propriedadesBanco.getProperty("h2.password"));
			return conexao;
		} catch (SQLException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

}
