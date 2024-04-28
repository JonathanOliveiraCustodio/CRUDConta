package br.edu.fateczl.CRUDConta.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.CRUDConta.model.ContaPoupanca;

@Repository
public class ContaPoupancaDao implements ICrud<ContaPoupanca>, IContaPoupancaDao {

	private GenericDao gDao;

	public ContaPoupancaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public ContaPoupanca consultar(ContaPoupanca c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "SELECT * FROM fn_consultar_conta_Poupanca(?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, c.getNumConta());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			c.setNumConta(rs.getInt("num_conta"));
			c.setNomeCliente(rs.getString("nome_cliente"));
			c.setSaldo(rs.getFloat("saldo"));
			c.setDiaRendimento(rs.getInt("dia_rendimento"));
		}
		rs.close();
		ps.close();
		con.close();
		return c;
	}

	@Override
	public List<ContaPoupanca> listar() throws SQLException, ClassNotFoundException {
		List<ContaPoupanca> dependentes = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM listarContasPoupanca() ");
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			ContaPoupanca c = new ContaPoupanca();

			c.setNumConta(rs.getInt("num_conta"));
			c.setNomeCliente(rs.getString("nome_cliente"));
			c.setSaldo(rs.getFloat("saldo"));
			c.setDiaRendimento(rs.getInt("dia_rendimento"));

			dependentes.add(c);
		}
		rs.close();
		ps.close();
		con.close();
		return dependentes;
	}

	@Override
	public String iudContaPoupanca(String acao, ContaPoupanca c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "{CALL sp_iud_contaPoupanca (?,?,?,?,?,?)}";
		CallableStatement cs = con.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, c.getNumConta());
		cs.setString(3, c.getNomeCliente());
		cs.setFloat(4, c.getSaldo());
		cs.setInt(5, c.getDiaRendimento());
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(6);
		cs.close();
		con.close();

		return saida;
	}

	@Override
	public List<ContaPoupanca> listarDependente(int codigoFuncionario) throws SQLException, ClassNotFoundException {

		return null;
	}

	public Float calcularSomaSalario(int codigo) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "{ ? = call fn_soma_salario(?) }";
		CallableStatement cs = con.prepareCall(sql);
		cs.registerOutParameter(1, Types.DECIMAL);
		cs.setInt(2, codigo);
		cs.execute();
		Float totalSalario = cs.getFloat(1);
		cs.close();
		con.close();
		return totalSalario;
	}

	@Override
	public void calcularNovoSaldo(ContaPoupanca contaPoupanca, float taxaRendimento)
			throws SQLException, ClassNotFoundException {

	}

	@Override
	public String sacar(ContaPoupanca t, int numConta, float valor)
			throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "{CALL sp_sacar (?,?,?)}";
		CallableStatement cs = con.prepareCall(sql);
		cs.setInt(1, numConta);
		cs.setFloat(2, valor);
		cs.registerOutParameter(3, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(3);
		cs.close();
		con.close();

		return saida;
	}

	@Override
	public String depositar(ContaPoupanca t, int numConta, float valor)
			throws SQLException, ClassNotFoundException {
		return null;

	}

}