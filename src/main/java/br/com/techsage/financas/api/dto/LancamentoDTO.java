package br.com.techsage.financas.api.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class LancamentoDTO {

	private int id;
	private String descricao;
	private int mes;
	private int ano;
	private BigDecimal valor;
	private String tipo;
	private String status;
	private int usuario;
	
	public LancamentoDTO() {
	}

	public LancamentoDTO(int id, String descricao, int mes, int ano, BigDecimal valor, String tipo, String status,
			int usuario) {
		this.id = id;
		this.descricao = descricao;
		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
		this.tipo = tipo;
		this.status = status;
		this.usuario = usuario;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano, descricao, id, mes, status, tipo, usuario, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LancamentoDTO other = (LancamentoDTO) obj;
		return ano == other.ano && Objects.equals(descricao, other.descricao) && id == other.id && mes == other.mes
				&& Objects.equals(status, other.status) && Objects.equals(tipo, other.tipo) && usuario == other.usuario
				&& Objects.equals(valor, other.valor);
	}

	@Override
	public String toString() {
		return "LancamentoDTO [id=" + id + ", descricao=" + descricao + ", mes=" + mes + ", ano=" + ano + ", valor="
				+ valor + ", tipo=" + tipo + ", status=" + status + ", usuario=" + usuario + "]";
	}
}
