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
@Table(name = "pessoa_contato", schema = "main")
public class PessoaContato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pco_id", nullable = false)
    private Long id;

    @Column(name = "pco_tipo", nullable = false)
    private short tipo;

    @Column(name = "pco_descricao", nullable = false)
    private String descricao;

    @JoinColumn(name = "pco_pes_id", referencedColumnName = "pes_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pessoa pessoa;
}
