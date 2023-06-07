package implementacaoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.PacienteDao;
import db.DB;
import db.DbException;
import entidades.Endereco;
import entidades.Paciente;

public class PacienteDaoJDBC implements PacienteDao {

	private Connection conn;

	public PacienteDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Paciente obj) {

		PreparedStatement st = null;
		String sql = "INSERT INTO tb_paciente (Nome, DataNascimento, Sexo, Telefone, ID_End)" + "VALUES (?,?,?,?,?)";
		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setDate(2, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(3, obj.getSexo());
			st.setString(4, obj.getTelefone());
			st.setInt(5, obj.getEndereco().getId());
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
	public void update(Paciente obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;

		String sql = "DELETE FROM TB_PACIENTE WHERE TB_PACIENTE.ID = ?";
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
	public Paciente findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "SELECT TB_PACIENTE.*, TB_ENDERECO.* FROM TB_PACIENTE"
				+ " JOIN TB_ENDERECO ON "
				+ "TB_PACIENTE.ID_END = TB_ENDERECO.ID WHERE TB_PACIENTE.ID = ?";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			while(rs.next()) {
				Endereco endereco = instanciaEndereco(rs);
				Paciente paciente = instanciaPaciente(rs, endereco);
				return paciente;
			}
		} catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Paciente> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Paciente> lista = new ArrayList<>();
		Map<Integer, Endereco> mapEnd = new HashMap<>();
		String sql = "SELECT TB_PACIENTE.*, TB_ENDERECO.* FROM TB_PACIENTE"
				+ " JOIN TB_ENDERECO WHERE "
				+ "TB_PACIENTE.ID_END = TB_ENDERECO.ID ORDER BY TB_PACIENTE.NOME";
		
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				Endereco end = mapEnd.get(rs.getInt("ID_END"));
				if(end == null) {
					end = instanciaEndereco(rs);
					mapEnd.put(rs.getInt("ID_END"), end);
				}
				Paciente pac = instanciaPaciente(rs, end);
				lista.add(pac);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public Paciente instanciaPaciente(ResultSet rs, Endereco endereco) throws SQLException {

		Paciente pac = new Paciente();
		pac.setId(rs.getInt("ID"));
		pac.setNome(rs.getString("Nome"));
		pac.setDataNascimento(rs.getDate("DATANASCIMENTO"));
		pac.setSexo(rs.getString("SEXO"));
		pac.setTelefone(rs.getString("TELEFONE"));
		pac.setEndereco(endereco);

		return pac;
	}

	public Endereco instanciaEndereco(ResultSet rs) throws SQLException {
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
