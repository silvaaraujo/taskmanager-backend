package br.com.silvaaraujo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.silvaaraujo.model.enuns.StatusTask;

@Table
@Entity
public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="O título deve ser informado.")
	@Length(min=5, max=20, message="O título deve possuir no mínimo 5 e no máximo 20 caracteres.")
	@Column(name="titulo", nullable=false, length=20)
	private String titulo;
	
	private String descricao;
	
	@Column(name="data_criacao")
	@Temporal(TemporalType.DATE)
	private Date dataCriacao;
	
	@Column(name="data_edicao")
	@Temporal(TemporalType.DATE)
	private Date dataEdicao;
	
	@Column(name="data_conclusao")
	@Temporal(TemporalType.DATE)
	private Date dataConclusao;
	
	@Column(name="status", nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private StatusTask status;

	public Task() {
		super();
		this.dataCriacao = new Date();
		this.status = StatusTask.PENDENTE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataEdicao() {
		return dataEdicao;
	}

	public void setDataEdicao(Date dataEdicao) {
		this.dataEdicao = dataEdicao;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
	
	public StatusTask getStatus() {
		return status;
	}

	public void setStatus(StatusTask status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Titulo: " + this.titulo + 
			   " - Status: " + this.status.getLabel();
	}
	
}
