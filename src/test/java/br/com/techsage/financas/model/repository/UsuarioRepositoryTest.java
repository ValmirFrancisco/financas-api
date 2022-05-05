package br.com.techsage.financas.model.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.techsage.financas.model.entity.Usuario;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveVerificarAExistenciaDeEmail() {
		// Scenery
		Usuario usuario = new Usuario();
		usuario.setNome("Asdrubal Moncorvo Filho");
		usuario.setEmail("asdrubal.moncorvo@gmail.com");
		usuario.setSenha("123456");
		usuario.setData_cadastro(LocalDateTime.now());
		entityManager.persist(usuario);
		
		// Action/Execution
		boolean result = repository.existsByEmail("asdrubal.moncorvo@gmail.com");
		
		//Checking
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		// Scenery
		
		// Action/Execution
		boolean result = repository.existsByEmail("asdrubal.moncorvo@gmail.com");
		
		//Checking
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		// Scenery
		Usuario usuario = new Usuario();
		usuario.setNome("Asdrubal Moncorvo Filho");
		usuario.setEmail("asdrubal.moncorvo@gmail.com");
		usuario.setSenha("123456");
		usuario.setData_cadastro(LocalDateTime.now());
		entityManager.persist(usuario);
		
		// Action/Execution
		Usuario usuarioSalvo = repository.save(usuario);
		
		//Checking
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBuscarUsuarioPorEmail() {
		// Scenery
		Usuario usuario = new Usuario();
		usuario.setNome("Asdrubal Moncorvo Filho");
		usuario.setEmail("asdrubal.moncorvo@gmail.com");
		usuario.setSenha("123456");
		usuario.setData_cadastro(LocalDateTime.now());
		entityManager.persist(usuario);
		
		// Action/Execution
		Optional<Usuario> result = repository.findByEmail("asdrubal.moncorvo@gmail.com");
		
		//Checking
		Assertions.assertThat(result.isPresent()).isTrue();		
	}
	
	@Test
	public void deveRetornarVazioQuandoBuscarUsuarioPorEmailSeNaoExistirNaBase() {
		// Scenery
		
		// Action/Execution
		Optional<Usuario> result = repository.findByEmail("asdrubal.moncorvo@gmail.com");
		
		//Checking
		Assertions.assertThat(result.isEmpty()).isTrue();		
	}
}
