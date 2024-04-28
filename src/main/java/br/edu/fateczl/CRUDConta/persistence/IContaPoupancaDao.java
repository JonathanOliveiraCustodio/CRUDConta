package br.edu.fateczl.CRUDConta.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.CRUDConta.model.ContaPoupanca;


public interface IContaPoupancaDao {
	
	public String iudContaPoupanca (String acao, ContaPoupanca d) throws SQLException, ClassNotFoundException;
	public List<ContaPoupanca> listarDependente(int codigo) throws SQLException, ClassNotFoundException;
	public void calcularNovoSaldo(ContaPoupanca contaPoupanca, float taxaRendimento) throws SQLException, ClassNotFoundException;

}
