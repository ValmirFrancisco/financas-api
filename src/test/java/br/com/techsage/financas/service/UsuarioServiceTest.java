package br.com.techsage.financas.service;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.techsage.financas.exception.RegraNegocioException;
import br.com.techsage.financas.model.entity.Usuario;
import br.com.techsage.financas.model.repository.UsuarioRepository;
import br.com.techsage.financas.model.service.UsuarioService;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;	
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// Scenery		
		UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);
		usuarioRepositoryMock.deleteAll();
		
		// Action/Execution
		service.validarEmail("jubiley.neves@email.com");

	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// Scenery
		Usuario usuario = new Usuario();
		usuario.setNome("Asdrubal Moncorvo Filho");
		usuario.setEmail("asdrubal.moncorvo@gmail.com");
		usuario.setSenha("123456");
		usuario.setData_cadastro(LocalDateTime.now());
		repository.save(usuario);
		
		// Action/Execution
		service.validarEmail("asdrubal.moncorvo@gmail.com");

	}	
	
}
