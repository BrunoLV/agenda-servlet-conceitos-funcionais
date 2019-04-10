package br.com.valhala.agenda.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexoesTeste implements FabricaConexoes {

    @Override
    public Connection getConexao() {

        try {
            Connection conexao = DriverManager.getConnection(PropriedadesBancoTesteUtils.getUrl(),
                    PropriedadesBancoTesteUtils.getUsuario(), PropriedadesBancoTesteUtils.getSenha());
            return conexao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
