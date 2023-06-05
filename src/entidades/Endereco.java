package entidades;

import java.io.Serializable;
import java.util.Objects;

public class Endereco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String logradouro;
	private Integer numEndereco;
	private String bairro;
	private String cidade;
	private String cep;
	
	public Endereco(Integer id, String logradouro, Integer numEndereco, String bairro, String cidade, String cep) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.numEndereco = numEndereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
	}
	
	public Endereco() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumEndereco() {
		return numEndereco;
	}

	public void setNumEndereco(Integer numEndereco) {
		this.numEndereco = numEndereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", logradouro=" + logradouro + ", Numero endereco=" + numEndereco + ", bairro="
				+ bairro + ", cidade=" + cidade + ", cep=" + cep + "]";
	}
	
	
	

}
