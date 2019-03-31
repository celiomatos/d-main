package br.com.dmain.service;

import br.com.dmain.dao.ClassificacaoRepository;
import br.com.dmain.model.Classificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository classificacaoRepository;

    /**
     * @return
     */
    public Page<Classificacao> findAll(Pageable pageable) {
        return classificacaoRepository.findAll(pageable);
    }
}
