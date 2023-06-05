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

import dao.FisioterapeutaDao;
import db.DB;
import db.DbException;
import entidades.Endereco;
import entidades.Especialidade;
import entidades.Fisioterapeuta;

public class FisioterapeutaDaoJDBC implements FisioterapeutaDao {

	private Connection conn;

	public FisioterapeutaDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Fisioterapeuta obj) {

		PreparedStatement st = null;
		String sql = "INSERT INTO tb_fisio (Nome, NUMEROREGISTRO, DataDeNascimento, SEXO, TELEFONE, ID_ESP, ID_END) "
				+ "VALUES (?,?,?,?,?,?,?)";

		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getNumeroRegistro());
			st.setDate(3, new java.sql.Date(obj.getDataDeNascimento().getTime()));
			st.setString(4, obj.getSexo());
			st.setString(5, obj.getTelefone());
			st.setInt(6, obj.getEspecialidade().getId());
			st.setInt(7, obj.getEndereco().getId());
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
	public void update(Fisioterapeuta obj) {

		PreparedStatement st = null;
		String sql = "UPDATE tb_fisio SET Nome = ?, NumeroRegistro = ?, DataDeNascimento = ?,"
				+ " Sexo = ?, Tefelone = ?, Id_esp = ?, Id_end = ? WHERE ID = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getNumeroRegistro());
			st.setDate(3, new java.sql.Date(obj.getDataDeNascimento().getTime()));
			st.setString(4, obj.getSexo());
			st.setString(5, obj.getTelefone());
			st.setInt(6, obj.getEspecialidade().getId());
			st.setInt(7, obj.getEndereco().getId());
			st.setInt(8, obj.getId());
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
		try {
			st = conn.prepareStatement("DELETE FROM tb_fisio " + "WHERE id = ?");
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
	public Fisioterapeuta findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql3 = "SELECT TB_FISIO.*, TB_ESPECIALIDADE.NOME AS NOME_ESPECIALIDADE, TB_ENDERECO.*\r\n"
				+ "FROM TB_FISIO\r\n" + "JOIN TB_ESPECIALIDADE ON TB_FISIO.ID_ESP = TB_ESPECIALIDADE.ID\r\n"
				+ "JOIN TB_ENDERECO ON TB_FISIO.ID_END = TB_ENDERECO.ID WHERE TB_FISIO.ID = ?";
		try {
			st = conn.prepareStatement(sql3);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Endereco endereco = instanciaEndereco(rs);
				Especialidade especialidade = instanciaEspecialidade(rs);
				Fisioterapeuta fisioterapeuta = instaciaFisio(rs, endereco, especialidade);
				return fisioterapeuta;
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
	public List<Fisioterapeuta> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Fisioterapeuta> lista = new ArrayList<>();
		Map<Integer, Especialidade> mapEsp = new HashMap<>();
		Map<Integer, Endereco> mapEnd = new HashMap<>();

		String sql3 = "SELECT TB_FISIO.*, TB_ESPECIALIDADE.NOME AS NOME_ESPECIALIDADE, TB_ENDERECO.*\r\n"
				+ "FROM TB_FISIO\r\n" + "JOIN TB_ESPECIALIDADE ON TB_FISIO.ID_ESP = TB_ESPECIALIDADE.ID\r\n"
				+ "JOIN TB_ENDERECO ON TB_FISIO.ID_END = TB_ENDERECO.ID ORDER BY TB_FISIO.NOME";
		try {
			st = conn.prepareStatement(sql3);
			rs = st.executeQuery();

			while (rs.next()) {
				Especialidade esp = mapEsp.get(rs.getInt("ID_ESP"));
				Endereco end = mapEnd.get(rs.getInt("ID_END"));

				if (esp == null && end == null) {
					esp = instanciaEspecialidade(rs);
					end = instanciaEndereco(rs);

					mapEsp.put(rs.getInt("ID_ESP"), esp);
					mapEnd.put(rs.getInt("ID_END"), end);

				}
				Fisioterapeuta fisio = instaciaFisio(rs, end, esp);
				lista.add(fisio);
			}

			return lista;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public Fisioterapeuta instaciaFisio(ResultSet rs, Endereco endereco, Especialidade especialidade)
			throws SQLException {
		Fisioterapeuta fisio = new Fisioterapeuta();
		fisio.setId(rs.getInt("ID"));
		fisio.setNome(rs.getString("Nome"));
		fisio.setNumeroRegistro(rs.getString("NUMEROREGISTRO"));
		fisio.setSexo(rs.getString("SEXO"));
		fisio.setTelefone(rs.getString("TELEFONE"));
		fisio.setDataDeNascimento(rs.getDate("DataDeNascimento"));
		fisio.setEndereco(endereco);
		fisio.setEspecialidade(especialidade);
		return fisio;
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

	public Especialidade instanciaEspecialidade(ResultSet rs) throws SQLException {
		Especialidade especialidade = new Especialidade();
		especialidade.setId(rs.getInt("ID"));
		especialidade.setNome(rs.getString("NOME_ESPECIALIDADE"));
		return especialidade;
	}

}
