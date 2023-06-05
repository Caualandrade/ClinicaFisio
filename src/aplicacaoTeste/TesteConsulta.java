package aplicacaoTeste;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import dao.ConsultaDao;
import dao.FisioterapeutaDao;
import dao.PacienteDao;
import dao.UnidadeDao;
import entidades.Consulta;
import entidades.Fisioterapeuta;
import entidades.Paciente;
import entidades.Unidade;
import implementacaoDao.DaoFactory;

public class TesteConsulta {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		Consulta consulta = new Consulta();
		ConsultaDao consultaDao = DaoFactory.createConsultaDao();
		
		consulta = consultaDao.findById(1);
		System.out.println(consulta);
		
		Fisioterapeuta fisio = new Fisioterapeuta();
		FisioterapeutaDao fisioDao = DaoFactory.createFisioterapeutaDao();
		
		fisio = fisioDao.findById(2);
		
		Paciente pac = new Paciente();
		PacienteDao pacDao = DaoFactory.createPacienteDao();
		
		pac = pacDao.findById(2);
		
		Unidade unidade = new Unidade();
		UnidadeDao uniDao = DaoFactory.createUnidadeDao();
		
		unidade = uniDao.findById(1);
		
		SimpleDateFormat sdfBrasil = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		consulta.setData_Hora(sdf.parse("2023-06-20 15:30:00"));
		consulta.setFisioterapeuta(fisio);
		consulta.setPaciente(pac);
		consulta.setUnidade(unidade);
		consultaDao.insert(consulta);
		*/
		
	}

}
