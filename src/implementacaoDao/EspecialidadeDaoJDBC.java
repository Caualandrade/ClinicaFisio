package implementacaoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.EspecialidadeDao;
import db.DB;
import db.DbException;
import entidades.Especialidade;

public class EspecialidadeDaoJDBC implements EspecialidadeDao {
	
	private Connection conn;
	
	public EspecialidadeDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Especialidade obj) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO tb_especialidade"
					+ "(NOME)"
					+ "VALUES(?)",Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Erro inesperado! Nenhuma inserção ocorreu");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Especialidade obj) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE tb_especialidade "
					+ "SET NOME = ? WHERE id = ?");
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());
			st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM tb_especialidade "
					+ "WHERE id = ?");
			st.setInt(1, id);
	
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas==0) {
				throw new DbException("Nenhuma exclusão ocorreu! Id inexistente");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public Especialidade findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT tb_especialidade.* FROM tb_especialidade"
					+ " WHERE tb_especialidade.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Especialidade especialidade = instaciaEspecialidade(rs);
				return especialidade;
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
	public List<Especialidade> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_especialidade ORDER BY Nome");
			
			rs = st.executeQuery();
			
			
			List <Especialidade> lista = new ArrayList<>();
			
			while (rs.next()) {
				Especialidade especialidade = instaciaEspecialidade(rs);
				lista.add(especialidade);
			}
			return lista;
		} 
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public Especialidade instaciaEspecialidade(ResultSet rs) throws SQLException {
		Especialidade especialidade = new Especialidade();
		especialidade.setId(rs.getInt("ID"));
		especialidade.setNome(rs.getString("NOME"));
		return especialidade;
	}

}
