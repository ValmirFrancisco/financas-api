package br.com.techsage.financas.model.service.Impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techsage.financas.enums.StatusLancamento;
import br.com.techsage.financas.enums.TipoLancamento;
import br.com.techsage.financas.exception.RegraNegocioException;
import br.com.techsage.financas.model.entity.Lancamento;
import br.com.techsage.financas.model.repository.LancamentoRepository;
import br.com.techsage.financas.model.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	public LancamentoRepository repository;
	
	public LancamentoServiceImpl(LancamentoRepository repository) {
		this.repository = repository;
	}
	
	
	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validar(lancamento);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example<Lancamento> example = Example.of( lancamentoFiltro,
				ExampleMatcher.matching()
					.withIgnorePaths("id") 
					.withIgnorePaths("id_usuario")
					.withIgnorePaths("data_cadastro")
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(example);
		//return repository.findAll().stream().filter(l -> l.getUsuario().getId() == lancamentoFiltro.getUsuario().getId()).collect(Collectors.toList());
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);
	}


	@Override
	public void validar(Lancamento lancamento) {
		
		if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descrição válida");
		}
		
		if (lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Informe um mês válido");
		}
		
		if (lancamento.getAno() < 1 || Integer.toString(lancamento.getAno()).length() != 4) {
			throw new RegraNegocioException("Informe um ano válido");
		}	
		
		if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == 0) {
			throw new RegraNegocioException("Informe um usuário válido");
		}
		
		if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um valor válido");
		}
		
		if(lancamento.getTipo() == null) {
			throw new RegraNegocioException("Informe um tipo de Lançamento.");
		}	
		
	}


	@Override
	public Optional<Lancamento> obterPorId(Integer id) {
		return repository.findById(id);
	}


	@Override
	public BigDecimal obterSaldoPorUsuario(Integer id) {
		BigDecimal receitas = repository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.RECEITA, StatusLancamento.EFETIVADO);
		BigDecimal despesas = repository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.DESPESA, StatusLancamento.EFETIVADO);
		
		if(receitas == null) {
			receitas = BigDecimal.ZERO;
		}
		
		if(despesas == null) {
			despesas = BigDecimal.ZERO;
		}
		
		return receitas.subtract(despesas);
	}

}
