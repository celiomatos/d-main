package br.com.dmain.service;

import br.com.dmain.dao.FonteRepository;
import br.com.dmain.model.Fonte;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FonteService {

    @Autowired
    private FonteRepository fonteRepository;

    /**
     * @param pageable
     * @return
     */
    public Page<Fonte> findAll(Pageable pageable) {
        return fonteRepository.findAll(pageable);
    }

    public Page<Fonte> findByNome(String nome, Pageable pageable) {
        return fonteRepository.findByNomeIgnoreCaseContainingOrderByNomeAsc(nome.trim(), pageable);
    }
}
