package br.com.dmain.dmain.controller;

import br.com.dmain.dmain.dto.FiveYearsDto;
import br.com.dmain.dmain.dto.PagamentoSearchDto;
import br.com.dmain.dmain.dto.TopFiveCredoresDto;
import br.com.dmain.dmain.dto.TopFiveOrgaosDto;
import br.com.dmain.dmain.model.Pagamento;
import br.com.dmain.dmain.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


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

    @GetMapping("/five-years-pagamento")
    public List<FiveYearsDto> fiveYearsPagagmentos() {
        return pagamentoService.fiveYearsPagagmentos();
    }

    @GetMapping("/top-five-credores")
    public List<TopFiveCredoresDto> topFiveCredores(@RequestParam Date dateInicial, @RequestParam Date dateFinal) {
        return pagamentoService.topFiveCredores(dateInicial, dateFinal);
    }

    @GetMapping("/top-five-orgaos")
    public List<TopFiveOrgaosDto> topFiveOrgaos(@RequestParam Date dateInicial, @RequestParam Date dateFinal) {
        return pagamentoService.topFiveOrgaos(dateInicial, dateFinal);
    }
}
