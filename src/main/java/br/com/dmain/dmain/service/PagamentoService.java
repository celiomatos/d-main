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


@Slf4j
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Page<Pagamento> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "data"));

        Specification spec = null;
        spec = PagamentoSpecs.isNrObPagamento("fdfdf");
        spec = spec.and(PagamentoSpecs.isNrNlPagamento("NL454545"));
        spec = spec.and(PagamentoSpecs.isOrgaoId(10));

        return pagamentoRepository.findAll(spec, pageable);
    }
}
