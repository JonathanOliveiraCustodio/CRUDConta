<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css" />
<title>Conta Poupanca</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<br />
	<div align="center" class="container">
		<form action="operacoes" method="post">
			<p class="title">
				<b>Operações Bancária</b>
			</p>
			<table>
				<tr>
				<tr>
					<td style="width: 20%;"><label for="codigo">Número
							Conta:</label></td>
					<td colspan="2" style="width: 60%;"><input
						class="input_data_id" type="number" min="0" step="1" id="numConta"
						name="numConta" placeholder="Número da Conta"
						value='<c:out value="${operacoes.numConta}"></c:out>'></td>
				</tr>
				<tr>
					<td style="width: 20%;"><label for="codigo">
						Valor:</label></td>
						<td colspan="2" style="width: 60%;"><input
						class="input_data_id" type="number" min="0" step="1"
						id="valor" name="valor" placeholder="Valor" required
						style="display: inline-block; width: 30%; text-align: left;">
					</td>
				</tr>


			</table>

			<table>
				<tr>
					<td><input type="submit" id="botao" name="botao" value="Sacar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Depositar"></td>

				</tr>
			</table>
		</form>
	</div>
	<br />
	<div align="center">
		<c:if test="${not empty erro}">
			<H2>
				<b><c:out value="${erro}"></c:out></b>
			</H2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida}">
			<H3>
				<b><c:out value="${saida}"></c:out></b>
			</H3>
		</c:if>
	</div>


	<br />
	<br />
	<div align="center">
		<c:if test="${not empty contas}">
			<table class="table_round">
				<thead>
					<tr>
						<th>Numero da Conta</th>
						<th>Nome do Cliente</th>
						<th>Saldo</th>
						<th>Dia de Rendimento</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${contas}">
						<tr>
							<td><c:out value="${c.numConta}" /></td>
							<td><c:out value="${c.nomeCliente}" /></td>
							<td><c:out value="${c.saldo}" /></td>
							<td><c:out value="${c.diaDeRendimento}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>