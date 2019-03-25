package br.com.dmain.dmain.service;

import br.com.dmain.dmain.dao.PagamentoRepository;
import br.com.dmain.dmain.dto.PagamentoSearchDto;
import br.com.dmain.dmain.model.Pagamento;
import br.com.dmain.dmain.spec.PagamentoSpecs;
import br.com.dmain.dmain.util.Util;
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

        Sort sort = new Sort(Sort.Direction.DESC, "data");
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

        if (!StringUtils.isEmpty(pagSearchDto.getDataInicial())) {
            spec = spec.and(PagamentoSpecs.isDataLess(pagSearchDto.getDataFinal()));
        }

        if (!StringUtils.isEmpty(pagSearchDto.getValorInicial())) {
            spec = spec.and(PagamentoSpecs.isValorGreater(new BigDecimal(pagSearchDto.getValorInicial())));
        }

        if (!StringUtils.isEmpty(pagSearchDto.getValorFinal())) {
            spec = spec.and(PagamentoSpecs.isValorLess(new BigDecimal(pagSearchDto.getValorFinal())));
        }
        System.out.println(sumPagamentoValor(pagSearchDto));
        return pagamentoRepository.findAll(spec, pageable);
    }

    public BigDecimal sumPagamentoValor(PagamentoSearchDto pagSearchDto) {
        StringBuilder query = new StringBuilder("select coalesce(sum(t.valor),0) from Pagamento t ");
        if (pagSearchDto.getOrgaos() != null && !pagSearchDto.getOrgaos().isEmpty()) {
            String orgaos = Util.arrayToCommaDelimited(pagSearchDto.getOrgaos().toString());
            query.append("where t.orgao.id in");
            query.append(orgaos);
        }
        return (BigDecimal) em.createQuery(query.toString()).getSingleResult();
    }

}
