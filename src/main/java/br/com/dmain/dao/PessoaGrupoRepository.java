package br.com.dmain.dao;

import br.com.dmain.model.PessoaGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaGrupoRepository extends JpaRepository<PessoaGrupo, Long> {
}
