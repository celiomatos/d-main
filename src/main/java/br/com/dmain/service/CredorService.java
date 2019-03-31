package br.com.dmain.service;

import br.com.dmain.dao.CredorRepository;
import br.com.dmain.dto.TopFiveCredoresDto;
import br.com.dmain.model.Credor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CredorService {

    @Autowired
    private CredorRepository credorRepository;

    /**
     * @param dateInicial initial date.
     * @param dateFinal   final date.
     * @return top five.
     */
    public List<TopFiveCredoresDto> topFive(String dateInicial, String dateFinal) {
        List<Object[]> result = credorRepository.findTopFiveCredores(
                new java.sql.Date(Long.parseLong(dateInicial)), new java.sql.Date(Long.parseLong(dateFinal)));

        List<TopFiveCredoresDto> fiveCredores = new ArrayList<>();
        for (Object[] obj : result) {
            fiveCredores.add(new TopFiveCredoresDto(obj[0].toString(), new BigDecimal(obj[1].toString())));
        }
        return fiveCredores;
    }

    /**
     * @param nome
     * @param pageable
     * @return
     */
    public Page<Credor> findByNome(String nome, Pageable pageable) {
        nome = nome.trim().toUpperCase();
        Page<Credor> page;
        if (nome.contains(",")) {
            String[] credores = nome.split(",");
            if (credores.length > 1) {
                page = credorRepository.findByNomeContainingAndNomeContainingOrderByNomeAsc(credores[0].trim(), credores[1].trim(), pageable);
            } else {
                page = credorRepository.findByNomeContainingOrderByNomeAsc(credores[0].trim(), pageable);
            }
        } else {
            page = credorRepository.findByNomeStartingWithOrderByNomeAsc(nome, pageable);
        }
        return page;
    }
}
