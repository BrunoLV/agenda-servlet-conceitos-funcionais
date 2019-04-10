<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/x-icon"
	href="${pageContext.servletContext.contextPath}/resources/images/favicon.ico" />
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css" />
<title>Agenda Servlet - Erro</title>
</head>
<body>
	<c:import url="/WEB-INF/layout/header.jsp" />
	<main class="w3-container">
	<p>Ocorreu um erro na requisicao.</p>
	<p>Passe as informações abaixo para o administrador do sistema.</p>
	<table class="w3-table w3-bordered w3-striped w3-card-4">
		<tbody>
			<tr>
				<th>Status code</th>
				<td><c:out value="${erro.statusCode}" /></td>
			</tr>
			<tr>
				<th>URI</th>
				<td><c:out value="${erro.uri}" /></td>
			</tr>
			<tr>
				<th>Mensagem</th>
				<td><c:out value="${erro.mensagem}" /></td>
			</tr>
		</tbody>
	</table>
	<br />
	<c:url var="home" value="/mvc">
		<c:param name="command" value="listarContatos" />
	</c:url> <a href="${home}" class="w3-button w3-green">Volta para o Lista de
		Contatos</a> </main>
	<c:import url="/WEB-INF/layout/footer.jsp" />
</body>
</html>