package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Consulta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date Data_Hora;
	
	private Fisioterapeuta fisioterapeuta;
	private Paciente paciente;
	private Unidade unidade;
	
	public Consulta(Integer id, Date data_Hora, Fisioterapeuta fisioterapeuta, Paciente paciente, Unidade unidade) {
		super();
		this.id = id;
		Data_Hora = data_Hora;
		this.fisioterapeuta = fisioterapeuta;
		this.paciente = paciente;
		this.unidade = unidade;
	}
	
	public Consulta() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData_Hora() {
		return Data_Hora;
	}

	public void setData_Hora(Date data_Hora) {
		Data_Hora = data_Hora;
	}

	public Fisioterapeuta getFisioterapeuta() {
		return fisioterapeuta;
	}

	public void setFisioterapeuta(Fisioterapeuta fisioterapeuta) {
		this.fisioterapeuta = fisioterapeuta;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
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
		Consulta other = (Consulta) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Consulta [id=" + id + ", Data_Hora=" + Data_Hora + ", fisioterapeuta=" + fisioterapeuta + ", paciente="
				+ paciente + ", unidade=" + unidade + "]";
	}
	
	public String consultaPerso() {
		return "Consulta [id=" + id + ", Data_Hora=" + Data_Hora + ", fisioterapeuta=" + fisioterapeuta.getNome() + ", paciente="
				+ paciente.getNome() + ", unidade=" + unidade.getNome() + "]";
	}
	

}
