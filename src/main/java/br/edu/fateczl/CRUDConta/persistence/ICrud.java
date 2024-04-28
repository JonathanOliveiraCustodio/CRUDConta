package br.edu.fateczl.CRUDConta.persistence;

import java.sql.SQLException;
import java.util.List;

public interface ICrud<T> {	
	public T consultar(T t) throws SQLException, ClassNotFoundException;
	public List<T> listar() throws SQLException, ClassNotFoundException;
    public String sacar(T t, int numConta, float valor) throws SQLException, ClassNotFoundException;
    public String depositar(T t,int numConta, float valor) throws SQLException, ClassNotFoundException;

}
