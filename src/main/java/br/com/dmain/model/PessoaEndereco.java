package br.com.dmain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pessoa_endereco", schema = "main")
public class PessoaEndereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pen_id", nullable = false)
    private Long id;

    @Column(name = "pen_cep")
    private String cep;

    @Column(name = "pen_uf", nullable = false)
    private String uf;

    @Column(name = "pen_municipio")
    private String municipio;

    @Column(name = "pen_bairro")
    private String bairro;

    @Column(name = "pen_logradouro")
    private String logradouro;

    @Column(name = "pen_numero")
    private String numero;

    @JoinColumn(name = "pen_pes_id", referencedColumnName = "pes_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pessoa pessoa;
}
