package br.com.valhala.agenda.web.commands.contato;

import br.com.valhala.agenda.db.FabricaConexoesDataSouce;
import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.service.ContatoService;
import br.com.valhala.agenda.web.commands.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ExcluirContatoCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ExcluirContatoCommand.class);

    private static final String URL_ACAO_LISTAGEM = "/mvc?command=listarContatos";

    @Override
    public void execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException {

        try (Connection conexao = FabricaConexoesDataSouce.getIntance().getConexao()) {

            LOGGER.info("Executando comando para exclusao de cadastro de contato.");

            Long id = Long.parseLong(requisicao.getParameter("id"));

            ContatoService service = new ContatoService(conexao);

            Boolean sucesso = service.deleta(id);

            if (sucesso) {
                resposta.sendRedirect(requisicao.getContextPath() + URL_ACAO_LISTAGEM);
            }

        } catch (SQLException e) {

            LOGGER.error("Ocorreu um erro na acao de exclusao de cadastro de contato. Erro: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);

        } catch (IOException e) {

            LOGGER.error("Ocorreu um erro na acao de exclusao de cadastro de contato. Erro: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);

        }

    }

}
