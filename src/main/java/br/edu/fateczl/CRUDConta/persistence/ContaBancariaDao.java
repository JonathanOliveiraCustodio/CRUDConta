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
import br.edu.fateczl.CRUDConta.model.ContaBancaria;


@Repository
public class ContaBancariaDao implements ICrud<ContaBancaria>,IContaBancariaDao {

	private GenericDao gDao;

	public ContaBancariaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public ContaBancaria consultar(ContaBancaria c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "SELECT * FROM fn_consultar_conta_Bancaria(?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, c.getNumConta());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			c.setNumConta(rs.getInt("num_conta"));
			c.setNomeCliente(rs.getString("nome_cliente"));
			c.setSaldo(rs.getFloat("saldo"));
			//c.setDiaRendimento(rs.getInt("dia_rendimento"));
		}
		rs.close();
		ps.close();
		con.close();
		return c;
	}

	@Override
	public List<ContaBancaria> listar() throws SQLException, ClassNotFoundException {
		List<ContaBancaria> dependentes = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM listarContas() ");
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			ContaBancaria c = new ContaBancaria();

			c.setNumConta(rs.getInt("num_conta"));
			c.setNomeCliente(rs.getString("nome_cliente"));
			c.setSaldo(rs.getFloat("saldo"));
		//	c.setDiaRendimento(rs.getInt("dia_rendimento"));

			dependentes.add(c);
		}
		rs.close();
		ps.close();
		con.close();
		return dependentes;
	}

	@Override
	public String sacar(ContaBancaria t, int numConta, float valor) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String depositar(ContaBancaria t, int numConta, float valor) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String iudContaBancaria(String acao, ContaBancaria c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "{CALL sp_iud_contaBancaria (?,?,?,?,?)}";
		CallableStatement cs = con.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, c.getNumConta());
		cs.setString(3, c.getNomeCliente());
		cs.setFloat(4, c.getSaldo());
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(5);
		cs.close();
		con.close();

		return saida;
	}



	
}