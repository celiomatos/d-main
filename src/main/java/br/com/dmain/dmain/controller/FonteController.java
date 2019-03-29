package br.com.dmain.dmain.controller;

import br.com.dmain.dmain.model.Fonte;
import br.com.dmain.dmain.service.FonteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fontes")
public class FonteController {

    @Autowired
    private FonteService fonteService;

    @GetMapping("/find-all")
    public Page<Fonte> findAll(@RequestParam Integer page, @RequestParam Integer count,
                               @RequestParam String order, @RequestParam String sortProperty) {
        Sort.Direction direction = (order == null || order.equalsIgnoreCase("ASC"))
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        return fonteService.findAll(PageRequest.of(page, count, new Sort(direction, sortProperty)));
    }
}
