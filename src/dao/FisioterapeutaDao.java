package dao;

import java.util.List;

import entidades.Fisioterapeuta;
import entidades.Unidade;

public interface FisioterapeutaDao {
	void insert(Fisioterapeuta obj);

	void update(Fisioterapeuta obj);

	void deleteById(Integer id);

	Fisioterapeuta findById(Integer id);

	List<Fisioterapeuta> findAll();
}
