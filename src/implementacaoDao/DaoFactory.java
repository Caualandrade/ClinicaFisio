package implementacaoDao;

import dao.ConsultaDao;
import dao.EnderecoDao;
import dao.EspecialidadeDao;
import dao.FisioterapeutaDao;
import dao.PacienteDao;
import dao.UnidadeDao;
import db.DB;

public class DaoFactory {

	public static EspecialidadeDao createEspecialidadeDao() {
		return new EspecialidadeDaoJDBC(DB.getConnection());
	}
	
	public static ConsultaDao createConsultaDao() {
		return new ConsultaDaoJDBC(DB.getConnection());
	}
	
	public static EnderecoDao createEnderecoDao() {
		return new EnderecoDaoJDBC(DB.getConnection());
	}
	
	public static FisioterapeutaDao createFisioterapeutaDao() {
		return new FisioterapeutaDaoJDBC(DB.getConnection());
	}
	
	public static PacienteDao createPacienteDao() {
		return new PacienteDaoJDBC(DB.getConnection());
	}
	
	public static UnidadeDao createUnidadeDao() {
		return new UnidadeDaoJDBC(DB.getConnection());
	}
	
}
