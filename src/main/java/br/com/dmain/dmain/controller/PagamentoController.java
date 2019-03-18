package br.com.dmain.dmain.controller;

import br.com.dmain.dmain.model.Pagamento;
import br.com.dmain.dmain.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping("/custom-pagamento")
    public Page<Pagamento> getCustomPagamento() {
        return pagamentoService.findAll(0, 10);
    }
}
