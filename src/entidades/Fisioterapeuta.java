package entidades;

import java.io.Serializable;
//import java.sql.Date;
import java.util.Date;
import java.util.Objects;

public class Fisioterapeuta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String numeroRegistro;
	private Date dataDeNascimento;
	private String sexo;
	private String telefone;

	private Endereco endereco;
	private Especialidade especialidade;

	
	public Fisioterapeuta(Integer id, String nome, String numeroRegistro, Date dataDeNascimento, String sexo,
			String telefone, Endereco endereco, Especialidade especialidade) {
		super();
		this.id = id;
		this.nome = nome;
		this.numeroRegistro = numeroRegistro;
		this.dataDeNascimento = dataDeNascimento;
		this.sexo = sexo;
		this.telefone = telefone;
		this.endereco = endereco;
		this.especialidade = especialidade;
	}

	public Fisioterapeuta() {

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

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
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
		Fisioterapeuta other = (Fisioterapeuta) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Fisioterapeuta [id=" + id + ", nome=" + nome + ", numeroRegistro=" + numeroRegistro
				+ ", dataDeNascimento=" + dataDeNascimento + ", sexo=" + sexo + ", telefone=" + telefone + ", endereco="
				+ endereco + ", especialidade=" + especialidade + "]";
	}

	

}
