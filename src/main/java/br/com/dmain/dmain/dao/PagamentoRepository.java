package br.com.dmain.dmain.dao;

import br.com.dmain.dmain.model.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, JpaSpecificationExecutor<Pagamento> {

    Page<Pagamento> findAll(Specification spec, Pageable pageable);
}
