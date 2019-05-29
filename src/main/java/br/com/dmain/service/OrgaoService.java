package br.com.dmain.service;

import br.com.dmain.dao.OrgaoRepository;
import br.com.dmain.dto.TopFiveOrgaosDto;
import br.com.dmain.model.Orgao;
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
public class OrgaoService {

    @Autowired
    private OrgaoRepository orgaoRepository;

    /**
     * @param dateInicial initial date.
     * @param dateFinal   final date.
     * @return top five.
     */
    public List<TopFiveOrgaosDto> topFive(String dateInicial, String dateFinal) {
        List<Object[]> result = orgaoRepository.findTopFiveOrgaos(
                new java.sql.Date(Long.parseLong(dateInicial)), new java.sql.Date(Long.parseLong(dateFinal)));

        List<TopFiveOrgaosDto> fiveOrgaos = new ArrayList<>();
        for (Object[] obj : result) {
            fiveOrgaos.add(new TopFiveOrgaosDto(
                    obj[0] != null ? obj[0].toString() : "ORGAO0001",
                    obj[1] != null ? obj[1].toString() : "X",
                    new BigDecimal(obj[2].toString())));
        }
        return fiveOrgaos;
    }

    /**
     * @return
     */
    public Page<Orgao> findAll(Pageable pageable) {
        return orgaoRepository.findAll(pageable);
    }

    /**
     * @param nome
     * @param pageable
     * @return
     */
    public Page<Orgao> findByNome(String nome, Pageable pageable) {
        return orgaoRepository.findByNomeIgnoreCaseContainingOrderByNomeAsc(nome.trim(), pageable);
    }
}
