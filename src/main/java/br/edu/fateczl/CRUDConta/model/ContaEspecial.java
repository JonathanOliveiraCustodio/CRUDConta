package br.edu.fateczl.CRUDConta.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaEspecial extends ContaBancaria{
	
	Float limite;

	@Override
	public String toString() {
		return "ContaEspecial [limite=" + limite + "]";
	}	

	
	


}