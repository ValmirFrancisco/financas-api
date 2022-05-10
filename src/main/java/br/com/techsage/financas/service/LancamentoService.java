package br.com.techsage.financas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import br.com.techsage.financas.enums.StatusLancamento;
import br.com.techsage.financas.model.entity.Lancamento;

public interface LancamentoService {
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar( Lancamento lancamentoFiltro );
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);
	
	Optional<Lancamento> obterPorId(int id);
	
	BigDecimal obterSaldoPorUsuario(int id);
}
