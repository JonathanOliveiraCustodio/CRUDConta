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
import br.edu.fateczl.CRUDConta.model.ContaEspecial;



@Repository
public class ContaEspecialDao implements ICrud<ContaEspecial>, IContaEspecialDao {

	private GenericDao gDao;

	public ContaEspecialDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public ContaEspecial consultar(ContaEspecial c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "SELECT * FROM fn_consultar_conta_Especial(?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, c.getNumConta());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			c.setNumConta(rs.getInt("num_conta"));
			c.setNomeCliente(rs.getString("nome_cliente"));
			c.setSaldo(rs.getFloat("saldo"));
			c.setLimite(rs.getFloat("limite"));
		}
		rs.close();
		ps.close();
		con.close();
		return c;
	}

	@Override
	public List<ContaEspecial> listar() throws SQLException, ClassNotFoundException {
		List<ContaEspecial> dependentes = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM listarContasEspeciais() ");
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			ContaEspecial c = new ContaEspecial();
			c.setNumConta(rs.getInt("num_conta"));
			c.setNomeCliente(rs.getString("nome_cliente"));
			c.setSaldo(rs.getFloat("saldo"));
			c.setLimite(rs.getFloat("limite"));

			dependentes.add(c);
		}
		rs.close();
		ps.close();
		con.close();
		return dependentes;
	}

	@Override
	public String iudContaEspecial(String acao, ContaEspecial c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "{CALL sp_iud_conta_especial (?,?,?,?,?,?)}";
		CallableStatement cs = con.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, c.getNumConta());
		cs.setString(3, c.getNomeCliente());
		cs.setFloat(4, c.getSaldo());
		cs.setFloat(5, c.getLimite());
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(6);
		cs.close();
		con.close();

		return saida;
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
	public String sacar(ContaEspecial t, int numConta, float valor) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String depositar(ContaEspecial t, int numConta, float valor) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}





	
}