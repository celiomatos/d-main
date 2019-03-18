package br.com.dmain.dmain.spec;

import br.com.dmain.dmain.model.Pagamento;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class PagamentoSpecs {

    public static Specification<Pagamento> isOrgaos(List<Long> ids) {
        return (root, query, builder) -> root.join("orgao").get("id").in(ids);
    }

    public static Specification<Pagamento> isFontes(List<Long> ids) {
        return (root, query, builder) -> root.join("fonte").get("id").in(ids);
    }

    public static Specification<Pagamento> isClassificacoes(List<Long> ids) {
        return (root, query, builder) -> root.join("classificacao").get("id").in(ids);
    }

    public static Specification<Pagamento> isCredores(List<Long> ids) {
        return (root, query, builder) -> root.join("credor").get("id").in(ids);
    }

    public static Specification<Pagamento> isLessData(Date date) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("data"), date);
    }

    public static Specification<Pagamento> isGreaterData(Date date) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("data"), date);
    }

    public static Specification<Pagamento> isLessValor(BigDecimal valor) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("valor"), valor);
    }

    public static Specification<Pagamento> isGreaterValor(BigDecimal valor) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("valor"), valor);
    }
}
