<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/x-icon"
	href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico"
/>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
/>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/layout.css"
/>
<title>Agenda Servlet - Atualiza Contato <c:out
		value="${contato.id}"
	/></title>
</head>
<body>
	<c:import url="/WEB-INF/layout/header.jsp" />
	<div class="container">
		<main class="w3-container"> <c:url var="urlSalvar" value="/mvc" />
		<form action="${urlSalvar}" method="post" id="formAtualiza">
			<div class="w3-card-4">
				<header class="w3-container w3-blue">
				<h4>Dados do contato</h4>
				</header>
				<div class="w3-container">
					<input type="hidden" value="salvarContato" name="command" /> <input
						type="hidden" value="${contato.id}" name="id" id="idContato"
					>
					<div class="w3-row-padding" style="margin-top: 20px;">
						<div class="w3-half">
							<label for="nome" class="w3-text-blue"> <c:out
									value="Nome: "
								/></label> <input class="w3-input" type="text" value="${contato.nome}"
								name="nome" id="nome"
							>
						</div>
					</div>
					<input type="hidden" name="id" id="idTelefone">
					<div class="w3-row-padding">
						<div class="w3-col m1">
							<label for="ddd" class="w3-text-blue">DDD</label> <input
								class="w3-input" type="text" name="ddd" id="ddd"
							/>
						</div>
						<div class="w3-col m3">
							<label for="telefone" class="w3-text-blue">Número</label> <input
								class="w3-input" type="text" name="telefone" id="telefone"
							/>
						</div>
						<div class="w3-col m2">
							<label for="tipo" class="w3-text-blue">Tipo</label> <select
								name="tipo" id="tipo" class="w3-select"
							>
								<option label="Celular" value="CELULAR">Celular</option>
								<option label="Residencial" value="RESIDENCIAL">Residencial</option>
								<option label="Comercial" value="COMERCIAL">Comercial</option>
							</select>
						</div>
						<div class="w3-col m3">
							<input type="button" style="margin-top: 22px;"
								onclick="moduloManipulacao.adicionaTelefone();"
								class="w3-button w3-green" value="Adicionar"
							>
						</div>
					</div>
					<div id="telefones" style="margin-bottom: 20px; margin-top: 20px;">
						<c:if test="${not empty contato.telefones}">
							<table id="tabelaTelefones"
								class="w3-table w3-bordered w3-striped w3-card-4"
							>
								<thead>
									<tr class="w3-blue">
										<th>DDD</th>
										<th>Número</th>
										<th>Tipo</th>
										<th colspan="2" style="width: 10%;">Ações</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${contato.telefones}" var="c">
										<tr>
											<input type="hidden" value="${c.id}" />
											<td><c:out value="${c.ddd}" /></td>
											<td><c:out value="${c.numero}" /></td>
											<td><c:out value="${c.tipo}" /></td>
											<td>
												<button type="button" class="w3-button w3-teal"
													onclick="moduloManipulacao.editaTelefone(this)"
												>Editar</button>
											</td>
											<td>
												<button type="button" class="w3-button w3-red"
													onclick="moduloManipulacao.removeTelefone(this)"
												>Remover</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
					<input type="button" onclick="moduloManipulacao.enviaContato();"
						value="Salvar" class="w3-button w3-green"
						style="margin-bottom: 20px;"
					/>
				</div>
			</div>
		</form>
		</main>
		<div class="push"></div>
	</div>
	<c:import url="/WEB-INF/modals/modal-adicao-invalida.jsp" />
	<c:import url="/WEB-INF/modals/modal-contato-invalido.jsp" />
	<c:import url="/WEB-INF/layout/footer.jsp" />
	<script
		src="${pageContext.servletContext.contextPath}/resources/js/contato/manipulacao-contato.js"
		type="text/javascript"
	></script>
</body>
</html>