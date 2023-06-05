package aplicacaoTeste;

import java.util.List;
import java.util.Scanner;

import dao.EnderecoDao;
import entidades.Endereco;
import implementacaoDao.DaoFactory;

public class TesteEndereco {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int resp;

		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
		Endereco endereco = new Endereco();

		List<Endereco> lista = enderecoDao.findAll();

		do {
			System.out.println("MENU");
			System.out.println(
					" 1 Ver enderecos\n 2 Editar endereco \n " + "3 Deletar endereco" + "\n 4 Adicionar\n 5 Sair");
			resp = sc.nextInt();

			switch (resp) {
			case 1:
				System.out.println("Lista de enderecos");
				for (Endereco end : lista) {
					System.out.println(end);
				}
				break;
			case 2:
				for (Endereco end : lista) {
					System.out.println(end);
				}
				System.out.println("Qual endereco deseja editar?");
				resp = sc.nextInt();
				endereco = enderecoDao.findById(resp);

				sc.nextLine();
				System.out.println("O que deseja editar");
				String atributo = sc.nextLine().toLowerCase();

				System.out.println("Digite a alteração");
				String dadoAlt = sc.nextLine();

				if (atributo.equals("logradouro")) {
					endereco.setLogradouro(dadoAlt);
					enderecoDao.update(endereco);
					System.out.println("Endereco alterado com sucesso");
				}else if(atributo.equals("cep")){
					endereco.setCep(dadoAlt);
					enderecoDao.update(endereco);
					System.out.println("Endereco alterado com sucesso");
				}
				break;
			case 3:
				for (Endereco end : lista) {
					System.out.println(end);
				}
				System.out.println("Qual endereco deseja deletar");
				resp = sc.nextInt();
				enderecoDao.deleteById(resp);
				System.out.println("Endereco deletado com sucesso");
				break;
			case 4:
				sc.nextLine();
				System.out.println("Digite o logradouro");
				endereco.setLogradouro(sc.nextLine());
				System.out.println("Digite o Cep");
				endereco.setCep(sc.nextLine());
				System.out.println("Digite o bairro");
				endereco.setBairro(sc.nextLine());
				System.out.println("Digite a cidade");
				endereco.setCidade(sc.nextLine());
				System.out.println("Digite o numero de endereco");
				endereco.setNumEndereco(sc.nextInt());
				enderecoDao.insert(endereco);
				break;
			}
		} while (resp != 5);
		sc.close();
	}
}
