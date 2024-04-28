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
import br.edu.fateczl.CRUDConta.model.ContaEspecial;
import br.edu.fateczl.CRUDConta.persistence.ContaEspecialDao;
import br.edu.fateczl.CRUDConta.persistence.GenericDao;

@Controller
public class ContaEspecialController {
	
	@Autowired
	GenericDao gDao;
	
	@Autowired
	ContaEspecialDao cDao;
	


	@RequestMapping(name = "contaEspecial", value = "/contaEspecial", method = RequestMethod.GET)
	public ModelAndView contaEspecialGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("cmd");
		String numConta = allRequestParam.get("numConta");

		if (cmd != null) {
			ContaEspecial c = new ContaEspecial();
			c.setNumConta(Integer.parseInt(numConta));

			String saida = "";
			String erro = "";
			List<ContaEspecial> contasEspeciais = new ArrayList<>();


			try {
				if (cmd.contains("alterar")) {
					c = buscarContaEspecial(c);
				} else if (cmd.contains("excluir")) {
					c = buscarContaEspecial(c);
					saida = excluirContaEspecial(c);
					c = null;
				}

				contasEspeciais = listarContasEspeciais();
		

			} catch (SQLException | ClassNotFoundException error) {
				erro = error.getMessage();
			} finally {

				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("contaEspecial", c);
				model.addAttribute("contasEspeciais", contasEspeciais);

			}
		}

		return new ModelAndView("contaEspecial");
	}


	@RequestMapping(name = "contaEspecial", value = "/contaEspecial", method = RequestMethod.POST)
	public ModelAndView contaPoupancaPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String cmd = allRequestParam.get("botao");
		String numConta = allRequestParam.get("numConta");
		String nomeCliente = allRequestParam.get("nomeCliente");
		String saldo = allRequestParam.get("saldo");
		String limite = allRequestParam.get("limite");

		String saida = "";
		String erro = "";
		ContaEspecial c = new ContaEspecial();

		List<ContaEspecial> contasEspeciais = new ArrayList<>();
	

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
					c.setLimite(Float.parseFloat(limite));
		
				}
				if (cmd.contains("Cadastrar")) {
					saida = cadastrarContaEspecial(c);
					c = null;
				}
				if (cmd.contains("Alterar")) {
					saida = alterarContaEspecial(c);
					c = null;
				}
				if (cmd.contains("Excluir")) {
					c = buscarContaEspecial(c);
					saida = excluirContaEspecial(c);
					c = null;
				}
				if (cmd.contains("Buscar")) {
					c = buscarContaEspecial(c);
				}
				if (cmd.contains("Listar")) {
					contasEspeciais = listarContasEspeciais();
				}
			
			} catch (SQLException | ClassNotFoundException error) {
				erro = error.getMessage();

			} finally {

				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("contaEspecial", c);
				model.addAttribute("contasEspeciais", contasEspeciais);
	
			}
		
		return new ModelAndView("contaEspecial");
	}

	private String cadastrarContaEspecial(ContaEspecial c) throws SQLException, ClassNotFoundException {
		String saida = cDao.iudContaEspecial("I", c);
		return saida;
	}

	private String alterarContaEspecial(ContaEspecial c) throws SQLException, ClassNotFoundException {
		String saida = cDao.iudContaEspecial("U", c);
		return saida;

	}

	private String excluirContaEspecial(ContaEspecial c) throws SQLException, ClassNotFoundException {
		String saida = cDao.iudContaEspecial("D", c);
		return saida;

	}

	private ContaEspecial buscarContaEspecial(ContaEspecial c) throws SQLException, ClassNotFoundException {
		c = cDao.consultar(c);
		return c;

	}

	private List<ContaEspecial> listarContasEspeciais() throws SQLException, ClassNotFoundException {
		List<ContaEspecial> contasEspeciais = cDao.listar();
		return contasEspeciais;
	}

	
}
