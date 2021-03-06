package br.com.techsage.financas.api.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.techsage.financas.api.dto.AtualizaStatusDTO;
import br.com.techsage.financas.api.dto.LancamentoDTO;
import br.com.techsage.financas.enums.StatusLancamento;
import br.com.techsage.financas.enums.TipoLancamento;
import br.com.techsage.financas.exception.RegraNegocioException;
import br.com.techsage.financas.model.entity.Lancamento;
import br.com.techsage.financas.model.entity.Usuario;
import br.com.techsage.financas.model.service.LancamentoService;
import br.com.techsage.financas.model.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

	private LancamentoService service;
	private UsuarioService usuarioService;

	public LancamentoResource(LancamentoService service, UsuarioService usuarioService) {
		this.service = service;
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("{id}")
	public ResponseEntity obterLancamento(@PathVariable("id") int id) {
		return service.obterPorId(id)
				.map( lancamento -> new ResponseEntity(converter(lancamento), HttpStatus.OK) )
				.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
	}
	

	@PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") int id, @RequestBody LancamentoDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Lancamento lancamento = converter(dto);
				lancamento.setId(entity.getId());
				service.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lan??amento n??o encontrado na base de dados", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus( @PathVariable("id") Integer id , @RequestBody AtualizaStatusDTO dto ) {
		return service.obterPorId(id).map( entity -> {
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("N??o foi poss??vel atualizar o status do lan??amento, envie um status v??lido.");
			}
			
			try {
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		
		}).orElseGet( () ->
		new ResponseEntity("Lan??amento n??o encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}	

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") int id) {
		return service.obterPorId(id).map(entity -> {
			try {
				service.deletar(entity);
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lan??amento n??o encontrado na base de dados", HttpStatus.BAD_REQUEST));
	}

	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam("usuario") int idUsuario) {

		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricao(descricao);

		if (mes != null) {
			lancamentoFiltro.setMes(mes);
		}
		if (ano != null) {
			lancamentoFiltro.setAno(ano);
		}
		if (tipo != null) {
			if (tipo.equals("RECEITA")) {
				lancamentoFiltro.setTipo(TipoLancamento.RECEITA);
			}
			if (tipo.equals("DESPESA")) {
				lancamentoFiltro.setTipo(TipoLancamento.DESPESA);
			}
		}
		if (status != null) {
			if (status.equals("CANCELADO")) {
				lancamentoFiltro.setStatus(StatusLancamento.CANCELADO);
			}
			if (status.equals("EFETIVADO")) {
				lancamentoFiltro.setStatus(StatusLancamento.EFETIVADO);
			}
			if (status.equals("PENDENTE")) {
				lancamentoFiltro.setStatus(StatusLancamento.PENDENTE);
			}
		}

		Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
		if (!usuario.isPresent()) {
			return ResponseEntity.badRequest()
					.body("N??o foi poss??vel realizar a consulta. Usu??rio n??o encontrado para o Id informado.");
		} else {
			lancamentoFiltro.setUsuario(usuario.get());
		}

		List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
		return ResponseEntity.ok(lancamentos);
	}
	
	private LancamentoDTO converter(Lancamento lancamento) {
		LancamentoDTO lancamentoDTO = new LancamentoDTO(lancamento.getId(),
				lancamento.getDescricao(),
				lancamento.getMes(),
				lancamento.getAno(),
				lancamento.getValor(),
				lancamento.getTipo().name(),
				lancamento.getStatus().name(),
				lancamento.getUsuario().getId());
		return lancamentoDTO;			
	}	

	private Lancamento converter(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(dto.getId());
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		
		Usuario usuario = usuarioService
			.obterPorId(dto.getUsuario())
			.orElseThrow( () -> new RegraNegocioException("Usu??rio n??o encontrado para o Id informado.") );
		
		lancamento.setUsuario(usuario);

		if(dto.getTipo() != null) {
			lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		}
		
		if(dto.getStatus() != null) {
			lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		}
		
		return lancamento;
	}

}
