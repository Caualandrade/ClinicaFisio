package dao;

import java.util.Date;
import java.util.List;

import entidades.Consulta;
import entidades.Unidade;

public interface ConsultaDao {
	
	void insert(Consulta obj);

	void update(Consulta obj);

	void deleteById(Integer id);

	Consulta findById(Integer id);

	List<Consulta> findAll();
	
	List<Consulta> findByDate(String inicio, String fim);
	
}
