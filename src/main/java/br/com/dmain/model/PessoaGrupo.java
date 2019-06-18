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
@Table(name = "pessoa_grupo", schema = "main")
public class PessoaGrupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pgr_id", nullable = false)
    private Long id;

    @Column(name = "pgr_cargo")
    private String cargo;

    @JoinColumn(name = "pgr_gru_id", referencedColumnName = "gru_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Grupo grupo;

    @JoinColumn(name = "pgr_pes_id", referencedColumnName = "pes_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pessoa pessoa;
}
