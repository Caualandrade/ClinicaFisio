package entidades;

import java.io.Serializable;
import java.util.Objects;

public class Unidade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	private Endereco endereco;

	public Unidade() {

	}

	public Unidade(Integer id, String nome, Endereco endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unidade other = (Unidade) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Unidade [id=" + id + ", nome=" + nome + ", endereco=" + endereco + "]";
	}

}
