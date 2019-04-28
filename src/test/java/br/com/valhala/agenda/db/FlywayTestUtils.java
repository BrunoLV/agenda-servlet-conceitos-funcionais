package br.com.valhala.agenda.db;

import java.util.Properties;

import org.flywaydb.core.Flyway;

public class FlywayTestUtils {

	private Flyway flyway;

	public FlywayTestUtils() {

		Properties propriedadesBancoDados = ConnectionUtilsTest.propriedadesBancoSupplier.get();

		flyway = Flyway.configure().dataSource(propriedadesBancoDados.getProperty("h2.url"),
				propriedadesBancoDados.getProperty("h2.user"), propriedadesBancoDados.getProperty("h2.password"))
				.load();
	}

	public void migrarBancoTeste() {
		flyway.clean();
		flyway.migrate();
	}

	public void limparBancoTeste() {
		flyway.clean();
	}

}
