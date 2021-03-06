package br.com.dmain.service;

import br.com.dmain.dao.DestinatarioRepository;
import br.com.dmain.model.Destinatario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DestinatarioService {

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    public String[] findByGrupo(String grupo) {
        List<Destinatario> list = destinatarioRepository.findByGrupo(grupo);
        if (!list.isEmpty()) {
            String destinatarios[] = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                destinatarios[i] = list.get(i).getEmail();
            }
            return destinatarios;
        }
        return null;
    }
}
