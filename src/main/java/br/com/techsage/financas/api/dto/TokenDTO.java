package br.com.techsage.financas.api.dto;


public class TokenDTO {
	private String nome;
	private String token;
	
	public TokenDTO() {
		super();
	}
	
	public TokenDTO(String nome, String token) {
		super();
		this.nome = nome;
		this.token = token;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
