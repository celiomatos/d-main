package br.com.dmain.controller;

import br.com.dmain.dto.FiveYearsDto;
import br.com.dmain.dto.PagamentoSearchDto;
import br.com.dmain.model.Pagamento;
import br.com.dmain.service.ExcelGeneratorService;
import br.com.dmain.service.PagamentoService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private ExcelGeneratorService excelGeneratorService;

    @Autowired
    private HttpServletResponse response;

    @PostMapping("/search")
    public Page<Pagamento> findPagamentos(@RequestBody @Valid PagamentoSearchDto pagSearchDto) {
        return pagamentoService.findAll(pagSearchDto);
    }

    @PostMapping("/sum-pagamento-valor")
    public BigDecimal sumPagamentoValor(@RequestBody PagamentoSearchDto pagSearchDto) {
        return pagamentoService.sumPagamentoValor(pagSearchDto);
    }

    @GetMapping("/five-years")
    public List<FiveYearsDto> fiveYearsPagagmentos() {
        return pagamentoService.fiveYearsPagamentos();
    }

    @PostMapping("/pagamentos-to-excell")
    public byte[] pagamentosToExcell(@RequestBody PagamentoSearchDto pagSearchDto) throws IOException {
        InputStream myStream = excelGeneratorService.pagamentosToExcell(pagSearchDto);
        return IOUtils.toByteArray(myStream);
    }
}
