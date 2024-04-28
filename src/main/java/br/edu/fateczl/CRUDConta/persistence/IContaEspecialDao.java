package br.edu.fateczl.CRUDConta.persistence;

import java.sql.SQLException;


import br.edu.fateczl.CRUDConta.model.ContaEspecial;



public interface IContaEspecialDao {
	
	public String iudContaEspecial(String acao, ContaEspecial d) throws SQLException, ClassNotFoundException;

}
