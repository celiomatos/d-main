package br.com.dmain.dmain.dao;

import br.com.dmain.dmain.model.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, JpaSpecificationExecutor<Pagamento> {

    @Transactional(readOnly = true)
    Page<Pagamento> findAll(Specification spec, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT date_part('year', pag_date) ano, sum(pag_valor) FROM scraper.pagamento " +
            "WHERE removido = false and pag_date > ?1  GROUP BY ano ORDER BY ano", nativeQuery = true)
    List<Object[]> findFiveYearsPagagmentos(java.sql.Date date);

    @Transactional(readOnly = true)
    @Query(value = "SELECT cre.cre_nome credor, sum(pag_valor) valor FROM scraper.pagamento pag " +
            "INNER JOIN scraper.credor cre on(pag.pag_cre_id = cre.cre_id) " +
            "WHERE pag_removido = false AND pag.pag_date >= ?1 AND pag.pag_date <= ?2 " +
            "GROUP BY credor ORDER BY valor DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopFiveCredores(java.sql.Date dateInicial, java.sql.Date dateFinal);

    @Transactional(readOnly = true)
    @Query(value = "SELECT org.org_sigla sigla, org.org_orgao orgao, sum(pag_valor) valor FROM scraper.pagamento pag " +
            "INNER JOIN scraper.orgao org on(pag.pag_org_id = org.org_id) " +
            "WHERE pag_removido = false AND pag.pag_date >= ?1 AND pag.pag_date <= ?2 " +
            "GROUP BY sigla, orgao ORDER BY valor DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopFiveOrgaos(java.sql.Date dateInicial, java.sql.Date dateFinal);

}
