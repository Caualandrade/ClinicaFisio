package aplicacaoTeste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import dao.EnderecoDao;
import dao.EspecialidadeDao;
import dao.FisioterapeutaDao;
import entidades.Endereco;
import entidades.Especialidade;
import entidades.Fisioterapeuta;
import implementacaoDao.DaoFactory;

public class TesteFisio {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		// Inserindo Fisio
		Scanner sc = new Scanner(System.in);

		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
		FisioterapeutaDao fisioterapeutaDao = DaoFactory.createFisioterapeutaDao();
		EspecialidadeDao especialidadeDao = DaoFactory.createEspecialidadeDao();

		Fisioterapeuta fisio = new Fisioterapeuta();
		
		SimpleDateFormat dateBrasil = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatSistema = new SimpleDateFormat("yyyy-MM-dd");

		fisio.setNome("Shirley");
		fisio.setDataDeNascimento(dateFormatSistema.parse("2003-09-12"));
		fisio.setNumeroRegistro("XXXXXX-AA");
		List<Endereco> listaEndereco = enderecoDao.findAll();
		for (Endereco end : listaEndereco) {
			System.out.println(end);
		}
		System.out.println("Selecione o endereco através do id");

		int resp = sc.nextInt();
		Endereco endereco = enderecoDao.findById(resp);
		fisio.setEndereco(endereco);

		List<Especialidade> listaEspecialidade = especialidadeDao.findAll();
		for (Especialidade esp : listaEspecialidade) {
			System.out.println(esp);
		}
		System.out.println("Selecione a especialidade através do id");
		int resp2 = sc.nextInt();
		Especialidade especialidade = especialidadeDao.findById(resp2);
		fisio.setEspecialidade(especialidade);

		fisio.setSexo("F");
		fisio.setTelefone("71988003344");
		fisioterapeutaDao.insert(fisio);
		
	}
}
