package br.com.techsage.financas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techsage.financas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

}
