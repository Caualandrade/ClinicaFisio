package implementacaoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.EnderecoDao;
import db.DB;
import db.DbException;
import entidades.Endereco;

public class EnderecoDaoJDBC implements EnderecoDao {

	private Connection conn;

	public EnderecoDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Endereco obj) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO " + "tb_endereco (LOGRADOURO, NUMEROENDERECO, BAIRRO, CIDADE, CEP)"
					+ "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getLogradouro());
			st.setInt(2, obj.getNumEndereco());
			st.setString(3, obj.getBairro());
			st.setString(4, obj.getCidade());
			st.setString(5, obj.getCep());

			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma inserção ocorreu");
			}
		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Endereco obj) {

		PreparedStatement st = null;
		String sql = "UPDATE tb_endereco SET Logradouro = ?, NumeroEndereco = ?, Bairro = ?,"
				+ " Cidade = ?, Cep = ? WHERE id = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, obj.getLogradouro());
			st.setInt(2, obj.getNumEndereco());
			st.setString(3, obj.getBairro());
			st.setString(4, obj.getCidade());
			st.setString(5, obj.getCep());
			st.setInt(6, obj.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		String sql = "DELETE FROM tb_endereco where id = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas == 0) {
				throw new DbException("Nenhuma exclusão ocorreu! Id inexistente");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Endereco findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM tb_endereco WHERE id = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Endereco endereco = instanciaEnderco(rs);
				return endereco;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

		return null;
	}

	@Override
	public List<Endereco> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM tb_endereco ORDER BY logradouro";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			List<Endereco> lista = new ArrayList<>();

			while (rs.next()) {
				Endereco endereco = instanciaEnderco(rs);
				lista.add(endereco);
			}
			return lista;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	public Endereco instanciaEnderco(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs.getInt("ID"));
		endereco.setLogradouro(rs.getString("LOGRADOURO"));
		endereco.setBairro(rs.getString("BAIRRO"));
		endereco.setCep(rs.getString("CEP"));
		endereco.setCidade(rs.getString("CIDADE"));
		endereco.setNumEndereco(rs.getInt("NUMEROENDERECO"));
		return endereco;
	}

}
