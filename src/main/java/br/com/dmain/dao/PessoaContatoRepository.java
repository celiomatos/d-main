package br.com.dmain.dao;

import br.com.dmain.model.PessoaContato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaContatoRepository extends JpaRepository<PessoaContato, Long> {
}
