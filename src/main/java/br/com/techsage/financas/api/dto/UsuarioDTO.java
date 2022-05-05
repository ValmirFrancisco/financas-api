package br.com.techsage.financas.api.dto;

import java.util.Objects;

public class UsuarioDTO {
	
    private String email;
    private String nome;
    private String senha;
    
    
	public UsuarioDTO() {
	}


	public UsuarioDTO(String email, String nome, String senha) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	@Override
	public int hashCode() {
		return Objects.hash(email, nome, senha);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(nome, other.nome)
				&& Objects.equals(senha, other.senha);
	}


	@Override
	public String toString() {
		return "UsuarioDTO [email=" + email + ", nome=" + nome + ", senha=" + senha + "]";
	}
}
