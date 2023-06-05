package implementacaoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.UnidadeDao;
import db.DB;
import db.DbException;
import entidades.Endereco;
import entidades.Unidade;

public class UnidadeDaoJDBC implements UnidadeDao {

	private Connection conn;

	public UnidadeDaoJDBC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public void insert(Unidade obj) {

		PreparedStatement st = null;
		String sql = "INSERT INTO tb_unidade(NOME, ID_END) VALUES (?,?)";
		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getEndereco().getId());
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
		}finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Unidade obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		String sql = "DELETE FROM TB_UNIDADE WHERE ID = ?";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas == 0) {
				throw new DbException("Nenhuma exclusão ocorreu! Id inexistente");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Unidade findById(Integer id) {
		
		PreparedStatement st =  null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM TB_UNIDADE JOIN"
				+ " TB_ENDERECO ON TB_UNIDADE.ID_END = TB_ENDERECO.ID WHERE TB_UNIDADE.ID = ?";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			while(rs.next()) {
				Endereco endereco = instanciaEndereco(rs);
				Unidade unidade = instanciaUnidade(rs, endereco);
				return unidade;
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
	public List<Unidade> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM TB_UNIDADE JOIN TB_ENDERECO ON TB_UNIDADE.ID_END = TB_ENDERECO.ID";
		
		try {
			Endereco endereco = new Endereco();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			List<Unidade> lista = new ArrayList<>();
			
			while(rs.next()) {
				endereco = instanciaEndereco(rs);
				Unidade unidade = instanciaUnidade(rs, endereco);
				lista.add(unidade);
			}
			return lista;
		} catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Unidade> findByEndereco(Endereco endereco) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Unidade instanciaUnidade(ResultSet rs, Endereco endereco) throws SQLException {
		Unidade unidade = new Unidade();
		unidade.setId(rs.getInt("ID"));
		unidade.setNome(rs.getString("Nome"));
		unidade.setEndereco(endereco);
		return unidade;
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
