package br.com.techsage.financas.api.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techsage.financas.api.dto.UsuarioDTO;
import br.com.techsage.financas.exception.ErroAutenticacao;
import br.com.techsage.financas.exception.RegraNegocioException;
import br.com.techsage.financas.model.entity.Usuario;
import br.com.techsage.financas.model.service.LancamentoService;
import br.com.techsage.financas.model.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
    private UsuarioService service;
    private LancamentoService lancamentoService;
    
    public UsuarioResource(UsuarioService service, LancamentoService lancamentoService) {
                   this.service = service;
                   this.lancamentoService = lancamentoService;
    }
    
    @PostMapping
    public ResponseEntity salvar( @RequestBody UsuarioDTO dto) {
                   Usuario usuario = new Usuario();
                   usuario.setNome(dto.getNome());
                   usuario.setEmail(dto.getEmail());
                   usuario.setSenha(dto.getSenha());
                   usuario.setData_cadastro(LocalDateTime.now());
                   
                   try {
                                 Usuario usuarioSalvo = service.salvarUsuario(usuario);
                                 return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
                   }
                   catch (RegraNegocioException e ) {
                                 return ResponseEntity.badRequest().body(e.getMessage());
                   }
                   
    }
    
    @PostMapping("/autenticar")
    public ResponseEntity autenticar( @RequestBody UsuarioDTO dto) {
    
                   try {
                                 Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
                                 return ResponseEntity.ok(usuarioAutenticado);
                   }
                   catch (ErroAutenticacao e ) {
                                 return ResponseEntity.badRequest().body(e.getMessage());
                   }
                   
    }
    
	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo( @PathVariable("id") Integer id ) {
		Optional<Usuario> usuario = service.obterPorId(id);
		
		if(!usuario.isPresent()) {
			return new ResponseEntity( HttpStatus.NOT_FOUND );
		}
		
		BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}

}
