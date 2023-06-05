package aplicacaoTeste;

import java.util.List;

import dao.EnderecoDao;
import dao.UnidadeDao;
import entidades.Endereco;
import entidades.Unidade;
import implementacaoDao.DaoFactory;

public class TesteUnidade {

	public static void main(String[] args) {

		UnidadeDao unidadeDao = DaoFactory.createUnidadeDao();
		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();

		
		Unidade unidade = new Unidade();
		Endereco endereco = new Endereco();
		
		List<Unidade> lista = unidadeDao.findAll();
		for(Unidade u: lista) {
			System.out.println(u);
		}
		/*
		endereco.setLogradouro("Rua Adelino Santos");
		endereco.setBairro("Liberdade");
		endereco.setCidade("Salvador");
		endereco.setNumEndereco(5);
		endereco.setCep("41650195");
		enderecoDao.insert(endereco);

		unidade.setNome("NeoFisio Liberdade");
		unidade.setEndereco(endereco);
		unidadeDao.insert(unidade);
		*/
	}
}
