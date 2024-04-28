package br.edu.fateczl.CRUDConta.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import br.edu.fateczl.CRUDConta.model.ContaPoupanca;
import br.edu.fateczl.CRUDConta.persistence.ContaEspecialDao;
import br.edu.fateczl.CRUDConta.persistence.ContaPoupancaDao;
import br.edu.fateczl.CRUDConta.persistence.GenericDao;

@Controller
public class OperacoesController {

	@Autowired
	GenericDao gDao;

	@Autowired
	ContaPoupancaDao cpDao;

	@Autowired
	ContaEspecialDao ceDao;

	@RequestMapping(name = "operacoes", value = "/operacoes", method = RequestMethod.GET)
	public ModelAndView operacoesGet(ModelMap model) {

		return new ModelAndView("operacoes");
	}

	@RequestMapping(name = "operacoes", value = "/operacoes", method = RequestMethod.POST)
	public ModelAndView operacoesPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("botao");
		String numConta = allRequestParam.get("numConta");
		String valor = allRequestParam.get("valor");

		String saida = "";
		String erro = "";

		ContaPoupanca c = new ContaPoupanca();

		try {
			if (cmd.contains("Sacar")) {

				c.setNumConta(Integer.parseInt(numConta));
				c = buscarContaPoupanca(c);
				float valorSaque = Float.parseFloat(valor);
				saida = sacarContaPoupanca(c, valorSaque);
				c = null;
			}
			if (cmd.contains("Depositar")) {
				c.setNumConta(Integer.parseInt(numConta));
				c = buscarContaPoupanca(c);
				float valorDeposito = Float.parseFloat(valor);
				saida = sacarContaPoupanca(c, valorDeposito);
				c = null;
			}
		} catch (SQLException | ClassNotFoundException error) {
			erro = error.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("contaBancaria", c);
		}

		return new ModelAndView("operacoes");
	}

	private String sacarContaPoupanca(ContaPoupanca c, float valor) throws SQLException, ClassNotFoundException {
		float novoSaldo = c.getSaldo() - valor;
		c.setSaldo(novoSaldo);
		cpDao.iudContaPoupanca("U", c);

		String saldoString = "Saldo Atual após Saque R$" + c.getSaldo();

		// String saida = (Float.parseFloat(c.getSaldo()));
		return saldoString;
	}

	
	
	private ContaPoupanca buscarContaPoupanca(ContaPoupanca c) throws SQLException, ClassNotFoundException {
		c = cpDao.consultar(c);
		return c;

	}
}