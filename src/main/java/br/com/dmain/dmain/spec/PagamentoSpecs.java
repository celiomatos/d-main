package br.com.dmain.dmain.spec;

import org.springframework.data.jpa.domain.Specification;


public class PagamentoSpecs {

    public static Specification isNrObPagamento(String nrOb) {
        return (root, query, builder) -> builder.equal(root.get("nrOb"), nrOb);
    }

    public static Specification isNrNlPagamento(String nrNl) {
        return (root, query, builder) -> builder.equal(root.get("nrNl"), nrNl);
    }

    public static Specification isOrgaoId(long id) {
        return (root, query, builder) -> builder.equal(root.join("orgao").get("id"), id);
    }
}
