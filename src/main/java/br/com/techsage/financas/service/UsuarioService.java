package br.com.techsage.financas.service;

import java.util.Optional;

import br.com.techsage.financas.model.entity.Usuario;

public interface UsuarioService {
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario> obterPorId(int id);
}
