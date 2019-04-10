<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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
<title>Agenda Servlet - Novo Contato</title>
</head>
<body>
	<c:import url="/WEB-INF/layout/header.jsp" />
	<div class="container">
		<main class="w3-container"> <c:url var="urlSalvar" value="/mvc" />
		<form id="formNovo" action="${urlSalvar}" method="post">
			<div class="w3-card-4">
				<header class="w3-container w3-blue">
					<h4>Dados do contato</h4>
				</header>
				<div class="w3-container">
					<input type="hidden" value="salvarContato" name="command" />
					<div class="w3-row-padding" style="margin-top: 20px;">
						<div class="w3-half">
							<label for="nome" class="w3-text-blue"> <c:out
									value="Nome*"
								/></label> <input type="text" class="w3-input" value="${contato.nome}"
								name="nome" id="nome"
							>
						</div>
					</div>
					<div class="w3-row-padding">
						<div class="w3-col m1">
							<label for="ddd" class="w3-text-blue"><c:out value="DDD*" /></label>
							<input type="text" class="w3-input" name="ddd" id="ddd" />
						</div>
						<div class="w3-col m3">
							<label for="telefone" class="w3-text-blue"><c:out
									value="Número*"
								/></label> <input type="text" class="w3-input" name="telefone"
								id="telefone"
							/>
						</div>
						<div class="w3-col m2">
							<label for="tipo" class="w3-text-blue">Tipo*</label> <select
								name="tipo" class="w3-select" id="tipo"
							>
								<option label="Celular" value="CELULAR"><c:out
										value="Celular"
									/></option>
								<option label="Residencial" value="RESIDENCIAL"><c:out
										value="Residencial"
									/></option>
								<option label="Comercial" value="COMERCIAL"><c:out
										value="Comercial"
									/></option>
							</select>
						</div>
						<div class="w3-col m3">
							<input type="button" style="margin-top: 22px;"
								onclick="moduloManipulacao.adicionaTelefone();"
								class="w3-button w3-green" value="Adicionar"
							>
						</div>
					</div>
					<div id="telefones" style="margin-bottom: 20px; margin-top: 20px;"></div>
					<hr />
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