package br.com.dmain.dmain.service;

import br.com.dmain.dmain.dao.PagamentoRepository;
import br.com.dmain.dmain.spec.PagamentoSpecs;
import br.com.dmain.dmain.model.Pagamento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Page<Pagamento> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "data"));

        Specification spec = null;

        List<Long> orgaos = new ArrayList<>();
        orgaos.add(10L);
        orgaos.add(20L);
        spec = PagamentoSpecs.isOrgaos(orgaos);

        List<Long> credores = new ArrayList<>();
        credores.add(1L);
        credores.add(5L);
        spec = spec.and(PagamentoSpecs.isCredores(credores));

        spec = spec.and(PagamentoSpecs.isGreaterData(new Date()));
        spec = spec.and(PagamentoSpecs.isLessData(new Date()));

        spec = spec.and(PagamentoSpecs.isGreaterValor(new BigDecimal(10)));
        spec = spec.and(PagamentoSpecs.isLessValor(new BigDecimal(5)));

        return pagamentoRepository.findAll(spec, pageable);
    }
}
