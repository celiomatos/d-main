package br.com.dmain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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

    @NotNull(message = "informe a pagina")
    private Integer page;

    @NotNull(message = "informe o tamanho da pagina")
    private Integer size;
}
