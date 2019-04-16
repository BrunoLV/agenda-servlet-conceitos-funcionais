package br.com.valhala.agenda.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.web.commands.MvcCommands;

@WebServlet(urlPatterns = { "/mvc" })
public class MvcServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String COMMAND = "command";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String comando = request.getParameter(COMMAND);
		if (comando != null && !comando.isEmpty()) {
			MvcCommands.atendeRequisicao.apply(comando).accept(request, response);
		} else {
			throw new AppException("Não foi enviado nenhum comando para execução na aplicação.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String comando = request.getParameter(COMMAND);
		if (comando != null && !comando.isEmpty()) {
			MvcCommands.atendeRequisicao.apply(comando).accept(request, response);
		} else {
			throw new AppException("Não foi enviado nenhum comando para execução na aplicação.");
		}
	}

}