package br.edu.fateczl.CRUDConta.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import br.edu.fateczl.CRUDConta.model.ContaPoupanca;
import br.edu.fateczl.CRUDConta.persistence.ContaPoupancaDao;
import br.edu.fateczl.CRUDConta.persistence.GenericDao;

@Controller
public class ContaPoupancaController {
	
	@Autowired
	GenericDao gDao;
	
	@Autowired
	ContaPoupancaDao cDao;
	


	@RequestMapping(name = "contaPoupanca", value = "/contaPoupanca", method = RequestMethod.GET)
	public ModelAndView contaPoupancaGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("cmd");
		String numConta = allRequestParam.get("numConta");

		if (cmd != null) {
			ContaPoupanca c = new ContaPoupanca();
			c.setNumConta(Integer.parseInt(numConta));

			String saida = "";
			String erro = "";
			List<ContaPoupanca> contasPoupanca = new ArrayList<>();


			try {
				if (cmd.contains("alterar")) {
					c = buscarContaPoupanca(c);
				} else if (cmd.contains("excluir")) {
					c = buscarContaPoupanca(c);
					saida = excluirContaPoupanca(c);
					c = null;
				}

				contasPoupanca = listarContasPoupanca();
		

			} catch (SQLException | ClassNotFoundException error) {
				erro = error.getMessage();
			} finally {

				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("contaPoupanca", c);
				model.addAttribute("contasPoupanca", contasPoupanca);
			}
		}
		return new ModelAndView("contaPoupanca");
	}

	@RequestMapping(name = "contaPoupanca", value = "/contaPoupanca", method = RequestMethod.POST)
	public ModelAndView contaPoupancaPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("botao");
		String numConta = allRequestParam.get("numConta");
		String nomeCliente = allRequestParam.get("nomeCliente");
		String saldo = allRequestParam.get("saldo");
		String diaRendimento = allRequestParam.get("diaRendimento");

		String saida = "";
		String erro = "";
		ContaPoupanca c = new ContaPoupanca();

		List<ContaPoupanca> contasPoupanca = new ArrayList<>();
	

		if (cmd != null && !cmd.isEmpty() && cmd.contains("Limpar")) {
			c = null;

		} else 

			if (!cmd.contains("Listar")) {
				c.setNumConta(Integer.parseInt(numConta));
			}

			try {

			

				if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
					c.setNomeCliente(nomeCliente);
					c.setSaldo(Float.parseFloat(saldo));
					c.setDiaRendimento(Integer.parseInt(diaRendimento));
		

				}
				if (cmd.contains("Cadastrar")) {
					saida = cadastrarContaPoupanca(c);
					c = null;
				}
				if (cmd.contains("Alterar")) {
					saida = alterarContaPoupanca(c);
					c = null;
				}
				if (cmd.contains("Excluir")) {
					c = buscarContaPoupanca(c);
					saida = excluirContaPoupanca(c);
					c = null;
				}
				if (cmd.contains("Buscar")) {
					c = buscarContaPoupanca(c);
				}
				if (cmd.contains("Listar")) {
					contasPoupanca = listarContasPoupanca();
				}
			
			} catch (SQLException | ClassNotFoundException error) {
				erro = error.getMessage();

			} finally {

				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("contaPoupanca", c);
				model.addAttribute("contasPoupanca", contasPoupanca);
	
			}
		
		return new ModelAndView("contaPoupanca");
	}

	private String cadastrarContaPoupanca(ContaPoupanca c) throws SQLException, ClassNotFoundException {
		String saida = cDao.iudContaPoupanca("I", c);
		return saida;
	}

	private String alterarContaPoupanca(ContaPoupanca c) throws SQLException, ClassNotFoundException {
		String saida = cDao.iudContaPoupanca("U", c);
		return saida;

	}

	private String excluirContaPoupanca(ContaPoupanca c) throws SQLException, ClassNotFoundException {
		String saida = cDao.iudContaPoupanca("D", c);
		return saida;

	}

	private ContaPoupanca buscarContaPoupanca(ContaPoupanca c) throws SQLException, ClassNotFoundException {
		c = cDao.consultar(c);
		return c;

	}

	private List<ContaPoupanca> listarContasPoupanca() throws SQLException, ClassNotFoundException {
		List<ContaPoupanca> contasPoupanca = cDao.listar();
		return contasPoupanca;
	}

	
}
