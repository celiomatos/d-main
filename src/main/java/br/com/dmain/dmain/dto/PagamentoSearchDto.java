package br.com.dmain.dmain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PagamentoSearchDto {

    private List<Long> orgaos;

    private List<Long> credores;

    private List<String> fontes;

    private List<Long> classificacoes;

    private Date dataInicial;

    private Date dataFinal;

    private String valorInicial;

    private String valorFinal;
}
