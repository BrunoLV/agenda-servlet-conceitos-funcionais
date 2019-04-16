package br.com.valhala.agenda.web.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.valhala.agenda.adapters.json.NumberTypeAdapter;
import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.service.ContatoService;

public final class MvcCommands {

	private static final String PARAMETRO_JSON = "json";
	private static final String PARAMETRO_ID = "id";

	private static final String ATRIBUTO_LISTA = "contatos";
	private static final String ATRIBUTO_CONTATO = "contato";

	private static final String URL_ACAO_LISTAGEM = "/mvc?command=listarContatos";

	private static final String URL_PAGINA_LISTAGEM = "/WEB-INF/paginas/contato/lista.jsp";
	private static final String URL_PAGINA_EDICAO = "/WEB-INF/paginas/contato/atualiza.jsp";
	private static final String URL_PAGINA_INCLUSAO = "/WEB-INF/paginas/contato/novo.jsp";

	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Long.class, new NumberTypeAdapter())
			.create();

	private static final BiConsumer<HttpServletRequest, HttpServletResponse> salvaContato = (requisicao, resposta) -> {
		try {
			final Contato contato = GSON.fromJson(requisicao.getParameter(PARAMETRO_JSON), Contato.class);
			boolean sucesso = ContatoService.salva(contato);
			if (sucesso)
				resposta.sendRedirect(requisicao.getContextPath() + URL_ACAO_LISTAGEM);
		} catch (SQLException | IOException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

	private static final BiConsumer<HttpServletRequest, HttpServletResponse> editaContato = (requisicao, resposta) -> {
		try {
			final Long id = Long.parseLong(requisicao.getParameter(PARAMETRO_ID));
			final Contato contato = ContatoService.buscaPorId(id);
			requisicao.setAttribute(ATRIBUTO_CONTATO, contato);
			requisicao.getRequestDispatcher(URL_PAGINA_EDICAO).forward(requisicao, resposta);
		} catch (SQLException | IOException | ServletException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

	private static final BiConsumer<HttpServletRequest, HttpServletResponse> listaContatos = (requisicao, resposta) -> {
		try {
			final Collection<Contato> contatos = ContatoService.lista();
			requisicao.setAttribute(ATRIBUTO_LISTA, contatos);
			requisicao.getRequestDispatcher(URL_PAGINA_LISTAGEM).forward(requisicao, resposta);
		} catch (SQLException | ServletException | IOException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

	private static final BiConsumer<HttpServletRequest, HttpServletResponse> deletaContato = (requisicao, resposta) -> {
		try {
			final Long id = Long.parseLong(requisicao.getParameter(PARAMETRO_ID));
			Boolean sucesso = ContatoService.deleta(id);
			if (sucesso) {
				resposta.sendRedirect(requisicao.getContextPath() + URL_ACAO_LISTAGEM);
			}
		} catch (SQLException | IOException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

	private static final BiConsumer<HttpServletRequest, HttpServletResponse> novoContato = (requisicao, resposta) -> {
		try {
			Contato contato = new Contato.Builder().build();
			requisicao.setAttribute(ATRIBUTO_CONTATO, contato);
			requisicao.getRequestDispatcher(URL_PAGINA_INCLUSAO).forward(requisicao, resposta);
		} catch (IOException | ServletException e) {
			throw new AppException(e.getMessage(), e);
		}
	};

	public static final Function<String, BiConsumer<HttpServletRequest, HttpServletResponse>> atendeRequisicao = comando -> (
			requisicao, resposta) -> {

		switch (comando) {
		case "cadastrarNovoContato":
			novoContato.accept(requisicao, resposta);
			break;
		case "editarContato":
			editaContato.accept(requisicao, resposta);
			break;
		case "excluirContato":
			deletaContato.accept(requisicao, resposta);
			break;
		case "listarContatos":
			listaContatos.accept(requisicao, resposta);
			break;
		case "salvarContato":
			salvaContato.accept(requisicao, resposta);
			break;
		default:
		}

	};

}
