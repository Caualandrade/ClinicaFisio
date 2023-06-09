package dao;

import java.util.List;

import entidades.Endereco;

public interface EnderecoDao {

	void insert(Endereco obj);

	void update(Endereco obj);

	void deleteById(Integer id);

	Endereco findById(Integer id);

	List<Endereco> findAll();
}
