package br.com.valhala.agenda.db;

import org.flywaydb.core.Flyway;

public class FlywayTestUtils {

	private Flyway flyway;

	public FlywayTestUtils() {
		flyway = Flyway.configure().dataSource(PropriedadesBancoTesteUtils.getUrl(),
				PropriedadesBancoTesteUtils.getUsuario(), PropriedadesBancoTesteUtils.getSenha()).load();
	}

	public void migrarBancoTeste() {
		flyway.clean();
		flyway.migrate();
	}

	public void limparBancoTeste() {
		flyway.clean();
	}

}
