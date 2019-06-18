package br.com.dmain.controller;

import br.com.dmain.service.DestinatarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/destinatarios")
public class DestinatarioController {

    @Autowired
    private DestinatarioService destinatarioService;

    @GetMapping("/find-by-grupo/{grupo}")
    public String[] findByGrupo(@PathVariable String grupo) {
        return destinatarioService.findByGrupo(grupo);
    }
}
