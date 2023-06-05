package aplicacao;

import dao.EnderecoDao;
import implementacaoDao.DaoFactory;

public class TesteConexaoBanco {

	public static void main(String[] args) {
		
		/*
		Connection conn = DB.getConnection();
		System.out.println("Conectado");
		DB.closeConnection();
		System.out.println("Desconectado");
		*/
		
		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
		enderecoDao.deleteById(3);
		System.out.println("Deletado");
		
		
	}

}
