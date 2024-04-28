<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Conta Poupança</title>
<script>
	function consultarContaPoupanca(numConta) {
		window.location.href = 'consulta?numConta=' + numConta;
	}
</script>

<script>
	function editarContaPoupanca(numConta) {
		window.location.href = 'contaPoupanca?cmd=alterar&numConta=' + numConta;
	}

	function excluirContaPoupanca(numConta) {
		if (confirm("Tem certeza que deseja excluir esta Conta Poupança?")) {
			window.location.href = 'contaPoupanca?cmd=excluir&numConta='
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
	<form action="contaPoupanca" method="post">
		<p class="title">
			<b>Conta Poupança</b>
		</p>

		<table>
			<tr>
				<td style="width: 20%;"><label for="codigo">Numero
						Conta:</label></td>
				<td colspan="2" style="width: 60%;"><input
					class="input_data_id" type="number" min="0" step="1" id="numConta"
					name="numConta" placeholder="Número Conta"
					value='<c:out value="${contaPoupanca.numConta}"></c:out>'></td>
				<td style="width: 20%;"><input type="submit" id="botao"
					name="botao" value="Buscar"></td>
			</tr>

			<tr>
				<td><label for="nome">Nome Cliente:</label></td>
				<td colspan="4"><input class="input_data" type="text"
					id="nomeCliente" name="nomeCliente" placeholder="Nome do Cliente"
					value='<c:out value="${contaPoupanca.nomeCliente}"></c:out>'></td>
			</tr>

			<tr>
				<td><label for="salario">Saldo:</label></td>
				<td colspan="4"><input class="input_data" type="number"
					id="saldo" name="saldo" placeholder="Saldo"
					value='<c:out value="${contaPoupanca.saldo}"></c:out>'></td>
			</tr>

			<tr>
				<td><label for="salario">Dia Rendimento:</label></td>
				<td colspan="4"><input class="input_data" type="number"
					id="diaRendimento" name="diaRendimento"
					placeholder="Dia Rendimento"
					value='<c:out value="${contaPoupanca.diaRendimento}"></c:out>'
					step="1"></td>
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
	<c:if test="${not empty contasPoupanca }">
		<table class="table_round">
			<thead>
				<tr>
					<th class="titulo-tabela" colspan="6"
						style="text-align: center; font-size: 23px;">Lista de Contas
						Poupança</th>
				</tr>
				<tr>
					<th>Selecionar</th>
					<th>Número Conta</th>
					<th>Nome Cliente</th>
					<th>Saldo</th>
					<th>Dia Rendimento</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${contasPoupanca}">
					<tr>
						<td><input type="radio" name="opcao" value="${c.numConta}"
							onclick="editarContaPoupanca(this.value)"
							${c.numConta eq codigoEdicao ? 'checked' : ''} /></td>
						<td><c:out value="${c.numConta}" /></td>
						<td><c:out value="${c.nomeCliente}" /></td>
						<td><c:out value="${c.saldo}" /></td>
						<td><c:out value="${c.diaRendimento}" /></td>

						<td style="text-align: center;">
							<button class="btn-excluir"
								onclick="excluirContaPoupanca('${e.numConta}')">Excluir</button>
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>

</body>
</html>
