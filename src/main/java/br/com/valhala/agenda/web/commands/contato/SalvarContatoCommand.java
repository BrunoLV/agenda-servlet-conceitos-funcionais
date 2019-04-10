package br.com.valhala.agenda.web.commands.contato;

import br.com.valhala.agenda.adapters.json.NumberTypeAdapter;
import br.com.valhala.agenda.db.FabricaConexoesDataSouce;
import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.service.ContatoService;
import br.com.valhala.agenda.web.commands.Command;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SalvarContatoCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SalvarContatoCommand.class);

    private static final String PARAMETRO_JSON = "json";
    private static final String URL_ACAO_LISTAGEM = "/mvc?command=listarContatos";

    @Override
    public void execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException {

        try (Connection conexao = FabricaConexoesDataSouce.getIntance().getConexao()) {
            try {

                LOGGER.info("Executando comando para salvar informacoes de contato na base do sistema.");

                Gson gson = new GsonBuilder().registerTypeAdapter(Long.class, new NumberTypeAdapter()).create();
                Contato contato = gson.fromJson(requisicao.getParameter(PARAMETRO_JSON), Contato.class);

                ContatoService service = new ContatoService(conexao);

                boolean sucesso = service.salva(contato);

                if (sucesso) {
                    resposta.sendRedirect(requisicao.getContextPath() + URL_ACAO_LISTAGEM);
                }

            } catch (SQLException e) {

                LOGGER.error("Ocorreu um erro na acao de salvar informacoes de contato. Erro: " + e.getMessage(), e);
                throw new AppException(e.getMessage(), e);

            } catch (IOException e) {

                LOGGER.error("Ocorreu um erro na acao de salvar informacoes de contato. Erro: " + e.getMessage(), e);
                throw new AppException(e.getMessage(), e);

            }
        } catch (SQLException e) {

            LOGGER.error("Ocorreu um erro na acao de salvar informacoes de contato. Erro: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);

        }

    }

}
