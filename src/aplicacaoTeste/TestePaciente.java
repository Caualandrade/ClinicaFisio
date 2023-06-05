package aplicacaoTeste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import dao.EnderecoDao;
import dao.PacienteDao;
import entidades.Endereco;
import entidades.Paciente;
import implementacaoDao.DaoFactory;

public class TestePaciente {

	public static void main(String[] args) throws ParseException {
		// Teste de Paciente
		
		PacienteDao pacienteDao = DaoFactory.createPacienteDao();
		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();

		
		SimpleDateFormat sdfm = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfmBanco = new SimpleDateFormat("yyyy-MM-dd");
		
		/*
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua B");
		endereco.setBairro("Bairro B");
		endereco.setCidade("Salvador");
		endereco.setNumEndereco(222);
		endereco.setCep("4119809");
		enderecoDao.insert(endereco);
		System.out.println("Endereço criado");

		Paciente paciente = new Paciente();
		paciente.setNome("Stefson Andrade");
		paciente.setSexo("M");
		paciente.setDataNascimento(sdfmBanco.parse("1964-12-15"));
		paciente.setTelefone("71988413931");
		paciente.setEndereco(endereco);
		pacienteDao.insert(paciente);
		System.out.println("Paciente criado");
		*/
		
		System.out.println("Lista de pacientes");
		List<Paciente> lista = pacienteDao.findAll();
		for(Paciente p: lista) {
			System.out.println(p);
		}
		
		System.out.println("Procurar especifico");
		Paciente paciente = pacienteDao.findById(3);
		System.out.println(paciente);
		
	}
}
