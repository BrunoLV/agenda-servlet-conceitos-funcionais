package br.com.valhala.agenda.db;

import java.sql.Connection;

public interface FabricaConexoes {

	Connection getConexao();

}
