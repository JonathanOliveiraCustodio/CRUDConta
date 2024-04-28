package br.edu.fateczl.CRUDConta.persistence;

import java.sql.SQLException;

import br.edu.fateczl.CRUDConta.model.ContaBancaria;




public interface IContaBancariaDao {
	
	public String iudContaBancaria(String acao, ContaBancaria d) throws SQLException, ClassNotFoundException;

}
