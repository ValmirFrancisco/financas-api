package br.com.techsage.financas.service;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.techsage.financas.exception.RegraNegocioException;
import br.com.techsage.financas.model.entity.Usuario;
import br.com.techsage.financas.model.repository.UsuarioRepository;
import br.com.techsage.financas.model.service.UsuarioService;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;	
	
	@Test
	public void deveValidarEmail() {
		// Scenery
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		// Action
		service.validarEmail("email@email.com");
	}
	
	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// Scenery
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		// Action
		org.junit.jupiter.api.Assertions
			.assertThrows(RegraNegocioException.class, () -> service.validarEmail("asdrubal.moncorvo@gmail.com"));
	}
	
	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		// Scenery
		String email = "asdrubal.moncorvo@gmail.com";

		Usuario usuario = new Usuario();
		usuario.setNome("Asdrubal Moncorvo Filho");
		usuario.setEmail("asdrubal.moncorvo@gmail.com");
		usuario.setSenha("123456");
		usuario.setData_cadastro(LocalDateTime.now());
		repository.save(usuario);
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		// Action
		org.junit.jupiter.api.Assertions
			.assertThrows(RegraNegocioException.class, () -> service.salvarUsuario(usuario) ) ;
		
		// Check
		Mockito.verify( repository, Mockito.never() ).save(usuario);
	}
	
}
