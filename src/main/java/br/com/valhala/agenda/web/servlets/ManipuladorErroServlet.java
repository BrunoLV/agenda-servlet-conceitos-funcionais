package br.com.valhala.agenda.web.servlets;

import br.com.valhala.agenda.erro.Erro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/error"})
public class ManipuladorErroServlet extends HttpServlet {

    private static final long serialVersionUID = 7701190140442887399L;

    private static final String NOME_ATRIBUTO_ERRO = "erro";
    private static final String URL_PAGINA_ERRO = "/WEB-INF/paginas/error.jsp";

    private static final String ATRIBUTO_MENSAGEM_ERRO = "javax.servlet.error.message";
    private static final String ATRIBUTO_URI_ERRO = "javax.servlet.error.request_uri";
    private static final String ATRIBUTO_STATUS_CODE = "javax.servlet.error.status_code";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mensagem = (String) req.getAttribute(ATRIBUTO_MENSAGEM_ERRO);
        String uri = (String) req.getAttribute(ATRIBUTO_URI_ERRO);
        Integer code = (Integer) req.getAttribute(ATRIBUTO_STATUS_CODE);
        Erro erro = new Erro.Builder().mensagem(mensagem).uri(uri).statusCode(code).build();
        req.setAttribute(NOME_ATRIBUTO_ERRO, erro);
        req.getRequestDispatcher(URL_PAGINA_ERRO).forward(req, resp);
    }

}
