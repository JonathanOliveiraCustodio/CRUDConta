<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Conta Especial</title>
<script >
function consultarContaEspecial(numConta) {
	window.location.href = 'consulta?numConta=' + numConta;
}
</script>

<script>
function editarContaEspecial(numConta) {
	window.location.href = 'contaEspecial?cmd=alterar&numConta=' + numConta;
}

function excluirContaEspecial(numConta) {
	if (confirm("Tem certeza que deseja excluir esta Conta Especial?")) {
		window.location.href = 'contaEspecial?cmd=excluir&numConta='
				+ numConta;
	}
}

</script>
</head>
<div>
	<jsp:include page="menu.jsp" />
</div>
<br />
<div align="center" class="container">
	<form action="contaEspecial" method="post">
		<p class="title">
			<b>Conta Especial</b>
		</p>

		<table>
			<tr>
				<td style="width: 20%;"><label for="codigo">Numero
						Conta:</label></td>
				<td colspan="2" style="width: 60%;"><input
					class="input_data_id" type="number" min="0" step="1" id="numConta"
					name="numConta" placeholder="Número Conta"
					value='<c:out value="${contaEspecial.numConta}"></c:out>'></td>
				<td style="width: 20%;"><input type="submit" id="botao"
					name="botao" value="Buscar"></td>
			</tr>

			<tr>
				<td><label for="nome">Nome Cliente:</label></td>
				<td colspan="4"><input class="input_data" type="text"
					id="nomeCliente" name="nomeCliente" placeholder="Nome do Cliente"
					value='<c:out value="${contaEspecial.nomeCliente}"></c:out>'></td>
			</tr>

			<tr>
				<td><label for="saldo">Saldo:</label></td>
				<td colspan="4"><input class="input_data" type="number"
					id="saldo" name="saldo" placeholder="Saldo"
					value='<c:out value="${contaEspecial.saldo}"></c:out>'></td>
			</tr>

			<tr>
				<td><label for="limite">Limite:</label></td>
				<td colspan="4"><input class="input_data" type="number"
					id="limite" name="limite" placeholder="limite"
					value='<c:out value="${contaEspecial.limite}"></c:out>'></td>
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
	<c:if test="${not empty contasEspeciais }">
		<table class="table_round">
			<thead>
				<tr>
					<th>Selecionar</th>
					<th>Número Conta</th>
					<th>Nome Cliente</th>
					<th>Saldo</th>
					<th>Limite</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${contasEspeciais}">
					<tr>
						<td><input type="radio" name="opcao" value="${c.numConta}"
							onclick="editarContaEspecial(this.value)"
							${c.numConta eq codigoEdicao ? 'checked' : ''} /></td>
						<td><c:out value="${c.numConta}" /></td>
						<td><c:out value="${c.nomeCliente}" /></td>
						<td><c:out value="${c.saldo}" /></td>
						<td><c:out value="${c.limite}" /></td>

						<td style="text-align: center;">
							<button class="btn-excluir"
								onclick="excluirContaEspecial('${e.numConta}')">Excluir</button>
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>

</body>
</html>
