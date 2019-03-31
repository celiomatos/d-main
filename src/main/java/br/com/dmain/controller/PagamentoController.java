package br.com.dmain.controller;

import br.com.dmain.dto.FiveYearsDto;
import br.com.dmain.dto.PagamentoSearchDto;
import br.com.dmain.model.Pagamento;
import br.com.dmain.service.PagamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Api(value = "d main")
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/search")
    public Page<Pagamento> findPagamentos(@RequestBody @Valid PagamentoSearchDto pagSearchDto) {
        return pagamentoService.findAll(pagSearchDto);
    }

    @PostMapping("/sum-pagamento-valor")
    public BigDecimal sumPagamentoValor(@RequestBody PagamentoSearchDto pagSearchDto) {
        return pagamentoService.sumPagamentoValor(pagSearchDto);
    }

    @ApiOperation(value = "five years")
    @GetMapping("/five-years-pagamento")
    public List<FiveYearsDto> fiveYearsPagagmentos() {
        return pagamentoService.fiveYearsPagagmentos();
    }

}
