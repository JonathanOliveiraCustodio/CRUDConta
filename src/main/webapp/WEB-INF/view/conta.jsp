<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Conta </title>
<script >


</script>
</head>
<div>
	<jsp:include page="menu.jsp" />
</div>
<br />
<div align="center" class="container">
	<form action="conta" method="post">
		<p class="title">
			<b>Conta</b>
		</p>

		<table>
			<tr>
				<td style="width: 20%;"><label for="codigo">Numero Conta:</label></td>
				<td colspan="2" style="width: 60%;"><input
					class="input_data_id" type="number" min="0" step="1" id="codigo"
					name="codigo" placeholder="Código"
					value='<c:out value="${conta.codigo}"></c:out>'></td>
				<td style="width: 20%;"><input type="submit" id="botao"
					name="botao" value="Buscar"></td>
			</tr>

			<tr>
				<td><label for="nome">Nome Cliente:</label></td>
				<td colspan="4"><input class="input_data" type="text" id="nome"
					name="nome" placeholder="Nome do Dependente"
					value='<c:out value="${conta.nome}"></c:out>'></td>
			</tr>

			<tr>
				<td><label for="salario">Saldo:</label></td>
				<td colspan="4"><input class="input_data" type="number"
					id="salario" name="saldo" placeholder="Saldo"
					value='<c:out value="${conta.saldo}"></c:out>'></td>
			</tr>
			
					
		</table>

		<table>
			<tr>
				<td><input type="submit" id="botao" name="botao"
					value="Cadastrar"></td>
				<td><input type="submit" id="botao" name="botao"
					value="Alterar"></td>
				<td><input type="submit" id="botao" name="botao"
					value="Excluir"></td>
				<td><input type="submit" id="botao" name="botao" value="Listar">
				<td><input type="submit" id="botao" name="botao" value="Limpar">
				</td>

			</tr>
		</table>
	</form>
</div>
<br />
<div align="center">
	<c:if test="${not empty erro }">
		<h2>
			<b><c:out value="${erro }" /></b>
		</h2>
	</c:if>
</div>

<br />
<div align="center">
	<c:if test="${not empty saida }">
		<h3>
			<b><c:out value="${saida }" /></b>
		</h3>
	</c:if>
</div>

<br />
<div align="center">
	<c:if test="${not empty contas }">
		<table class="table_round">
			<thead>
				<tr>
					<th>Selecionar</th>
					<th>Código</th>
					<th>Nome Dependente</th>
					<th>Salário</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${contas }">
					<tr>
						<td><input type="radio" name="opcao" value="${c.codigo}"
							onclick="editarDependente(this.value)"
							${d.codigo eq codigoEdicao ? 'checked' : ''} /></td>
						<td><c:out value="${c.codigo }" /></td>
						<td><c:out value="${c.nome }" /></td>
						<td><c:out value="${c.salario }" /></td>
						<td><button onclick="excluirDependente('${c.codigo}')">EXCLUIR</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>

</body>
</html>
