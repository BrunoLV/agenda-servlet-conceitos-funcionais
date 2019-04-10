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
<title>Agenda Servlet - Contatos</title>
</head>
<body>
	<c:import url="/WEB-INF/layout/header.jsp" />
	<div class="container">
		<main class="w3-container"> <c:url var="urlNovo" value="/mvc">
			<c:param name="command" value="cadastrarNovoContato" />
		</c:url> <a href="${urlNovo}" class="w3-button w3-brown"> <c:out
				value="Novo"
			/>
		</a> <br />
		<br />
		<c:if test="${empty contatos}">
			<c:out value="Não há registros para visualização" />
		</c:if> <c:if test="${not empty contatos }">
			<table class="w3-table w3-bordered w3-striped w3-card-4">
				<thead>
					<tr class="w3-blue">
						<th><c:out value="ID" /></th>
						<th><c:out value="NOME"></c:out></th>
						<th colspan="2" style="width: 10%;">Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contatos}" var="c">
						<tr>
							<td><c:out value="${c.id}" /></td>
							<td><c:out value="${c.nome}" /></td>
							<td><c:url var="urlEditar" value="/mvc">
									<c:param name="command" value="editarContato" />
									<c:param name="id" value="${c.id}" />
								</c:url> <a href="${urlEditar}" class="w3-button w3-teal"> <c:out
										value="Editar"
									/>
							</a></td>
							<td><c:url var="urlExcluir" value="/mvc">
									<c:param name="command" value="excluirContato" />
									<c:param name="id" value="${c.id}" />
								</c:url><a href="${urlExcluir}" class="w3-button w3-red"> <c:out
										value="Excluir"
									/>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if> </main>
		<div class="push"></div>
	</div>
	<c:import url="/WEB-INF/layout/footer.jsp" />
</body>
</html>