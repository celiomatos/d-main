package br.com.dmain.service;

import br.com.dmain.dao.PagamentoRepository;
import br.com.dmain.dto.FiveYearsDto;
import br.com.dmain.dto.PagamentoSearchDto;
import br.com.dmain.model.Pagamento;
import br.com.dmain.spec.PagamentoSpecs;
import br.com.dmain.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Slf4j
@Service
public class PagamentoService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    /**
     * @param pagSearchDto dto.
     * @return payments.
     */
    public Page<Pagamento> findAll(PagamentoSearchDto pagSearchDto) {

        Sort sort = new Sort(Sort.Direction.ASC, "orgao.nome", "credor.nome", "data");
        Pageable pageable = PageRequest.of(pagSearchDto.getPage(), pagSearchDto.getSize(), sort);

        Specification<Pagamento> spec = (root, query, builder) -> builder.equal(root.get("removido"), Boolean.FALSE);

        if (pagSearchDto.getOrgaos() != null && !pagSearchDto.getOrgaos().isEmpty()) {
            spec = spec.and(PagamentoSpecs.isOrgaos(pagSearchDto.getOrgaos()));
        }

        if (pagSearchDto.getCredores() != null && !pagSearchDto.getCredores().isEmpty()) {
            spec = spec.and(PagamentoSpecs.isCredores(pagSearchDto.getCredores()));
        }

        if (pagSearchDto.getFontes() != null && !pagSearchDto.getFontes().isEmpty()) {
            spec = spec.and(PagamentoSpecs.isFontes(pagSearchDto.getFontes()));
        }

        if (pagSearchDto.getClassificacoes() != null && !pagSearchDto.getClassificacoes().isEmpty()) {
            spec = spec.and(PagamentoSpecs.isClassificacoes(pagSearchDto.getClassificacoes()));
        }

        if (!StringUtils.isEmpty(pagSearchDto.getDataInicial())) {
            spec = spec.and(PagamentoSpecs.isDataGreater(pagSearchDto.getDataInicial()));
        }

        if (!StringUtils.isEmpty(pagSearchDto.getDataFinal())) {
            spec = spec.and(PagamentoSpecs.isDataLess(pagSearchDto.getDataFinal()));
        }

        if (!StringUtils.isEmpty(pagSearchDto.getValorInicial())) {
            spec = spec.and(PagamentoSpecs.isValorGreater(new BigDecimal(pagSearchDto.getValorInicial())));
        }

        if (!StringUtils.isEmpty(pagSearchDto.getValorFinal())) {
            spec = spec.and(PagamentoSpecs.isValorLess(new BigDecimal(pagSearchDto.getValorFinal())));
        }

        return pagamentoRepository.findAll(spec, pageable);
    }

    /**
     * @param pagSearchDto parametros de pesquisa.
     * @return sum.
     */
    public BigDecimal sumPagamentoValor(PagamentoSearchDto pagSearchDto) {
        StringBuilder query = new StringBuilder();
        query.append("select coalesce(sum(t.valor),0) from Pagamento t where t.removido = false ");

        if (pagSearchDto.getOrgaos() != null && !pagSearchDto.getOrgaos().isEmpty()) {
            String orgao = Util.arrayToCommaDelimited(pagSearchDto.getOrgaos().toString());

            query.append("and t.orgao.id in");
            query.append(orgao);
        }

        if (pagSearchDto.getCredores() != null && !pagSearchDto.getCredores().isEmpty()) {
            String credor = Util.arrayToCommaDelimited(pagSearchDto.getCredores().toString());

            query.append(" and t.credor.id in");
            query.append(credor);
        }

        if (pagSearchDto.getFontes() != null && !pagSearchDto.getFontes().isEmpty()) {
            String fonte = Util.arrayToStringCommaDelimited(pagSearchDto.getFontes().toString());

            query.append(" and t.fonte.id in");
            query.append(fonte);
        }

        if (pagSearchDto.getClassificacoes() != null && !pagSearchDto.getClassificacoes().isEmpty()) {
            String classificacao = Util.arrayToCommaDelimited(pagSearchDto.getClassificacoes().toString());

            query.append(" and t.classificacao.id in");
            query.append(classificacao);
        }

        if (!StringUtils.isEmpty(pagSearchDto.getDataInicial())) {
            query.append(" and t.data >= '");
            query.append(pagSearchDto.getDataInicial());
            query.append("'");
        }

        if (!StringUtils.isEmpty(pagSearchDto.getDataFinal())) {
            query.append(" and t.data <= '");
            query.append(pagSearchDto.getDataFinal());
            query.append("'");
        }

        if (!StringUtils.isEmpty(pagSearchDto.getValorInicial())) {
            query.append(" and t.valor >= ");
            query.append(pagSearchDto.getValorInicial());
        }

        if (!StringUtils.isEmpty(pagSearchDto.getValorFinal())) {
            query.append(" and t.valor <= ");
            query.append(pagSearchDto.getValorFinal());
        }

        return (BigDecimal) em.createQuery(query.toString()).getSingleResult();
    }

    /**
     * @return five years.
     */
    public List<FiveYearsDto> fiveYearsPagamentos() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 5);
        List<Object[]> result = pagamentoRepository.findFiveYearsPagagmentos(new java.sql.Date(cal.getTimeInMillis()));
        List<FiveYearsDto> fiveYears = new ArrayList<>();
        for (Object[] obj : result) {
            fiveYears.add(new FiveYearsDto(obj[0].toString().substring(0, 4), new BigDecimal(obj[1].toString())));
        }
        return fiveYears;
    }

}
