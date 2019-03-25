package br.com.dmain.dmain.controller;

import br.com.dmain.dmain.dto.PagamentoSearchDto;
import br.com.dmain.dmain.model.Pagamento;
import br.com.dmain.dmain.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;


@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping()
    public Page<Pagamento> findPagamentos(@RequestBody @Valid PagamentoSearchDto pagSearchDto) {
        return pagamentoService.findAll(pagSearchDto);
    }

    @PostMapping("/sum-pagamento-valor")
    public BigDecimal sumPagamentoValor(@RequestBody PagamentoSearchDto pagSearchDto) {
        return pagamentoService.sumPagamentoValor(pagSearchDto);
    }
}
