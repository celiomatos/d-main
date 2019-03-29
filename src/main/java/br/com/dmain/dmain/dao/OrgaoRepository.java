package br.com.dmain.dmain.dao;

import br.com.dmain.dmain.model.Orgao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT org.org_sigla sigla, org.org_orgao orgao, sum(pag_valor) valor FROM scraper.pagamento pag " +
            "INNER JOIN scraper.orgao org on(pag.pag_org_id = org.org_id) " +
            "WHERE pag_removido = false AND pag.pag_date >= ?1 AND pag.pag_date <= ?2 " +
            "GROUP BY sigla, orgao ORDER BY valor DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopFiveOrgaos(java.sql.Date dateInicial, java.sql.Date dateFinal);

    Page<Orgao> findByNomeContainingOrderByNomeAsc(String nome, Pageable pageable);
}
