package br.com.valhala.agenda.db;

import br.com.valhala.agenda.erro.AppException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class FabricaConexoesDataSouce implements FabricaConexoes {

    private static final FabricaConexoesDataSouce instance = new FabricaConexoesDataSouce();
    private DataSource dataSource;

    protected FabricaConexoesDataSouce() {
        try {
            Context contextInicial = new InitialContext();
            Context contextoAmbiente = (Context) contextInicial.lookup("java:/comp/env");
            dataSource = (DataSource) contextoAmbiente.lookup("jdbc/agenda");
        } catch (NamingException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    public static FabricaConexoesDataSouce getIntance() {
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Connection getConexao() {
        try {
            Connection conexao = dataSource.getConnection();
            conexao.setAutoCommit(false);
            return conexao;
        } catch (SQLException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

}
