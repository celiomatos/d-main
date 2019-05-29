package br.com.dmain.dao;

import br.com.dmain.model.Classificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    Page<Classificacao> findByNomeIgnoreCaseContainingOrderByNomeAsc(String nome, Pageable pageable);
}
