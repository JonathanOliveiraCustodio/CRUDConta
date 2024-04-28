package br.edu.fateczl.CRUDConta.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaPoupanca extends ContaBancaria {

	int diaRendimento;

	@Override
	public String toString() {
		return "ContaPoupanca [diaRendimento=" + diaRendimento + "]";
	}	

}
