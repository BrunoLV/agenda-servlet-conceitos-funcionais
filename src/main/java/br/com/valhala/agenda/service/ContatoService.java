package br.com.valhala.agenda.service;

import br.com.valhala.agenda.db.dao.ContatoDao;
import br.com.valhala.agenda.modelo.Contato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class ContatoService {

    private Connection conexao;

    public ContatoService(final Connection conexao) {
        this.conexao = conexao;
    }

    public Contato buscaPorId(final Long id) throws SQLException {
        return ContatoDao.buscaPorId(id, conexao);
    }

    public boolean salva(final Contato contato) throws SQLException {
        boolean operacaoSucesso = false;
        try {
            if (contato.getId() == null) {
                ContatoDao.insere(contato, conexao);
            } else {
                ContatoDao.atualiza(contato, conexao);
            }
            operacaoSucesso = true;
            conexao.commit();
        } catch (SQLException e) {
            conexao.rollback();
            throw e;
        }
        return operacaoSucesso;
    }

    public Collection<Contato> lista() throws SQLException {
        Collection<Contato> lista = ContatoDao.lista(conexao);
        return lista;
    }

    public boolean deleta(final Long id) throws SQLException {
        boolean operacaoSucesso = false;
        try {
            ContatoDao.excluir(id, conexao);
            conexao.commit();
            operacaoSucesso = true;
        } catch (SQLException e) {
            conexao.rollback();
            throw e;
        }
        return operacaoSucesso;
    }

}
