package br.com.valhala.agenda.web.commands.contato;

import br.com.valhala.agenda.db.FabricaConexoesDataSouce;
import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.service.ContatoService;
import br.com.valhala.agenda.web.commands.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class ListaContatoCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ListaContatoCommand.class);

    private static final String URL_PAGINA_LISTAGEM = "/WEB-INF/paginas/contato/lista.jsp";
    private static final String ATRIBUTO_LISTA = "contatos";

    @Override
    public void execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException {
        try (Connection conexao = FabricaConexoesDataSouce.getIntance().getConexao()) {
            LOGGER.info("Executando comando de listagem de contatos.");
            ContatoService service = new ContatoService(conexao);
            Collection<Contato> contatos = service.lista();
            requisicao.setAttribute(ATRIBUTO_LISTA, contatos);
            requisicao.getRequestDispatcher(URL_PAGINA_LISTAGEM).forward(requisicao, resposta);
        } catch (SQLException e) {
            LOGGER.error("Ocorreu um erro na acao de listagem de cadastro de contato. Erro: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error("Ocorreu um erro na acao de listagem de cadastro de contato. Erro: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

}
