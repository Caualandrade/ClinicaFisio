package aplicacaoTeste;

import java.util.List;

import dao.EspecialidadeDao;
import entidades.Especialidade;
import implementacaoDao.DaoFactory;

public class TesteEspecialidade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Teste de especialidade
		
		EspecialidadeDao especialidadeDao = DaoFactory.createEspecialidadeDao();

		List<Especialidade> lista = especialidadeDao.findAll();
		for (Especialidade esp : lista) {
			System.out.println(esp);
		}

		Especialidade especialidade = especialidadeDao.findById(9);
		especialidade.setNome("Pilates");
		especialidadeDao.update(especialidade);

	}

}
