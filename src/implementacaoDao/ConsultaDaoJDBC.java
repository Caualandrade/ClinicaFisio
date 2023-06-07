package implementacaoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.ConsultaDao;
import db.DB;
import db.DbException;
import entidades.Consulta;
import entidades.Fisioterapeuta;
import entidades.Paciente;
import entidades.Unidade;

public class ConsultaDaoJDBC implements ConsultaDao {

	private Connection conn;

	public ConsultaDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Consulta obj) {

		PreparedStatement st = null;
		String sql = "INSERT INTO tb_consulta (DATA_HORA, ID_FISIO, ID_PAC, ID_UNI)" + " VALUES (?,?,?,?)";
		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			Date dataHora = obj.getData_Hora();
			Timestamp timestamp = new Timestamp(dataHora.getTime());
			st.setTimestamp(1, timestamp);
			st.setInt(2, obj.getFisioterapeuta().getId());
			st.setInt(3, obj.getPaciente().getId());
			st.setInt(4, obj.getUnidade().getId());
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
	public void update(Consulta obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;

		String sql = "DELETE FROM TB_CONSULTA WHERE tb_consulta.id = ?";
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
	public Consulta findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		String sql = "SELECT tb_consulta.ID, tb_consulta.DATA_HORA as Consulta_Data_Hora, tb_paciente.Nome as Nome_Paciente,\r\n"
				+ "				tb_fisio.nome as Nome_Fisio, tb_unidade.NOME as Nome_Unidade\r\n"
				+ "				from tb_consulta join tb_paciente on tb_consulta.ID_PAC = tb_paciente.ID\r\n"
				+ "				join tb_fisio on tb_consulta.ID_FISIO = tb_fisio.Id\r\n"
				+ "				join tb_unidade on tb_consulta.ID_UNI = tb_unidade.ID where tb_consulta.id = ?";

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Paciente paciente = instanciaPaciente(rs);
				Unidade unidade = instanciaUnidade(rs);
				Fisioterapeuta fisio = instaciaFisio(rs);
				Consulta consulta = instanciaConsulta(rs, paciente, unidade, fisio);
				return consulta;
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
	public List<Consulta> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		String sql = "SELECT tb_consulta.ID, tb_consulta.DATA_HORA as Consulta_Data_Hora, tb_paciente.Nome as Nome_Paciente,\r\n"
				+ "				tb_fisio.nome as Nome_Fisio, tb_unidade.NOME as Nome_Unidade\r\n"
				+ "				from tb_consulta join tb_paciente on tb_consulta.ID_PAC = tb_paciente.ID\r\n"
				+ "				join tb_fisio on tb_consulta.ID_FISIO = tb_fisio.Id\r\n"
				+ "				join tb_unidade on tb_consulta.ID_UNI = tb_unidade.ID order by Consulta_Data_Hora";

		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			List<Consulta> lista = new ArrayList();
			while (rs.next()) {
				Paciente paciente = instanciaPaciente(rs);
				Unidade unidade = instanciaUnidade(rs);
				Fisioterapeuta fisio = instaciaFisio(rs);
				Consulta consulta = instanciaConsulta(rs, paciente, unidade, fisio);
				lista.add(consulta);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Consulta> findByDate(String inicio, String fim) {

		PreparedStatement st = null;
		ResultSet rs = null;

		String sql = "SELECT TB_CONSULTA.ID, TB_CONSULTA.DATA_HORA AS CONSULTA_DATA_HORA, tb_paciente.Nome as Nome_Paciente,\r\n"
				+ "				tb_fisio.nome as Nome_Fisio, tb_unidade.NOME as Nome_Unidade\r\n"
				+ "				from tb_consulta join tb_paciente on tb_consulta.ID_PAC = tb_paciente.ID\r\n"
				+ "				join tb_fisio on tb_consulta.ID_FISIO = tb_fisio.Id\r\n"
				+ "				join tb_unidade on tb_consulta.ID_UNI = tb_unidade.ID where tb_consulta.data_hora between ? and ?";

		try {
			st = conn.prepareStatement(sql);
			st.setString(1, inicio);
			st.setString(2, fim);
			rs = st.executeQuery();
			List<Consulta> lista = new ArrayList();
			while (rs.next()) {
				Paciente paciente = instanciaPaciente(rs);
				Unidade unidade = instanciaUnidade(rs);
				Fisioterapeuta fisio = instaciaFisio(rs);
				Consulta consulta = instanciaConsulta(rs, paciente, unidade, fisio);
				lista.add(consulta);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public Consulta instanciaConsulta(ResultSet rs, Paciente pac, Unidade uni, Fisioterapeuta fisio)
			throws SQLException {

		Consulta con = new Consulta();
		con.setId(rs.getInt("ID"));
		con.setData_Hora(rs.getDate("Consulta_Data_Hora"));
		con.setFisioterapeuta(fisio);
		con.setUnidade(uni);
		con.setPaciente(pac);

		return con;
	}

	public Paciente instanciaPaciente(ResultSet rs) throws SQLException {

		Paciente pac = new Paciente();
		pac.setId(rs.getInt("ID"));
		pac.setNome(rs.getString("Nome_Paciente"));
		// pac.setDataNascimento(rs.getDate("DATANASCIMENTO"));
		// pac.setSexo(rs.getString("SEXO"));
		// pac.setTelefone(rs.getString("TELEFONE"));
		// pac.setEndereco(endereco);

		return pac;
	}

	public Unidade instanciaUnidade(ResultSet rs) throws SQLException {
		Unidade unidade = new Unidade();
		unidade.setId(rs.getInt("ID"));
		unidade.setNome(rs.getString("Nome_Unidade"));
		// unidade.setEndereco(endereco);
		return unidade;
	}

	public Fisioterapeuta instaciaFisio(ResultSet rs) throws SQLException {
		Fisioterapeuta fisio = new Fisioterapeuta();
		fisio.setId(rs.getInt("ID"));
		fisio.setNome(rs.getString("Nome_Fisio"));
		// fisio.setNumeroRegistro(rs.getString("NUMEROREGISTRO"));
		// fisio.setSexo(rs.getString("SEXO"));
		// fisio.setTelefone(rs.getString("TELEFONE"));
		// fisio.setDataDeNascimento(rs.getDate("DataDeNascimento"));
		// fisio.setEndereco(endereco);
		// fisio.setEspecialidade(especialidade);
		return fisio;
	}

}
