package br.com.dmain.dmain.controller;

import br.com.dmain.dmain.dto.TopFiveCredoresDto;
import br.com.dmain.dmain.model.Credor;
import br.com.dmain.dmain.service.CredorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credores")
public class CredorController {

    @Autowired
    private CredorService credorService;

    @GetMapping("/top-five-credores")
    public List<TopFiveCredoresDto> topFiveCredores(@RequestParam String dateInicial, @RequestParam String dateFinal) {
        return credorService.topFive(dateInicial, dateFinal);
    }

    @GetMapping("/find-by-nome/{nome}")
    public Page<Credor> findByName(@PathVariable String nome, @RequestParam Integer page, @RequestParam Integer count,
                                   @RequestParam String order, @RequestParam String sortProperty) {
        Sort.Direction direction = (order == null || order.equalsIgnoreCase("ASC"))
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        return credorService.findByNome(nome, PageRequest.of(page, count, new Sort(direction, sortProperty)));
    }
}
