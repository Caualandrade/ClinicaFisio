package dao;

import java.util.List;

import entidades.Endereco;
import entidades.Unidade;

public interface UnidadeDao {
	
	void insert(Unidade obj);

	void update(Unidade obj);

	void deleteById(Integer id);

	Unidade findById(Integer id);

	List<Unidade> findAll();
	
	List<Unidade> findByEndereco(Endereco endereco);
}
