package br.com.techsage.financas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.com.techsage.financas.enums.StatusLancamento;
import br.com.techsage.financas.enums.TipoLancamento;

@Entity
@Table(name = "lancamento")
public class Lancamento {
	
	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "descricao", length = 100, nullable = false)
	private String descricao;
	
	@Column(name = "mes", nullable = false)
	private int mes;
	
	@Column(name = "ano", nullable = false)	
	private int ano;
	
	@Column(name = "valor", nullable = false, precision = 16, scale = 2)	
	private BigDecimal valor;
	
	@Column(name = "tipo", length = 20, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private TipoLancamento tipo;
	
	@Column(name = "status", length = 20, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private StatusLancamento status;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "data_cadastro", nullable = false)
	@Convert( converter = Jsr310JpaConverters.LocalDateTimeConverter.class )	
	private LocalDateTime data_cadastro;
	
	public Lancamento() {
	}

	public Lancamento(int id, String descricao, int mes, int ano, BigDecimal valor, TipoLancamento tipo,
			StatusLancamento status, Usuario usuario, LocalDateTime data_cadastro) {
		this.id = id;
		this.descricao = descricao;
		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
		this.tipo = tipo;
		this.status = status;
		this.usuario = usuario;
		this.data_cadastro = data_cadastro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public StatusLancamento getStatus() {
		return status;
	}

	public void setStatus(StatusLancamento status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(LocalDateTime data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano, data_cadastro, descricao, id, mes, status, tipo, usuario, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		return ano == other.ano && Objects.equals(data_cadastro, other.data_cadastro)
				&& Objects.equals(descricao, other.descricao) && id == other.id && mes == other.mes
				&& status == other.status && tipo == other.tipo && Objects.equals(usuario, other.usuario)
				&& Objects.equals(valor, other.valor);
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", descricao=" + descricao + ", mes=" + mes + ", ano=" + ano + ", valor="
				+ valor + ", tipo=" + tipo + ", status=" + status + ", usuario=" + usuario + ", data_cadastro="
				+ data_cadastro + "]";
	}
	
	
}
