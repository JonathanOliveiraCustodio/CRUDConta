package br.edu.fateczl.CRUDConta.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaBancaria {
	
	String nomeCliente;	
	int  numConta;			
	Float saldo;	
	@Override
	
	public String toString() {
		return nomeCliente;
	} 

}